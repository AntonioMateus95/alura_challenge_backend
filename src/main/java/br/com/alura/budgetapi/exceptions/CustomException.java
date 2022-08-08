package br.com.alura.budgetapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CustomException extends Exception {
    private String message;
    private HttpStatus status;
}
