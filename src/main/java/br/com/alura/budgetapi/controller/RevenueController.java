package br.com.alura.budgetapi.controller;

import br.com.alura.budgetapi.controller.request.RevenueRequest;
import br.com.alura.budgetapi.controller.response.RevenueListResponse;
import br.com.alura.budgetapi.controller.response.RevenueResponse;
import br.com.alura.budgetapi.exceptions.CustomException;
import br.com.alura.budgetapi.model.Revenue;
import br.com.alura.budgetapi.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/receitas")
public class RevenueController {

    @Autowired
    private RevenueRepository revenueRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<RevenueResponse> create(@RequestBody @Valid RevenueRequest revenue, UriComponentsBuilder uriBuilder) throws CustomException {
        List<Revenue> existingOnes = revenueRepository.findAllByMonthAndDescription(revenue.getDate().getMonth().getValue(), revenue.getDescription());
        if (existingOnes.isEmpty()) {
            Revenue entity = revenue.toEntity();
            revenueRepository.save(entity);

            URI uri = uriBuilder.path("/{id}").buildAndExpand(entity.getId()).toUri();
            return ResponseEntity.created(uri).body(new RevenueResponse(entity));
        } else {
            throw new CustomException("Receita já existe dentro do mês", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<RevenueListResponse> list() {
        List<Revenue> revenues = revenueRepository.findAll();
        return RevenueListResponse.map(revenues);
    }
}
