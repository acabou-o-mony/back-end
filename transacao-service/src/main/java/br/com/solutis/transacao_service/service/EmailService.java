package br.com.solutis.transacao_service.service;

import br.com.solutis.transacao_service.dto.TransacaoResponseDto;
import br.com.solutis.transacao_service.entity.Transacao;
import br.com.solutis.usuario_service.repository.UsuarioRepository;
import br.com.solutis.venda_service.entity.Pedido;
import br.com.solutis.usuario_service.entity.conta.Conta;
import br.com.solutis.usuario_service.entity.Usuario;
import br.com.solutis.transacao_service.repository.TransacaoRepository;
import br.com.solutis.usuario_service.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TransacaoRepository transacaoRep;

    @Autowired
    private ContaRepository contaRepo;

    private final RestTemplate template = new RestTemplate();

    public void enviarEmailConfirmacao(Transacao entity) {
        SimpleMailMessage message = new SimpleMailMessage();

        String url = "http://localhost:8085/" + entity.getPedidoId();
        Pedido pedido = template.getForObject(url, Pedido.class);

        Optional<Conta> conta = contaRepo.findById(pedido.getIdConta());
        Usuario usuario = conta.get().getUsuario();


        message.setTo(usuario.getEmail());
        message.setSubject("Transação concluída");
        message.setText("Olá " + usuario.getNome() + "\n\n" + "Seu pedido no valor de R$" + pedido.getTotal() + " foi pago com sucesso.\n\nObrigado pela preferência!");

        sender.send(message);

        System.out.println("📤 E-mail de confirmação enviado para: " + usuario.getEmail());
    }

}
