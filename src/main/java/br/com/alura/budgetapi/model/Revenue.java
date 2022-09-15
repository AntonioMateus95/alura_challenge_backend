package br.com.alura.budgetapi.model;

import br.com.alura.budgetapi.controller.request.RevenueRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "revenues")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Column(name = "total_value")
    private BigDecimal value;
    private LocalDate date;

    public void update(RevenueRequest form) {
        this.setDescription(form.getDescription());
        this.setValue(form.getValue());
        this.setDate(form.getDate());
    }
}
