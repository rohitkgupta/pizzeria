package com.pizzeria.store.dao.impl;

import com.pizzeria.store.dao.ItemDao;
import com.pizzeria.store.entity.Item;
import com.pizzeria.store.exception.InvalidDataException;

import java.util.*;

public class ItemDaoImpl implements ItemDao {

    private static final ItemDaoImpl INSTANCE = new ItemDaoImpl();
    //Item table. It will have all types of item(Pizza, toppings, sides)
    private List<Item> items = new ArrayList<>();
    //Type index to store list of indexes of same type of item
    private Map<Item.Type, List<Integer>> typeIndex = new HashMap<>();

    private ItemDaoImpl() {
    }

    public static ItemDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Set<Item.Type> getAllItemType() {
        return typeIndex.keySet();
    }

    @Override
    public List<Item> getItems(Item.Type type) {
        List<Item> result = Collections.emptyList();
        if (typeIndex.containsKey(type)) {
            result = new ArrayList<>();
            for (Integer index : typeIndex.get(type)) {
                result.add(items.get(index));
            }
        }
        return result;
    }

    @Override
    public Optional<Item> getItem(Integer id) {
        if (id < items.size()) {
            return Optional.of(items.get(id));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Item> getItem(Item.Type type, String name) {
        for (Item item : getItems(type)) {
            if (item.getName().equals(name)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    /*@Override
    public Item saveItem(Item item) {
        //If id==null then add as new item
        if (item.getId() == null) {
            return addItem(item);
        } else if (item.getId() < items.size()) {
            return updateItem(item);
        } else {
            throw new InvalidDataException("Item doesn't exist for id:" + item.getId());
        }
    } */

    @Override
    public Item updateItem(Item item) {
        if (item != null && item.getId() != null && item.getId() < items.size()) {
            Item existingItem = items.get(item.getId());
            if (item.getPrice() != null) {
                existingItem.setPrice(item.getPrice());
            }
            if (item.getQuantity() != null) {
                existingItem.setQuantity(item.getQuantity());
            }
            return existingItem;
        }
        throw null;
    }

    @Override
    public Item addItem(Item item) {
        if (item != null && item.getType() != null) {
            synchronized (items) {
                item.setId(items.size());
                items.add(item);
                updateIndex(item);
            }
            return item;
        }
        return null;
    }

    private void updateIndex(Item item) {
        if (!typeIndex.containsKey(item.getType())) {
            typeIndex.put(item.getType(), new ArrayList<>());
        }
        typeIndex.get(item.getType()).add(item.getId());
    }
}
