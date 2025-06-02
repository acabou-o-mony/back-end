package br.com.solutis.venda_service.exception;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(String message) {
        super(message);
    }
}
