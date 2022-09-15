package br.com.alura.budgetapi.controller;

import br.com.alura.budgetapi.controller.request.ExpenseRequest;
import br.com.alura.budgetapi.controller.response.ExpenseListResponse;
import br.com.alura.budgetapi.controller.response.ExpenseResponse;
import br.com.alura.budgetapi.model.Expense;
import br.com.alura.budgetapi.model.ExpenseCategory;
import br.com.alura.budgetapi.repository.ExpenseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpenseControllerTest {
    @InjectMocks
    private ExpenseController expenseController;

    @Mock
    private ExpenseRepository repository;

    @Test
    public void shouldCreateExpense() {
        ExpenseRequest expenseRequest = mock(ExpenseRequest.class);
        Expense expenseEntity = mock(Expense.class);
        UriComponentsBuilder uriBuilder = mock(UriComponentsBuilder.class);
        UriComponents uriComponents = mock(UriComponents.class);

        when(expenseRequest.toEntity()).thenReturn(expenseEntity);
        when(uriBuilder.path(anyString())).thenReturn(uriBuilder);
        when(uriBuilder.buildAndExpand(anyLong())).thenReturn(uriComponents);
        when(uriComponents.toUri()).thenReturn(URI.create("/1"));

        ResponseEntity<ExpenseResponse> responseEntity = expenseController.create(expenseRequest, uriBuilder);
        Assertions.assertEquals(201, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("/1", Objects.requireNonNull(responseEntity.getHeaders().getLocation()).getPath());
    }

    @Test
    public void shouldListExpenses() {
        Expense firstExpense = new Expense(1L, "Primeira despesa", new BigDecimal("3000"), LocalDate.now(), ExpenseCategory.OTHERS);

        when(repository.findAll()).thenReturn(List.of(firstExpense));

        List<ExpenseListResponse> expenses = expenseController.list(Optional.empty());
        Assertions.assertEquals(1, expenses.size());
    }

    @Test
    public void shouldFilterExpensesByDescription() {
        when(repository.searchByDescriptionContainingIgnoreCase(anyString())).thenReturn(List.of());

        List<ExpenseListResponse> expenses = expenseController.list(Optional.of("test"));
        Assertions.assertEquals(0, expenses.size());
    }

    @Test
    public void shouldListExpensesByYearAndMonth() {
        Expense firstExpense = new Expense(1L, "Despesa do mês", new BigDecimal("2000"), LocalDate.of(2022, 8, 31), ExpenseCategory.FOOD);

        when(repository.findAllByYearAndMonth(anyInt(), anyInt())).thenReturn(List.of(firstExpense));

        List<ExpenseListResponse> expenses = expenseController.listByYearAndMonth(2022, 8);
        Assertions.assertEquals(1, expenses.size());
        Assertions.assertTrue(expenses.stream().findFirst().isPresent());
    }

    @Test
    public void shouldGetExpenseDetailsById() {
        Expense expense = new Expense(1L, "Despesa do mês", new BigDecimal("2000"), LocalDate.of(2022, 8, 31), ExpenseCategory.FOOD);

        when(repository.findById(1L)).thenReturn(Optional.of(expense));

        ResponseEntity<ExpenseResponse> responseEntity = expenseController.getDetails(1L);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldNotGetExpenseDetailsByNotFoundId() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<ExpenseResponse> responseEntity = expenseController.getDetails(2L);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldUpdateExpense() {
        Expense expense = mock(Expense.class);
        ExpenseRequest expenseRequest = mock(ExpenseRequest.class);

        when(repository.findById(anyLong())).thenReturn(Optional.of(expense));

        ResponseEntity<ExpenseResponse> responseEntity = expenseController.update(1L, expenseRequest);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldNotUpdateExpenseWhenItDoesNotExist() {
        ExpenseRequest expenseRequest = mock(ExpenseRequest.class);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ExpenseResponse> responseEntity = expenseController.update(2L, expenseRequest);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldRemoveExpense() {
        Expense expense = mock(Expense.class);

        when(repository.findById(anyLong())).thenReturn(Optional.of(expense));

        ResponseEntity<ExpenseResponse> responseEntity = expenseController.remove(1L);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldNotRemoveExpenseWhenItDoesNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<ExpenseResponse> responseEntity = expenseController.remove(2L);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }
}
