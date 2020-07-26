package com.pizzeria.store.rule;

import com.pizzeria.store.entity.MenuItem;

public interface RuleValidator {
    MenuItem validate(MenuItem item);
}
