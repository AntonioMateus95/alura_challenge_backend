package br.com.alura.budgetapi.repository;

import br.com.alura.budgetapi.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("Select e From Expense e Where month(e.date) = ?1 And e.description = ?2 And (?3 Is Null Or e.id <> ?3)")
    List<Expense> findAllByMonthAndDescription(Integer month, String description, Long id);
}
