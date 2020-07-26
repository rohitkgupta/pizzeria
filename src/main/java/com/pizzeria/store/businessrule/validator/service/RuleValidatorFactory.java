package com.pizzeria.store.businessrule.validator.service;

import com.pizzeria.store.businessrule.validator.RuleValidator;
import com.pizzeria.store.businessrule.validator.impl.*;

import java.util.Optional;

class RuleValidatorFactory {

    Optional<RuleValidator> getRuleValidator(String ruleIdentifier) {
        if ("veg-pizza-topping-rule".equals(ruleIdentifier)) {
            return Optional.of(new VegPizzaToppingValidator());
        } else if ("paneer-topping-rule".equals(ruleIdentifier)) {
            return Optional.of(new PaneerToppingValidator());
        } else if ("pizza-crust-rule".equals(ruleIdentifier)) {
            return Optional.of(new PizzaCrustValidator());
        } else if ("nonveg-pizza-topping-rule".equals(ruleIdentifier)) {
            return Optional.of(new NonVegPizzaToppingValidator());
        }
        return Optional.empty();
    }
}
