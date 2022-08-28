package br.com.alura.budgetapi.model;

import br.com.alura.budgetapi.controller.request.ExpenseRequest;
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
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Column(name = "total_value")
    private BigDecimal value;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    public void update(ExpenseRequest form) {
        this.setDescription(form.getDescription());
        this.setValue(form.getValue());
        this.setDate(form.getDate());
        this.setCategory(form.getCategory());
    }
}
