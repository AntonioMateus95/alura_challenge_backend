package br.com.alura.budgetapi.config.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormErrorResponse {
    private String field;
    private String message;
}
