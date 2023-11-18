package api.vacinacao.vacina.handler.exceptions;

public class ResourceNotFoundException extends Throwable {

    public ResourceNotFoundException() {
        super("Vacina não encontrada");
    }
}
