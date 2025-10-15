package br.com.uniceplac.sneaklab.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail handleNotFound(UserNotFoundException ex) {
    var pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    pd.setTitle("Recurso não encontrado");
    return pd;
  }

  @ExceptionHandler({ DataIntegrityViolationException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleIntegrity(DataIntegrityViolationException ex) {
    var pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMostSpecificCause().getMessage());
    pd.setTitle("Violação de integridade");
    return pd;
  }

  @ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleValidation(Exception ex) {
    var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    pd.setTitle("Dados inválidos");
    pd.setDetail("Verifique os campos enviados.");
    return pd;
  }
}
