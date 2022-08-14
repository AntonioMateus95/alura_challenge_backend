package br.com.alura.budgetapi.controller;

import br.com.alura.budgetapi.controller.request.RevenueRequest;
import br.com.alura.budgetapi.controller.response.RevenueListResponse;
import br.com.alura.budgetapi.controller.response.RevenueResponse;
import br.com.alura.budgetapi.model.Revenue;
import br.com.alura.budgetapi.repository.RevenueRepository;
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
@RequestMapping("/receitas")
public class RevenueController {

    @Autowired
    private RevenueRepository revenueRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<RevenueResponse> create(@RequestBody @Valid RevenueRequest revenue, UriComponentsBuilder uriBuilder) {
        Revenue entity = revenue.toEntity();
        revenueRepository.save(entity);

        URI uri = uriBuilder.path("/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(new RevenueResponse(entity));
    }

    @GetMapping
    public List<RevenueListResponse> list() {
        List<Revenue> revenues = revenueRepository.findAll();
        return RevenueListResponse.map(revenues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevenueResponse> getDetails(@PathVariable Long id) {
        Optional<Revenue> entity = revenueRepository.findById(id);
        return entity.map(revenue -> ResponseEntity.ok(new RevenueResponse(revenue))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RevenueResponse> update(@PathVariable Long id, @RequestBody @Valid RevenueRequest form) {
        Optional<Revenue> entity = revenueRepository.findById(id);
        if (entity.isPresent()) {
            Revenue revenue = entity.get();
            revenue.update(form);
            return ResponseEntity.ok(new RevenueResponse(revenue));
        }
        return ResponseEntity.notFound().build();
    }
}
