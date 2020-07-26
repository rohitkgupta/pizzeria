package com.pizzeria.store.service;

import com.pizzeria.store.entity.Order;

public interface OrderValidatorService {
    void validate(Order order);
}
