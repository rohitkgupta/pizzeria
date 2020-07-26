package com.pizzeria.store.service.impl;

import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Order;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.businessrule.validator.service.RuleValidatorService;
import com.pizzeria.store.service.OrderValidatorService;

public class OrderValidatorServiceImpl implements OrderValidatorService {
    private static final OrderValidatorService INSTANCE = new OrderValidatorServiceImpl();
    private RuleValidatorService ruleValidatorService = RuleValidatorService.getInstance();

    private OrderValidatorServiceImpl() {}

    public static OrderValidatorService getInstance(){
        return INSTANCE;
    }

    @Override
    public void validate(Order order) {
        validateMandatoryField(order);
        isValidPizzaOrder(order);
        order.getCart().getItems().forEach(item -> ruleValidatorService.validate(item));
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
