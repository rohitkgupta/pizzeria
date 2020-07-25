package com.pizzeria.store.service;

import com.pizzeria.store.entity.Order;

public interface OrderValidatorService {
    Order validate(Order order);
}
