package com.pizzeria.store.service.impl;

import com.pizzeria.store.businessrule.offer.service.OfferService;
import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Order;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.service.OrderService;
import com.pizzeria.store.service.OrderValidatorService;

public class OrderServiceImpl implements OrderService {

    private static final OrderServiceImpl INSTANCE = new OrderServiceImpl();
    private OrderValidatorService validatorService = OrderValidatorServiceImpl.getInstance();
    private ItemService itemService = ItemServiceImpl.getInstance();
    private OfferService offerService = OfferService.getInstance();

    private OrderServiceImpl() {
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    @Override
    public Order placeOrder(Order order) {
        validatorService.validate(order);
        order.setDiscount(applyOffer(order));
        itemService.placeOrderAndUpdateItemInventory(order.getCart().getItems());
        order.setStatus(Order.Status.PLACED);
        return order;
    }

    private Float applyOffer(Order order) {
        Float discount = OfferService.DEFAULT_DISCOUNT;
        for (MenuItem item : order.getCart().getItems()) {
            discount = discount + offerService.applyOffer(item);
        }
        return discount;
    }

    @Override
    public void validate(Order order) {
        validatorService.validate(order);
        order.setDiscount(applyOffer(order));
    }
}
