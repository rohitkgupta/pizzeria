package com.pizzeria.store.service.impl;

import com.pizzeria.store.dao.ItemDao;
import com.pizzeria.store.dao.impl.ItemDaoImpl;
import com.pizzeria.store.entity.Item;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.service.ItemService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ItemServiceImpl implements ItemService {
    private ItemDao itemDao = ItemDaoImpl.getInstance();
    private static final ItemServiceImpl INSTANCE = new ItemServiceImpl();

    private ItemServiceImpl() {}

    public static ItemServiceImpl getInstance(){
        return INSTANCE;
    }

    @Override
    public Set<Item.Type> getAllItemType() {
        return null;
    }

    @Override
    public List<Item> getItems(Item.Type type) {
        return null;
    }

    @Override
    public Optional<Item> getItem(Integer id) {
        LockService.getLock(id).readLock();
        return itemDao.getItem(id);
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
        return itemDao.updateItem(item);
    }
}
