package br.com.alura.budgetapi.controller.request;

import br.com.alura.budgetapi.model.Revenue;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RevenueRequest {
    @NotNull @NotEmpty
    private String description;
    @NotNull @DecimalMin(value = "0", inclusive = false)
    private BigDecimal value;
    @NotNull
    private LocalDate date;

    public Revenue toEntity() {
        Revenue entity = new Revenue();
        entity.setDate(this.date);
        entity.setDescription(this.description);
        entity.setValue(this.value);
        return entity;
    }
}
