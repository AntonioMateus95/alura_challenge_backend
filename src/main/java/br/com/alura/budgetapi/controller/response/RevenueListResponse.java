package br.com.alura.budgetapi.controller.response;

import br.com.alura.budgetapi.model.Revenue;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RevenueListResponse {
    @JsonProperty("data")
    private LocalDate date;
    @JsonProperty("descricao")
    private String description;
    @JsonProperty("valor")
    private BigDecimal value;

    public RevenueListResponse(Revenue revenue) {
        this.date = revenue.getDate();
        this.description = revenue.getDescription();
        this.value = revenue.getValue();
    }

    public static List<RevenueListResponse> map(List<Revenue> items) {
        return items.stream().map(RevenueListResponse::new).collect(Collectors.toList());
    }
}
