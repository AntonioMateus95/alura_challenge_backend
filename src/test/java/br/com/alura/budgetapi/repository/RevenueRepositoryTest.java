package br.com.alura.budgetapi.repository;

import br.com.alura.budgetapi.model.Revenue;
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
@Sql({"/fixtures/revenues.sql"})
public class RevenueRepositoryTest {

    @Autowired
    private RevenueRepository repository;

    @Test
    public void shouldSearchRevenuesByDescription() {
        List<Revenue> revenues = repository.searchByDescriptionContainingIgnoreCase("salário");
        assertEquals(revenues.size(), 1);
    }

    @Test
    public void shouldNotFindAnyRevenuesInSeptember() {
        List<Revenue> revenues = repository.findAllByYearAndMonth(2022, 9);
        assertTrue(revenues.isEmpty());
    }

    @Test
    public void shouldNotFindAnySimilarRevenues() {
        Optional<Revenue> comparison = repository.findById(2L);
        assertTrue(comparison.isPresent());
        List<Revenue> similarities = repository.findAllByMonthAndDescription(
                comparison.get().getDate().getMonthValue(),
                comparison.get().getDescription(),
                comparison.get().getId());
        assertTrue(similarities.isEmpty());
    }

    @Test
    public void shouldFindSimilarRevenue() {
        Revenue newRevenue = new Revenue(null, "Freelancer", new BigDecimal("2000"), LocalDate.of(2022, 8, 20));
        List<Revenue> similarities = repository.findAllByMonthAndDescription(
                newRevenue.getDate().getMonthValue(),
                newRevenue.getDescription(),
                newRevenue.getId());
        assertFalse(similarities.isEmpty());
    }
}
