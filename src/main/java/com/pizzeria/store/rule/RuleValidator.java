package com.pizzeria.store.rule;

import com.pizzeria.store.entity.Item;

public interface RuleValidator {
    Item validate(Item item);
}
