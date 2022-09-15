package br.com.alura.budgetapi.controller.response;

import br.com.alura.budgetapi.model.ExpenseCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyCategorySummaryResponse {

    @JsonProperty("nome")
    private ExpenseCategory name;

    @JsonProperty("valor_total")
    private BigDecimal totalValue;
}
