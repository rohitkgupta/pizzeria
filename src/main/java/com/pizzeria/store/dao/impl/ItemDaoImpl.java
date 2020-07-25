package com.pizzeria.store.dao.impl;

import com.pizzeria.store.dao.ItemDao;
import com.pizzeria.store.entity.Item;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;

import java.util.*;

public class ItemDaoImpl implements ItemDao {

    private static final ItemDaoImpl INSTANCE = new ItemDaoImpl();
    //Item table. It will have all types of item(Pizza, toppings, sides)
    private List<Item> itemTable = new ArrayList<>();
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
                result.add(getItem(index).get());
            }
        }
        return result;
    }

    @Override
    public Optional<Item> getItem(Integer id) {
        if (id < itemTable.size()) {
            Item item = itemTable.get(id);
            if (item instanceof Pizza){
                return Optional.of(new Pizza((Pizza)item));
            } else if (item instanceof Topping){
                return Optional.of(new Topping((Topping) item));
            }
            return Optional.of(new Item(item));
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

    @Override
    public Item updateItem(Item item) {
        if (item != null && item.getId() != null && item.getId() <  itemTable.size()) {
            Item existingItem = itemTable.get(item.getId());
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
    public List<Item> updateQuantity(List<Item> items) {
        if (items != null) {
            List<Item> result = new LinkedList<>();
            for (Item item: items) {
                Item existingItem = itemTable.get(item.getId());
                if (item.getQuantity() != null) {
                    existingItem.setQuantity(existingItem.getQuantity() - item.getQuantity());
                }
                result.add(existingItem);
            }
            return result;
        }
        throw null;
    }

    @Override
    public Item addItem(Item item) {
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

    private void updateIndex(Item item) {
        if (!typeIndex.containsKey(item.getType())) {
            typeIndex.put(item.getType(), new ArrayList<>());
        }
        typeIndex.get(item.getType()).add(item.getId());
    }
}
