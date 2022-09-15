package br.com.alura.budgetapi.repository;

import br.com.alura.budgetapi.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    @Query("Select r From Revenue r Where year(r.date) = :year And month(r.date) = :month")
    List<Revenue> findAllByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);

    @Query("Select r From Revenue r Where month(r.date) = :month And r.description = :description And (:id Is Null Or r.id <> :id)")
    List<Revenue> findAllByMonthAndDescription(@Param("month") Integer month, @Param("description") String description, @Param("id") Long id);

    List<Revenue> searchByDescriptionContainingIgnoreCase(@Param("description") String description);
}
