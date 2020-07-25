package com.pizzeria.store.service.impl;

import com.pizzeria.store.entity.Item;
import com.pizzeria.store.entity.Order;
import com.pizzeria.store.exception.InvalidDataException;
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
        List<Item> validatedItems = new LinkedList<>();
        for (Item item : order.getCart().getItems()) {
            validatedItems.add(itemValidatorService.validate(item));
        }
        order.getCart().setItems(validatedItems);
        return order;
    }

    private void validateMandatoryField(Order order) {
        if (order == null || order.getCart() == null || order.getCart().getItems().isEmpty()) {
            throw new InvalidDataException("Invalid Order.");
        }
    }
}
