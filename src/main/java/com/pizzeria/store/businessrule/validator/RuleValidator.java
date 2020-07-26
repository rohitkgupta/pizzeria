package com.pizzeria.store.businessrule.validator;

import com.pizzeria.store.entity.MenuItem;

public interface RuleValidator<T extends MenuItem> {
    void validate(T item);
}
