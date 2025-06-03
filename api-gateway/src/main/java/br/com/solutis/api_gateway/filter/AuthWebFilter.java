package br.com.solutis.api_gateway.filter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Base64;

@Component
public class AuthWebFilter implements WebFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthWebFilter.class);

    private final Key secretKey;

    public AuthWebFilter(@Value("${jwt.secret}") String secretKeyBase64) {
        try {
            this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKeyBase64));
        } catch (Exception e) {
            logger.error("Erro ao decodificar a chave secreta: {}", e.getMessage());
            throw new IllegalArgumentException("Chave secreta inválida. Verifiquea configuração em jwt.secret", e);
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Cabeçalho Authorization ausente ou inválido: {}", authHeader);
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(401));
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            logger.debug("Token JWT válido para a requisição: {}", exchange.getRequest().getURI());
            return chain.filter(exchange);
        } catch (JwtException e) {
            logger.warn("Falha na validação do Token JWT: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(401));
            return exchange.getResponse().setComplete();
        } catch (Exception e) {
            logger.error("Erro inesperado ao validar o Token JWT: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(500));
            return exchange.getResponse().setComplete();
        }
    }

}
