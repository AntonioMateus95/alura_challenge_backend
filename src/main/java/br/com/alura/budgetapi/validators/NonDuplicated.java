package br.com.alura.budgetapi.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { MonthlyDuplicatedRevenueValidator.class, MonthlyDuplicatedExpenseValidator.class })
public @interface NonDuplicated {

    String message() default "duplicated registry";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
