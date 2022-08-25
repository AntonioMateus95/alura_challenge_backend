package br.com.alura.budgetapi.repository;

import br.com.alura.budgetapi.model.Expense;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
public class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository repository;

    @Test
    public void shouldSearchExpensesByDescription() {
        List<Expense> expenses = repository.findAll();
        Assertions.assertTrue(expenses.isEmpty());
    }


}
