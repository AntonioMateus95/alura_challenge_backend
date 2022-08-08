package br.com.alura.budgetapi.config.validation;


import br.com.alura.budgetapi.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {
    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormErrorResponse> handle(MethodArgumentNotValidException exception) {
        List<FormErrorResponse> dto = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            dto.add(new FormErrorResponse(e.getField(), message));
        });

        return dto;
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<GenericErrorResponse> handle(CustomException exception) {
        return ResponseEntity.status(exception.getStatus()).body(new GenericErrorResponse(exception.getMessage()));
    }
}
