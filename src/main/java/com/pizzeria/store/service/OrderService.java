package com.pizzeria.store.service;

import com.pizzeria.store.entity.Order;

public interface OrderService {
    Order placeOrder(Order order);
    Order validate(Order order);
}
