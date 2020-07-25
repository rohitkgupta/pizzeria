package com.pizzeria.store.rule.service;

import com.pizzeria.store.rule.RuleValidator;
import com.pizzeria.store.rule.impl.*;

public class RuleValidatorFactory {

    public RuleValidator getRuleValidator(String ruleIdentifier) {
        if ("veg-pizza-topping-rule".equals(ruleIdentifier)) {
            return new VegPizzaToppingValidator();
        } else if ("paneer-topping-rule".equals(ruleIdentifier)) {
            return new PaneerToppingValidator();
        } else if ("pizza-crust-rule".equals(ruleIdentifier)) {
            return new PizzaCrustValidator();
        } else if ("nonveg-pizza-topping-rule".equals(ruleIdentifier)) {
            return new NonVegPizzaToppingValidator();
        } else if ("large-pizza-topping-rule".equals(ruleIdentifier)) {
            return new LargePizzaValidator();
        }
        return null;
    }
}
