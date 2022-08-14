package br.com.alura.budgetapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;

public enum ExpenseCategory {
    FOOD("Alimentação"),
    HEALTH("Saúde"),
    HOUSING("Moradia"),
    TRANSPORT("Transporte"),
    EDUCATION("Educação"),
    ENTERTAINMENT("Lazer"),
    UNFORESEEN("Imprevistos"),
    OTHERS("Outras");

    private final String value;
    ExpenseCategory(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }

    @JsonCreator
    public static ExpenseCategory fromValue(String text) {
        Optional<ExpenseCategory> category = Arrays
                .stream(ExpenseCategory.values())
                .filter(c -> c.value.equals(text))
                .findFirst();

        return category.isPresent() ? category.get() : ExpenseCategory.OTHERS;
    }
}
