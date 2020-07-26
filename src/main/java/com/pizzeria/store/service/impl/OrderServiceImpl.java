package com.pizzeria.store.service.impl;

import com.pizzeria.store.entity.Order;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.service.OrderService;
import com.pizzeria.store.service.OrderValidatorService;

public class OrderServiceImpl implements OrderService {

    private static final OrderServiceImpl INSTANCE = new OrderServiceImpl();
    private OrderValidatorService validatorService = OrderValidatorServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();

    private OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    @Override
    public Order placeOrder(Order order) {
        validatorService.validate(order);
        order.getCart().setItems(itemService.placeOrderAndUpdateItemInventory(order.getCart().getItems()));
        order.setStatus(Order.Status.PLACED);
        return order;
    }

    @Override
    public Order validate(Order order) {
        return validatorService.validate(order);
    }
}
