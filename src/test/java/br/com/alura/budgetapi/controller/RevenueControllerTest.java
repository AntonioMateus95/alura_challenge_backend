package br.com.alura.budgetapi.controller;

import br.com.alura.budgetapi.controller.request.RevenueRequest;
import br.com.alura.budgetapi.controller.response.RevenueListResponse;
import br.com.alura.budgetapi.controller.response.RevenueResponse;
import br.com.alura.budgetapi.model.Revenue;
import br.com.alura.budgetapi.repository.RevenueRepository;
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
public class RevenueControllerTest {
    @InjectMocks
    private RevenueController revenueController;

    @Mock
    private RevenueRepository repository;

    @Test
    public void shouldCreateRevenue() {
        RevenueRequest revenueRequest = mock(RevenueRequest.class);
        Revenue revenueEntity = mock(Revenue.class);
        UriComponentsBuilder uriBuilder = mock(UriComponentsBuilder.class);
        UriComponents uriComponents = mock(UriComponents.class);

        when(revenueRequest.toEntity()).thenReturn(revenueEntity);
        when(uriBuilder.path(anyString())).thenReturn(uriBuilder);
        when(uriBuilder.buildAndExpand(anyLong())).thenReturn(uriComponents);
        when(uriComponents.toUri()).thenReturn(URI.create("/1"));

        ResponseEntity<RevenueResponse> responseEntity = revenueController.create(revenueRequest, uriBuilder);
        Assertions.assertEquals(201, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("/1", Objects.requireNonNull(responseEntity.getHeaders().getLocation()).getPath());
    }

    @Test
    public void shouldListRevenues() {
        Revenue firstRevenue = new Revenue(1L, "Primeira receita", new BigDecimal("3000"), LocalDate.now());

        when(repository.findAll()).thenReturn(List.of(firstRevenue));

        List<RevenueListResponse> revenues = revenueController.list(Optional.empty());
        Assertions.assertEquals(1, revenues.size());
    }

    @Test
    public void shouldFilterRevenuesByDescription() {
        when(repository.searchByDescriptionContainingIgnoreCase(anyString())).thenReturn(List.of());

        List<RevenueListResponse> revenues = revenueController.list(Optional.of("test"));
        Assertions.assertEquals(0, revenues.size());
    }

    @Test
    public void shouldListRevenuesByYearAndMonth() {
        Revenue firstRevenue = new Revenue(1L, "Salário", new BigDecimal("6000"), LocalDate.of(2022, 8, 31));

        when(repository.findAllByYearAndMonth(anyInt(), anyInt())).thenReturn(List.of(firstRevenue));

        List<RevenueListResponse> revenues = revenueController.listByYearAndMonth(2022, 8);
        Assertions.assertEquals(1, revenues.size());
        Assertions.assertTrue(revenues.stream().findFirst().isPresent());
    }

    @Test
    public void shouldGetRevenueDetailsById() {
        Revenue revenue = new Revenue(1L, "Salário", new BigDecimal("6000"), LocalDate.of(2022, 8, 31));

        when(repository.findById(1L)).thenReturn(Optional.of(revenue));

        ResponseEntity<RevenueResponse> responseEntity = revenueController.getDetails(1L);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldNotGetRevenueDetailsByNotFoundId() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<RevenueResponse> responseEntity = revenueController.getDetails(2L);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldUpdateRevenue() {
        Revenue revenue = mock(Revenue.class);
        RevenueRequest revenueRequest = mock(RevenueRequest.class);

        when(repository.findById(anyLong())).thenReturn(Optional.of(revenue));

        ResponseEntity<RevenueResponse> responseEntity = revenueController.update(1L, revenueRequest);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldNotUpdateRevenueWhenItDoesNotExist() {
        RevenueRequest revenueRequest = mock(RevenueRequest.class);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<RevenueResponse> responseEntity = revenueController.update(2L, revenueRequest);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldRemoveRevenue() {
        Revenue revenue = mock(Revenue.class);

        when(repository.findById(anyLong())).thenReturn(Optional.of(revenue));

        ResponseEntity<RevenueResponse> responseEntity = revenueController.remove(1L);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void shouldNotRemoveRevenueWhenItDoesNotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<RevenueResponse> responseEntity = revenueController.remove(2L);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }
}
