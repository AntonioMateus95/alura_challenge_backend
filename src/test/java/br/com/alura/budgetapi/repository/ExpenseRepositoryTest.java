package br.com.alura.budgetapi.repository;

import br.com.alura.budgetapi.model.Expense;
import br.com.alura.budgetapi.model.ExpenseCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql({"/fixtures/expenses.sql"})
public class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository repository;

    @Test
    public void shouldSearchExpensesByDescription() {
        List<Expense> expenses = repository.searchByDescriptionContainingIgnoreCase("parque");
        assertEquals(expenses.size(), 1);
    }

    @Test
    public void shouldNotFindAnyExpensesInSeptember() {
        List<Expense> expenses = repository.findAllByYearAndMonth(2022, 9);
        assertTrue(expenses.isEmpty());
    }

    @Test
    public void shouldNotFindAnySimilarExpenses() {
        Optional<Expense> comparison = repository.findById(5L);
        assertTrue(comparison.isPresent());
        List<Expense> similarities = repository.findAllByMonthAndDescription(
                comparison.get().getDate().getMonthValue(),
                comparison.get().getDescription(),
                comparison.get().getId());
        assertTrue(similarities.isEmpty());
    }

    @Test
    public void shouldFindSimilarExpense() {
        Expense newExpense = new Expense(null, "Compras do mês", new BigDecimal("1500"), LocalDate.of(2022, 8, 31), ExpenseCategory.FOOD);
        List<Expense> similarities = repository.findAllByMonthAndDescription(
                newExpense.getDate().getMonthValue(),
                newExpense.getDescription(),
                newExpense.getId());
        assertFalse(similarities.isEmpty());
    }
}
