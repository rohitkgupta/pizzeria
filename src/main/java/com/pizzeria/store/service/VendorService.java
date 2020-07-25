package com.pizzeria.store.service;

import com.pizzeria.store.entity.Item;

public interface VendorService {
    Item addStock(Integer itemId, Integer quantity);
    Item addItem(Item item);
    Item updatePrice(Integer itemId, Float price);
}
