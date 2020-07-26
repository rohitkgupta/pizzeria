package com.pizzeria.store.dao.impl;

import com.pizzeria.store.dao.ItemDao;
import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.utils.ItemUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ItemDaoImpl implements ItemDao {
    //It will have all types of item(Pizza, toppings, sides, Crust) with array index as ID
    private final List<MenuItem> itemTable = new ArrayList<>();
    //Index to store list of ids of same type of item for faster retrieval
    private final Map<MenuItem.Type, List<Integer>> typeIndex = new EnumMap<>(MenuItem.Type.class);

    private static final ItemDaoImpl INSTANCE = new ItemDaoImpl();

    private ItemDaoImpl() {
    }

    public static ItemDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public MenuItem addItem(MenuItem item) {
        if (item != null && item.getType() != null) {
            synchronized (itemTable) {
                addItemToList(item);
                updateIndex(item);
            }
            return ItemUtils.getCopy(item);
        }
        return null;
    }

    private void addItemToList(MenuItem item) {
        item.setId(itemTable.size());
        itemTable.add(item);
    }

    @Override
    public Set<MenuItem.Type> getAllItemType() {
        return typeIndex.keySet();
    }

    @Override
    public List<MenuItem> getItems(MenuItem.Type type) {
        if (typeIndex.containsKey(type)) {
            return typeIndex.get(type).stream().map(itemId -> getItem(itemId).get()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<MenuItem> getItem(Integer id) {
        if (id < itemTable.size()) {
            MenuItem item = itemTable.get(id);
            return Optional.of(ItemUtils.getCopy(item));
        }
        return Optional.empty();
    }

    @Override
    public MenuItem updateItem(MenuItem item) {
        if (item != null && item.getId() != null && item.getId() < itemTable.size()) {
            MenuItem existingItem = itemTable.get(item.getId());
            if (item.getPrice() != null) {
                existingItem.setPrice(item.getPrice());
            }
            if (item.getQuantity() != null) {
                existingItem.setQuantity(item.getQuantity());
            }
            return ItemUtils.getCopy(existingItem);
        }
        throw null;
    }

    @Override
    public List<MenuItem> updateQuantity(List<MenuItem> items) {
        if (items != null) {
            List<MenuItem> result = new LinkedList<>();
            items.forEach(item -> {
                MenuItem existingItem = itemTable.get(item.getId());
                if (item.getQuantity() != null) {
                    existingItem.setQuantity(existingItem.getQuantity() - item.getQuantity());
                }
                result.add(ItemUtils.getCopy(existingItem));
            });
            return result;
        }
        throw null;
    }

    private void updateIndex(MenuItem item) {
        if (!typeIndex.containsKey(item.getType())) {
            typeIndex.put(item.getType(), new ArrayList<>());
        }
        typeIndex.get(item.getType()).add(item.getId());
    }
}
