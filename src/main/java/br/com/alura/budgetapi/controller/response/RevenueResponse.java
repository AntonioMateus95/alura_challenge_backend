package br.com.alura.budgetapi.controller.response;

import br.com.alura.budgetapi.model.Revenue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RevenueResponse {
    private Long id;
    private LocalDate date;
    private String description;
    private BigDecimal value;

    public RevenueResponse(Revenue revenue) {
        this.id = revenue.getId();
        this.date = revenue.getDate();
        this.description = revenue.getDescription();
        this.value = revenue.getValue();
    }
}
