package com.pizzeria.store.businessrule.offer.service;

import com.pizzeria.store.businessrule.offer.OfferRule;
import com.pizzeria.store.businessrule.offer.impl.*;

import java.util.Optional;

class OfferRuleFactory {

    Optional<OfferRule> getRuleValidator(String ruleIdentifier) {
        if ("large-pizza-topping-offer".equals(ruleIdentifier)) {
            return Optional.of(new LargePizzaOfferRule());
        }
        return Optional.empty();
    }
}
