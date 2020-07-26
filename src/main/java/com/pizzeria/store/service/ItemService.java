package com.pizzeria.store.service;

import com.pizzeria.store.entity.MenuItem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemService {
    Set<MenuItem.Type> getAllItemType();
    List<MenuItem> getItems(MenuItem.Type type);
    Optional<MenuItem> getItem(Integer id);
    MenuItem addItem(MenuItem item);
    MenuItem updateItem(MenuItem item);
    void placeOrderAndUpdateItemInventory(List<MenuItem> items);
    MenuItem validateStock(MenuItem item);
    Optional<MenuItem> getItem(MenuItem.Type type, String name);
}
