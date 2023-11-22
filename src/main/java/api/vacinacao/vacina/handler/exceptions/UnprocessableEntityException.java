package api.vacinacao.vacina.handler.exceptions;

public class UnprocessableEntityException extends Exception{
    public UnprocessableEntityException(String message) {
        super(message);
    }
}