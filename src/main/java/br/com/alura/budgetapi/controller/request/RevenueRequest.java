package br.com.alura.budgetapi.controller.request;

import br.com.alura.budgetapi.model.Revenue;
import br.com.alura.budgetapi.validators.NonDuplicated;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("descricao")
    private String description;
    @NotNull @DecimalMin(value = "0", inclusive = false)
    @JsonProperty("valor")
    private BigDecimal value;
    @NotNull
    @JsonProperty("data")
    private LocalDate date;

    public Revenue toEntity() {
        Revenue entity = new Revenue();
        entity.setDate(this.date);
        entity.setDescription(this.description);
        entity.setValue(this.value);
        return entity;
    }
}
