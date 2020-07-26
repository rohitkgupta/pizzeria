package com.pizzeria.store.dao.impl;

import com.pizzeria.store.dao.ItemDao;
import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;

import java.util.*;

public class ItemDaoImpl implements ItemDao {

    private static final ItemDaoImpl INSTANCE = new ItemDaoImpl();
    //Item table. It will have all types of item(Pizza, toppings, sides)
    private List<MenuItem> itemTable = new ArrayList<>();
    //Type index to store list of indexes of same type of item
    private Map<MenuItem.Type, List<Integer>> typeIndex = new HashMap<>();

    private ItemDaoImpl() {
    }

    public static ItemDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Set<MenuItem.Type> getAllItemType() {
        return typeIndex.keySet();
    }

    @Override
    public List<MenuItem> getItems(MenuItem.Type type) {
        List<MenuItem> result = Collections.emptyList();
        if (typeIndex.containsKey(type)) {
            result = new ArrayList<>();
            for (Integer index : typeIndex.get(type)) {
                result.add(getItem(index).get());
            }
        }
        return result;
    }

    @Override
    public Optional<MenuItem> getItem(Integer id) {
        if (id < itemTable.size()) {
            MenuItem item = itemTable.get(id);
            if (item instanceof Pizza){
                return Optional.of(new Pizza((Pizza)item));
            } else if (item instanceof Topping){
                return Optional.of(new Topping((Topping) item));
            }
            return Optional.of(new MenuItem(item));
        }
        return Optional.empty();
    }

    @Override
    public Optional<MenuItem> getItem(MenuItem.Type type, String name) {
        for (MenuItem item : getItems(type)) {
            if (item.getName().equals(name)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    @Override
    public MenuItem updateItem(MenuItem item) {
        if (item != null && item.getId() != null && item.getId() <  itemTable.size()) {
            MenuItem existingItem = itemTable.get(item.getId());
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
    public List<MenuItem> updateQuantity(List<MenuItem> items) {
        if (items != null) {
            List<MenuItem> result = new LinkedList<>();
            items.forEach(item -> {
                MenuItem existingItem = itemTable.get(item.getId());
                if (item.getQuantity() != null) {
                    existingItem.setQuantity(existingItem.getQuantity() - item.getQuantity());
                }
                result.add(existingItem);
            });
            return result;
        }
        throw null;
    }

    @Override
    public MenuItem addItem(MenuItem item) {
        if (item != null && item.getType() != null) {
            synchronized (itemTable) {
                item.setId(itemTable.size());
                itemTable.add(item);
                updateIndex(item);
            }
            return item;
        }
        return null;
    }

    private void updateIndex(MenuItem item) {
        if (!typeIndex.containsKey(item.getType())) {
            typeIndex.put(item.getType(), new ArrayList<>());
        }
        typeIndex.get(item.getType()).add(item.getId());
    }
}
