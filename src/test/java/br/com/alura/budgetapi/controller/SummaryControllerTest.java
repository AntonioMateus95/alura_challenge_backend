package br.com.alura.budgetapi.controller;

import br.com.alura.budgetapi.controller.response.MonthlySummaryResponse;
import br.com.alura.budgetapi.model.Expense;
import br.com.alura.budgetapi.model.ExpenseCategory;
import br.com.alura.budgetapi.model.Revenue;
import br.com.alura.budgetapi.repository.ExpenseRepository;
import br.com.alura.budgetapi.repository.RevenueRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SummaryControllerTest {
    @InjectMocks
    private SummaryController controller;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private RevenueRepository revenueRepository;

    @Test
    public void shouldBuildMonthlySummary() {
        List<Revenue> revenues = List.of(
                new Revenue(1L, "Primeira receita", new BigDecimal("2500"), LocalDate.of(2022, 8, 10)),
                new Revenue(2L, "Segunda receita", new BigDecimal("2000"), LocalDate.of(2022, 8, 12))
        );

        List<Expense> expenses = List.of(
                new Expense(1L, "Primeira despesa", new BigDecimal("500"), LocalDate.of(2022, 8, 10), ExpenseCategory.FOOD),
                new Expense(2L, "Segunda despesa", new BigDecimal("500"), LocalDate.of(2022, 8, 11), ExpenseCategory.OTHERS),
                new Expense(3L, "Terceira despesa", new BigDecimal("500"), LocalDate.of(2022, 8, 12), ExpenseCategory.EDUCATION),
                new Expense(4L, "Quarta despesa", new BigDecimal("500"), LocalDate.of(2022, 8, 13), ExpenseCategory.OTHERS)
        );

        when(expenseRepository.findAllByYearAndMonth(anyInt(), anyInt())).thenReturn(expenses);
        when(revenueRepository.findAllByYearAndMonth(anyInt(), anyInt())).thenReturn(revenues);

        ResponseEntity<MonthlySummaryResponse> responseEntity = controller.monthlySummary(2022, 8);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals(new BigDecimal("4500"), responseEntity.getBody().getRevenueTotalValue());
        Assertions.assertEquals(new BigDecimal("2000"), responseEntity.getBody().getExpenseTotalValue());
        Assertions.assertEquals(new BigDecimal("2500"), responseEntity.getBody().getFinalBalance());
        Assertions.assertEquals(3, responseEntity.getBody().getCategories().size());
    }
}
