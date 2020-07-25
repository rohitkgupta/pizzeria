package com.pizzeria.store.dao;

import com.pizzeria.store.entity.Item;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemDao {

    Set<Item.Type> getAllItemType();
    List<Item> getItems(Item.Type type);
    Optional<Item> getItem(Integer id);
    Optional<Item> getItem(Item.Type type, String name);
    Item addItem(Item item);
    Item updateItem(Item item);
    List<Item> updateQuantity(List<Item> items);
}
