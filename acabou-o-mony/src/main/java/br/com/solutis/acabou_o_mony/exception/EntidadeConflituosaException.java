package br.com.solutis.acabou_o_mony.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeConflituosaException extends RuntimeException {
  public EntidadeConflituosaException(String message) {
    super(message);
  }
}
