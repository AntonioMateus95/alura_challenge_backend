package br.com.alura.budgetapi.controller.response;

import br.com.alura.budgetapi.model.Expense;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ExpenseListResponse {
    @JsonProperty("data")
    private LocalDate date;
    @JsonProperty("descricao")
    private String description;
    @JsonProperty("valor")
    private BigDecimal value;

    public ExpenseListResponse(Expense expense) {
        this.date = expense.getDate();
        this.description = expense.getDescription();
        this.value = expense.getValue();
    }

    public static List<ExpenseListResponse> map(List<Expense> items) {
        return items.stream().map(ExpenseListResponse::new).collect(Collectors.toList());
    }
}
