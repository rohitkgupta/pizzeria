package com.pizzeria.store.service;

import com.pizzeria.store.entity.MenuItem;

public interface VendorService {
    MenuItem restockItem(Integer itemId, Integer quantity);
    MenuItem addItem(MenuItem item);
    MenuItem updatePrice(Integer itemId, Float price);
}
