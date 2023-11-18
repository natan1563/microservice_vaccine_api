package api.vacinacao.vacina.handler.exceptions;

public class RegisterBadRequestException extends Throwable {

    public RegisterBadRequestException() {
        super("Vacinas com quantidade de doses superiores a uma necessitam do intervalo em dias.");
    }
}
