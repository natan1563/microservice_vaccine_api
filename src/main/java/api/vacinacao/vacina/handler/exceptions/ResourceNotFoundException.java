package api.vacinacao.vacina.handler.exceptions;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException() {
        super("Vacina não encontrada");
    }
}
