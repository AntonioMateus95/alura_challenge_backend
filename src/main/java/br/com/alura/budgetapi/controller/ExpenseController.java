package br.com.alura.budgetapi.controller;

import br.com.alura.budgetapi.controller.request.ExpenseRequest;
import br.com.alura.budgetapi.controller.response.ExpenseListResponse;
import br.com.alura.budgetapi.controller.response.ExpenseResponse;
import br.com.alura.budgetapi.model.Expense;
import br.com.alura.budgetapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesas")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<ExpenseResponse> create(@RequestBody @Valid ExpenseRequest form, UriComponentsBuilder uriBuilder) {
        Expense entity = form.toEntity();
        expenseRepository.save(entity);

        URI uri = uriBuilder.path("/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(new ExpenseResponse(entity));
    }

    @GetMapping
    public List<ExpenseListResponse> list(@RequestParam("descricao") Optional<String> description) {
        List<Expense> expenses;
        if (description.isPresent()) {
            expenses = expenseRepository.searchByDescriptionContainingIgnoreCase(description.get());
        } else {
            expenses = expenseRepository.findAll();
        }
        return ExpenseListResponse.map(expenses);
    }

    @GetMapping("/{year}/{month}")
    public List<ExpenseListResponse> listByYearAndMonth(@PathVariable Integer year, @PathVariable Integer month) {
        List<Expense> expenses = expenseRepository.findAllByYearAndMonth(year, month);
        return ExpenseListResponse.map(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getDetails(@PathVariable Long id) {
        Optional<Expense> entity = expenseRepository.findById(id);
        if (entity.isPresent()) {
            return ResponseEntity.ok(new ExpenseResponse(entity.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> update(@PathVariable Long id, @RequestBody @Valid ExpenseRequest form) {
        Optional<Expense> entity = expenseRepository.findById(id);
        if (entity.isPresent()) {
            Expense expense = entity.get();
            expense.update(form);
            return ResponseEntity.ok(new ExpenseResponse(expense));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<ExpenseResponse> remove(@PathVariable Long id) {
        Optional<Expense> entity = expenseRepository.findById(id);
        if (entity.isPresent()) {
            expenseRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
