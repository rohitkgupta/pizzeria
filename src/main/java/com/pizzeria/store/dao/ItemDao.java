package com.pizzeria.store.dao;

import com.pizzeria.store.entity.MenuItem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemDao {

    Set<MenuItem.Type> getAllItemType();
    List<MenuItem> getItems(MenuItem.Type type);
    Optional<MenuItem> getItem(Integer id);
    Optional<MenuItem> getItem(MenuItem.Type type, String name);
    MenuItem addItem(MenuItem item);
    MenuItem updateItem(MenuItem item);
    List<MenuItem> updateQuantity(List<MenuItem> items);
}
