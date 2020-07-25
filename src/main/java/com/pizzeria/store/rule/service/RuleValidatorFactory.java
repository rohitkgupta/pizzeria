package com.pizzeria.store.rule.service;

import com.pizzeria.store.rule.RuleValidator;
import com.pizzeria.store.rule.impl.VegPizzaToppingValidator;

public class RuleValidatorFactory {

    public RuleValidator getRuleValidator(String ruleIdentifier) {
        if ("veg-pizza-topping-rule".equals(ruleIdentifier)) {
            return new VegPizzaToppingValidator();
        }
        return null;
    }
}
