package com.pizzeria.store.service.impl;

import com.pizzeria.store.dao.ItemDao;
import com.pizzeria.store.dao.impl.ItemDaoImpl;
import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.service.ItemService;
import com.pizzeria.store.utils.ItemUtils;

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
        ItemUtils.isValidItemData(item);
        return itemDao.addItem(item);
    }

    @Override
    public MenuItem updateItem(MenuItem item) {
        ItemUtils.isValidItem(item);
        try {
            LockService.getLock(item.getId()).writeLock().lock();
            return itemDao.updateItem(item);
        } finally {
            LockService.getLock(item.getId()).writeLock().unlock();
        }
    }


    @Override
    public void placeOrderAndUpdateItemInventory(List<MenuItem> items) {
        isValidCart(items);
        List<MenuItem> itemListWithToppingAndCrust = getItemsToUpdateInventory(items);
        try {
            itemListWithToppingAndCrust.forEach(item -> {
                LockService.getLock(item.getId()).writeLock().lock();
                verifyStock(item);
            });
            itemDao.updateQuantity(itemListWithToppingAndCrust);
        } finally {
            itemListWithToppingAndCrust.forEach(item -> LockService.getLock(item.getId()).writeLock().unlock());
        }
    }

    private void isValidCart(List<MenuItem> items) {
        if (items == null || items.isEmpty()) {
            throw new InvalidOrderException("Empty cart.");
        }
    }

    @Override
    public MenuItem validateStock(MenuItem item) {
        ItemUtils.isValidItem(item);
        ItemUtils.isValidField(item.getQuantity());
        try {
            LockService.getLock(item.getId()).readLock().lock();
            MenuItem itemFromDB = verifyStock(item);
            overrideMetaAndPriceFromDB(item, itemFromDB);
            if (item.getType() == MenuItem.Type.PIZZA) {
                validateStockForPizzaToppingAndCrust(item);
            }
        } finally {
            LockService.getLock(item.getId()).readLock().unlock();
        }
        return item;
    }

    private MenuItem verifyStock(MenuItem item) {
        Optional<MenuItem> result = itemDao.getItem(item.getId());
        if (!result.isPresent() || result.get().getQuantity() < item.getQuantity()) {
            throw new InvalidOrderException("Item [" + result.map(value -> "," + value.getName()).orElse("") + "] out of stock!");
        }
        return result.get();
    }

    private void validateStockForPizzaToppingAndCrust(MenuItem item) {
        validateStock(((Pizza) item).getCrust());
        if (item instanceof ToppingDecorator) {
            for (Topping topping : ((ToppingDecorator) item).getToppingList()) {
                validateStock(topping);
            }
        }
    }


    private void overrideMetaAndPriceFromDB(MenuItem item, MenuItem existingItem) {
        item.setName(existingItem.getName());
        item.setDescription(existingItem.getDescription());
        item.setPrice(existingItem.getPrice());
        if (item.getType() == MenuItem.Type.PIZZA){
            ((Pizza) item).setPizzaType(((Pizza)existingItem).getPizzaType());
            ((Pizza) item).setSize(((Pizza)existingItem).getSize());
        } else if (item.getType() == MenuItem.Type.TOPPING){
            ((Topping) item).setToppingType(((Topping)existingItem).getToppingType());
        }
    }

    private List<MenuItem> getItemsToUpdateInventory(List<MenuItem> items) {
        List<MenuItem> result = new LinkedList<>(items);
        items.forEach(item -> {
            if (item.getType() == MenuItem.Type.PIZZA) {
                result.add(((Pizza) item).getCrust());
                if (item instanceof ToppingDecorator) {
                    result.addAll(((ToppingDecorator) item).getToppingList());
                }
            }
        });
        return result;
    }
}
