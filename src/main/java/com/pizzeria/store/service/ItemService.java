package com.pizzeria.store.service;

import com.pizzeria.store.entity.Item;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ItemService {
    Set<Item.Type> getAllItemType();
    List<Item> getItems(Item.Type type);
    Optional<Item> getItem(Integer id);
    Item addItem(Item item);
    Item updateItem(Item item);
    List<Item> placeOrderAndUpdateItemInventory(List<Item> items);
}
