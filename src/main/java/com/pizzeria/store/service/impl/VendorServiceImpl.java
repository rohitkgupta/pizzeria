package com.pizzeria.store.service.impl;

import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.service.VendorService;

import java.util.Optional;

public class VendorServiceImpl implements VendorService {
    private ItemService itemService = ItemServiceImpl.getInstance();
    private static final VendorServiceImpl INSTANCE = new VendorServiceImpl();

    private VendorServiceImpl() {
    }

    public static VendorService getInstance() {
        return INSTANCE;
    }

    @Override
    public MenuItem addStock(Integer itemId, Integer quantity) {
        Optional<MenuItem> result = itemService.getItem(itemId);
        if (result.isPresent()) {
            MenuItem item = result.get();
            item.setQuantity(item.getQuantity() + quantity);
            itemService.updateItem(item);
            return item;
        }
        throw new InvalidDataException("Item not found for id:" + itemId);
    }

    @Override
    public MenuItem addItem(MenuItem item) {
        return itemService.addItem(item);
    }

    @Override
    public MenuItem updatePrice(Integer itemId, Float price) {
        Optional<MenuItem> result = itemService.getItem(itemId);
        if (result.isPresent()) {
            MenuItem item = result.get();
            item.setPrice(price);
            itemService.updateItem(item);
            return item;
        }
        throw new InvalidDataException("Item not found for id:" + itemId);
    }
}
