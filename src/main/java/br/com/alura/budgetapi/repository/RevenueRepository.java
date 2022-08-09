package br.com.alura.budgetapi.repository;

import br.com.alura.budgetapi.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    @Query("Select r From Revenue r Where month(r.date) = ?1 And r.description = ?2 And r.id <> ?3")
    List<Revenue> findAllByMonthAndDescription(Integer month, String description, Long id);
}
