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

    @Transactional
    @PostMapping
    public ResponseEntity<RevenueResponse> create(@RequestBody @Valid RevenueRequest form, UriComponentsBuilder uriBuilder) {
        Revenue entity = form.toEntity();
        revenueRepository.save(entity);

        URI uri = uriBuilder.path("/{id}").buildAndExpand(entity.getId()).toUri();
        return ResponseEntity.created(uri).body(new RevenueResponse(entity));
    }

    @GetMapping
    public List<RevenueListResponse> list(@RequestParam("descricao") Optional<String> description) {
        List<Revenue> revenues;
        if (description.isPresent()) {
            revenues = revenueRepository.searchByDescriptionContainingIgnoreCase(description.get());
        } else {
            revenues = revenueRepository.findAll();
        }
        return RevenueListResponse.map(revenues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevenueResponse> getDetails(@PathVariable Long id) {
        Optional<Revenue> entity = revenueRepository.findById(id);
        if (entity.isPresent()) {
            return ResponseEntity.ok(new RevenueResponse(entity.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<RevenueResponse> update(@PathVariable Long id, @RequestBody @Valid RevenueRequest form) {
        Optional<Revenue> entity = revenueRepository.findById(id);
        if (entity.isPresent()) {
            Revenue revenue = entity.get();
            revenue.update(form);
            return ResponseEntity.ok(new RevenueResponse(revenue));
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<RevenueResponse> remove(@PathVariable Long id) {
        Optional<Revenue> entity = revenueRepository.findById(id);
        if (entity.isPresent()) {
            revenueRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
