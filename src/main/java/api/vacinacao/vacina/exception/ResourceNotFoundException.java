package api.vacinacao.vacina.exception;

public class ResourceNotFoundException extends Throwable {

    public ResourceNotFoundException() {
        super("Vacina não encontrada");
    }
}
