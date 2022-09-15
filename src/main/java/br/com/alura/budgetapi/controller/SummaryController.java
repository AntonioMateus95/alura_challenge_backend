package br.com.alura.budgetapi.controller;

import br.com.alura.budgetapi.controller.response.MonthlyCategorySummaryResponse;
import br.com.alura.budgetapi.controller.response.MonthlySummaryResponse;
import br.com.alura.budgetapi.model.Expense;
import br.com.alura.budgetapi.model.Revenue;
import br.com.alura.budgetapi.repository.ExpenseRepository;
import br.com.alura.budgetapi.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resumo")
public class SummaryController {

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<MonthlySummaryResponse> monthlySummary(@PathVariable Integer year, @PathVariable Integer month) {
        List<Revenue> revenues = revenueRepository.findAllByYearAndMonth(year, month);
        List<Expense> expenses = expenseRepository.findAllByYearAndMonth(year, month);

        BigDecimal revenueTotalValue = revenues.stream().map(Revenue::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expenseTotalValue = expenses.stream().map(Expense::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);

        MonthlySummaryResponse summary = new MonthlySummaryResponse();
        summary.setRevenueTotalValue(revenueTotalValue);
        summary.setExpenseTotalValue(expenseTotalValue);
        summary.setFinalBalance(revenueTotalValue.subtract(expenseTotalValue));

        List<MonthlyCategorySummaryResponse> categories = new ArrayList<>();
        expenses.stream().collect(Collectors.groupingBy(Expense::getCategory))
                .forEach((category, expenseList) ->
                        categories.add(new MonthlyCategorySummaryResponse(
                                category,
                                expenseList.stream().map(Expense::getValue).reduce(BigDecimal.ZERO, BigDecimal::add)
                        ))
                );
        summary.setCategories(categories);

        return ResponseEntity.ok().body(summary);
    }
}
