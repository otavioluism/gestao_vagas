package br.com.otavioluism.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException() {
        super("Usuário já existente!");
    }
}
