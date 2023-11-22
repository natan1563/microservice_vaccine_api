package api.vacinacao.vacina.handler;

public class UnprocessableEntityException extends Exception{
    public UnprocessableEntityException(String message) {
        super(message);
    }
}