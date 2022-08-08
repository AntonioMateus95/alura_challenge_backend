package br.com.alura.budgetapi.controller.response;

import br.com.alura.budgetapi.model.Revenue;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RevenueResponse {
    private Long id;
    @JsonProperty("data")
    private LocalDate date;
    @JsonProperty("descricao")
    private String description;
    @JsonProperty("valor")
    private BigDecimal value;

    public RevenueResponse(Revenue revenue) {
        this.id = revenue.getId();
        this.date = revenue.getDate();
        this.description = revenue.getDescription();
        this.value = revenue.getValue();
    }
}
