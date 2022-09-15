package br.com.alura.budgetapi.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MonthlySummaryResponse {

    @JsonProperty("valor_total_receitas")
    private BigDecimal revenueTotalValue;

    @JsonProperty("valor_total_despesas")
    private BigDecimal expenseTotalValue;

    @JsonProperty("saldo_final")
    private BigDecimal finalBalance;

    @JsonProperty("categorias")
    private List<MonthlyCategorySummaryResponse> categories = new ArrayList<>();
}
