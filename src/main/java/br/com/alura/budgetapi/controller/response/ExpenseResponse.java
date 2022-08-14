package br.com.alura.budgetapi.controller.response;

import br.com.alura.budgetapi.model.Expense;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ExpenseResponse {
    private Long id;
    @JsonProperty("data")
    private LocalDate date;
    @JsonProperty("descricao")
    private String description;
    @JsonProperty("valor")
    private BigDecimal value;

    public ExpenseResponse(Expense expense) {
        this.id = expense.getId();
        this.date = expense.getDate();
        this.description = expense.getDescription();
        this.value = expense.getValue();
    }
}
