package com.pizzeria.store.service.impl;

import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Order;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.rule.service.ItemValidatorService;
import com.pizzeria.store.service.OrderValidatorService;

import java.util.LinkedList;
import java.util.List;

public class OrderValidatorServiceImpl implements OrderValidatorService {
    private static final OrderValidatorService INSTANCE = new OrderValidatorServiceImpl();
    private ItemValidatorService itemValidatorService = ItemValidatorService.getInstance();

    private OrderValidatorServiceImpl() {}

    public static OrderValidatorService getInstance(){
        return INSTANCE;
    }

    @Override
    public Order validate(Order order) {
        validateMandatoryField(order);
        isValidPizzaOrder(order);
        List<MenuItem> validatedItems = new LinkedList<>();
        for (MenuItem item : order.getCart().getItems()) {
            validatedItems.add(itemValidatorService.validate(item));
        }
        order.getCart().setItems(validatedItems);
        return order;
    }

    private void isValidPizzaOrder(Order order) {
        if (order.getCart().getItems().stream().filter(item -> item.getType() == MenuItem.Type.PIZZA).count() == 0){
            throw new InvalidOrderException("Order should have atleast one pizza.");
        }
    }

    private void validateMandatoryField(Order order) {
        if (order == null || order.getCart() == null || order.getCart().getItems().isEmpty()) {
            throw new InvalidDataException("Invalid Order.");
        }
    }
}
