package br.com.alura.budgetapi.validators;

import br.com.alura.budgetapi.controller.request.RevenueRequest;
import br.com.alura.budgetapi.model.Revenue;
import br.com.alura.budgetapi.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MonthlyDuplicatedRevenueValidator
    implements ConstraintValidator<NonDuplicated, RevenueRequest> {

    @Autowired
    private RevenueRepository repository;

    @Autowired
    private HttpServletRequest request;

    public void initialize(NonDuplicated constraintAnnotation) {

    }

    @Override
    public boolean isValid(RevenueRequest value, ConstraintValidatorContext context) {
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long id = Objects.isNull(map.get("id")) ? null : Long.valueOf(map.get("id"));
        LocalDate date = value.getDate();
        String description = value.getDescription();
        if (date == null || description == null) {
            return true;
        }

        List<Revenue> existingOnes = repository.findAllByMonthAndDescription(date.getMonth().getValue(), description, id);
        return existingOnes.isEmpty();
    }
}
