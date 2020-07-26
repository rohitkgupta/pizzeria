package com.pizzeria.store.service.impl;

import com.pizzeria.store.dao.ItemDao;
import com.pizzeria.store.dao.impl.ItemDaoImpl;
import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
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
    public Set<MenuItem.Type> getAllItemType() {
        return itemDao.getAllItemType();
    }

    @Override
    public List<MenuItem> getItems(MenuItem.Type type) {
        return itemDao.getItems(type);
    }

    @Override
    public Optional<MenuItem> getItem(Integer id) {
        try {
            LockService.getLock(id).readLock().lock();
            return itemDao.getItem(id);
        } finally {
            LockService.getLock(id).readLock().unlock();
        }
    }

    @Override
    public MenuItem addItem(MenuItem item) {
        isValidItemData(item);
        return new MenuItem(itemDao.addItem(item));
    }

    private void isValidItemData(MenuItem item) {
        if (item == null || item.getType() == null || item.getName() == null || item.getQuantity() == null || item.getPrice() == null) {
            throw new InvalidDataException("Invalid data.");
        }
    }

    @Override
    public MenuItem updateItem(MenuItem item) {
        isValidItem(item);
        try {
            LockService.getLock(item.getId()).writeLock().lock();
            return new MenuItem(itemDao.updateItem(item));
        } finally {
            LockService.getLock(item.getId()).writeLock().unlock();
        }
    }

    private void isValidItem(MenuItem item) {
        if (item == null || item.getId() == null) {
            throw new InvalidDataException("Invalid data.");
        }
    }

    @Override
    public List<MenuItem> placeOrderAndUpdateItemInventory(List<MenuItem> items) {
        if (items != null && !items.isEmpty()) {
            List<MenuItem> itemListWithToppingAndCrust = getItemsToUpdateInventory(items);
            try {
                for (MenuItem item : itemListWithToppingAndCrust) {
                    LockService.getLock(item.getId()).writeLock().lock();
                    validateStock(item);
                }
                return itemDao.updateQuantity(itemListWithToppingAndCrust);
            } finally {
                for (MenuItem item : itemListWithToppingAndCrust) {
                    LockService.getLock(item.getId()).writeLock().unlock();
                }
            }
        }
        throw new InvalidOrderException("Empty cart.");
    }

    @Override
    public MenuItem validateStock(MenuItem item) {
        if (item != null && item.getId() != null && item.getQuantity() != null) {
            Optional<MenuItem> result = itemDao.getItem(item.getId());
            if (!result.isPresent() || result.get().getQuantity() < item.getQuantity()) {
                throw new InvalidOrderException("Item [" + item.getId() + result.map(value -> "," + value.getName()).orElse("") + "] out of stock!");
            }
            copyMetaAndPriceFromDB(item, result.get());
            if (item.getType() == MenuItem.Type.PIZZA){
                if(item instanceof ToppingDecorator) {
                    validateStock(((Pizza) item).getCrust());
                    for (Topping topping : ((ToppingDecorator) item).getToppingList()) {
                        copyMetaAndPriceFromDB(topping, validateStock(topping));
                    }
                }
            }
        } else {
            throw new InvalidDataException("Invalid item id or quantity");
        }
        return item;
    }

    private void copyMetaAndPriceFromDB(MenuItem item, MenuItem existingItem) {
        item.setName(existingItem.getName());
        item.setDescription(existingItem.getDescription());
        item.setPrice(existingItem.getPrice());
    }

    private List<MenuItem> getItemsToUpdateInventory(List<MenuItem> items) {
        List<MenuItem> result = new LinkedList<>(items);
        items.forEach(item -> {
            if(item.getType() == MenuItem.Type.PIZZA){
                result.add(((Pizza)item).getCrust());
                if (item instanceof ToppingDecorator) {
                    result.addAll(((ToppingDecorator) item).getToppingList());
                }
            }
        });
        return result;
    }
}
