package br.com.alura.budgetapi.config.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericErrorResponse {
    private String message;
}
