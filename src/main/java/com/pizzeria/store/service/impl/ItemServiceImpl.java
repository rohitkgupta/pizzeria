package com.pizzeria.store.service.impl;

import com.pizzeria.store.dao.ItemDao;
import com.pizzeria.store.dao.impl.ItemDaoImpl;
import com.pizzeria.store.entity.Item;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.ToppingDecorator;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.service.ItemService;

import java.util.LinkedList;
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
        if (items != null && !items.isEmpty()) {
            List<Item> itemListWithTopping = addToppingsInItemList(items);
            try {
                for (Item item : itemListWithTopping) {
                    LockService.getLock(item.getId()).writeLock().lock();
                    validateStock(item);
                }
                return itemDao.updateQuantity(itemListWithTopping);
            } finally {
                for (Item item : itemListWithTopping) {
                    LockService.getLock(item.getId()).writeLock().unlock();
                }
            }
        }
        throw new InvalidOrderException("Empty cart.");
    }

    @Override
    public Item validateStock(Item item) {
        if (item != null && item.getId() != null && item.getQuantity() != null) {
            Optional<Item> result = itemDao.getItem(item.getId());
            if (!result.isPresent() || result.get().getQuantity() < item.getQuantity()) {
                throw new InvalidOrderException("Item [" + item.getId() + result.map(value -> "," + value.getName()).orElse("") + "] out of stock!");
            }
            copyMetaAndPriceFromDB(item, result.get());
            if (item.getType() == Item.Type.PIZZA && ((Pizza)item) instanceof ToppingDecorator) {
                for (Topping topping : ((ToppingDecorator) item).getToppingList()) {
                    copyMetaAndPriceFromDB(topping, validateStock(topping));
                }
            }
        } else {
            throw new InvalidDataException("Invalid item id or quantity");
        }
        return item;
    }

    private void copyMetaAndPriceFromDB(Item item, Item existingItem) {
        item.setName(existingItem.getName());
        item.setDescription(existingItem.getDescription());
        item.setPrice(existingItem.getPrice());
    }

    private List<Item> addToppingsInItemList(List<Item> items) {
        List<Item> itemListWithTopping = new LinkedList<>(items);
        for (Item item : items) {
            if (item instanceof ToppingDecorator) {
                itemListWithTopping.addAll(((ToppingDecorator) item).getToppingList());
            }
        }
        return itemListWithTopping;
    }
}
