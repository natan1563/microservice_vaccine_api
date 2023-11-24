package api.vacinacao.vacina.handler;

import api.vacinacao.vacina.handler.exceptions.RegisterBadRequestException;
import api.vacinacao.vacina.handler.exceptions.ResourceNotFoundException;
import api.vacinacao.vacina.handler.exceptions.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        body.put("mensagem", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();

        body.put("mensagem", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegisterBadRequestException.class)
    public ResponseEntity<Object> handleRegisterBadRequest(RegisterBadRequestException ex) {
        Map<String, Object> body = new HashMap<>();

        body.put("mensagem", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Object> handleUnprocessableEntityException(UnprocessableEntityException ex) {
        Map<String, Object> body = new HashMap<>();

        body.put("mensagem", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof DateTimeParseException) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("mensagem", "Formato de data inválido. Use o formato 'yyyy-MM-dd'");

            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> body = new HashMap<>();
        body.put("mensagem", "Você passou algum dado inválido no corpo");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException ||
                ex instanceof ResourceNotFoundException ||
                ex instanceof RegisterBadRequestException ||
                ex instanceof HttpMessageNotReadableException) {
            return null;
        }

        Map<String, Object> body = new HashMap<>();
        body.put("mensagem", "Ocorreu um erro na aplicação. Nossa equipe de TI já foi notificada e em" +
                " breve nossos serviços estarão reestabelecidos. Para maiores informações entre em" +
                " contato pelo nosso WhatsApp 71 99999-9999. Lamentamos o ocorrido!");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}