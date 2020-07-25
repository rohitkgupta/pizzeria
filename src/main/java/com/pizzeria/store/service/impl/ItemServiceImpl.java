package com.pizzeria.store.service.impl;

import com.pizzeria.store.dao.ItemDao;
import com.pizzeria.store.dao.impl.ItemDaoImpl;
import com.pizzeria.store.entity.Item;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.service.ItemService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ItemServiceImpl implements ItemService {
    private ItemDao itemDao = ItemDaoImpl.getInstance();
    private static final ItemServiceImpl INSTANCE = new ItemServiceImpl();

    private ItemServiceImpl() {
    }

    public static ItemServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Set<Item.Type> getAllItemType() {
        return itemDao.getAllItemType();
    }

    @Override
    public List<Item> getItems(Item.Type type) {
        return itemDao.getItems(type);
    }

    @Override
    public Optional<Item> getItem(Integer id) {
        try {
            LockService.getLock(id).readLock().lock();
            return itemDao.getItem(id);
        } finally {
            LockService.getLock(id).readLock().unlock();
        }
    }

    @Override
    public Item addItem(Item item) {
        if (item == null || item.getType() == null || item.getName() == null || item.getQuantity() == null || item.getPrice() == null) {
            throw new InvalidDataException("Invalid data.");
        }
        return itemDao.addItem(item);
    }

    @Override
    public Item updateItem(Item item) {
        if (item == null || item.getId() == null) {
            throw new InvalidDataException("Invalid data.");
        }
        try {
            LockService.getLock(item.getId()).writeLock().lock();
            return itemDao.updateItem(item);
        } finally {
            LockService.getLock(item.getId()).writeLock().unlock();
        }
    }

    @Override
    public List<Item> placeOrderAndUpdateItemInventory(List<Item> items) {
        if (items != null && items.isEmpty()) {
            for (Item item : items) {
                if (item == null || item.getId() == null || item.getQuantity() <= 0) {
                    throw new InvalidDataException("Invalid order.");
                }
            }
            try {
                for (Item item : items) {
                    LockService.getLock(item.getId()).writeLock().lock();
                    Optional<Item> existingItem = itemDao.getItem(item.getId());
                    if (!existingItem.isPresent() || existingItem.get().getQuantity() < item.getQuantity()) {
                        throw new InvalidOrderException("Item [" + item.getId() + existingItem.map(value -> "," + value.getName()).orElse("") + "] out of stock!");
                    }
                }
                return itemDao.updateQuantity(items);
            } finally {
                for (Item item : items) {
                    LockService.getLock(item.getId()).writeLock().unlock();
                }
            }
        }
        return items;
    }
}
