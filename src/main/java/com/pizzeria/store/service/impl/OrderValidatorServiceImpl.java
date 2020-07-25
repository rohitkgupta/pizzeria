package com.pizzeria.store.service.impl;

import com.pizzeria.store.entity.Item;
import com.pizzeria.store.entity.Order;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.rule.service.ItemValidatorService;
import com.pizzeria.store.service.OrderValidatorService;

import java.util.LinkedList;
import java.util.List;

public class OrderValidatorServiceImpl implements OrderValidatorService {

    private ItemValidatorService itemValidatorService = ItemValidatorService.getInstance();

    @Override
    public void validate(Order order) {
        if (order == null || order.getCart() == null || order.getCart().getItems().isEmpty()) {
            throw new InvalidDataException("Invalid Order.");
        }
        List<Item> validatedItems = new LinkedList<>();
        for (Item item : order.getCart().getItems()) {
            validatedItems.add(itemValidatorService.validate(item));
        }
        order.getCart().setItems(validatedItems);
    }
}
