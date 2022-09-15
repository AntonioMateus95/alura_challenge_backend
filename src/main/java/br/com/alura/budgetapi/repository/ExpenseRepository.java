package br.com.alura.budgetapi.repository;

import br.com.alura.budgetapi.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("Select e From Expense e Where year(e.date) = :year And month(e.date) = :month")
    List<Expense> findAllByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);

    @Query("Select e From Expense e Where month(e.date) = :month And e.description = :description And (:id Is Null Or e.id <> :id)")
    List<Expense> findAllByMonthAndDescription(@Param("month") Integer month, @Param("description") String description, @Param("id") Long id);

    List<Expense> searchByDescriptionContainingIgnoreCase(String description);
}
