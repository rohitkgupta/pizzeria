package com.pizzeria.store.service;

import com.pizzeria.store.dao.impl.ItemDaoImpl;
import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.entity.MenuItem;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.service.impl.ItemServiceImpl;
import com.pizzeria.store.service.impl.LockService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ItemDaoImpl.class, LockService.class})
public class ItemServiceTest {

    private ItemService service = null;
    private ItemDaoImpl mockedDao = null;
    private Pizza vegPizza = new Pizza.Builder().withName("VegPizza").setPrice(10f).setQuantity(1).setPizzaType(Pizza.Type.VEG).setCrust(new Crust(1)).build();
    private MenuItem outPutPizza = null;
    private ReadWriteLock mockedReadWriteLock = null;
    private Lock mockedReadLock = null;
    private Lock mockedWriteLock = null;

    @Before
    public void setup() {
        PowerMockito.mockStatic(ItemDaoImpl.class);
        mockedDao = Mockito.mock(ItemDaoImpl.class);
        PowerMockito.when(ItemDaoImpl.getInstance()).thenReturn(mockedDao);

        PowerMockito.mockStatic(LockService.class);
        mockedReadWriteLock = Mockito.mock(ReadWriteLock.class);
        PowerMockito.when(LockService.getLock(Matchers.anyInt())).thenReturn(mockedReadWriteLock);
        mockedReadLock = Mockito.mock(Lock.class);
        Mockito.when(mockedReadWriteLock.readLock()).thenReturn(mockedReadLock);
        mockedWriteLock = Mockito.mock(Lock.class);
        Mockito.when(mockedReadWriteLock.writeLock()).thenReturn(mockedWriteLock);

        service = ItemServiceImpl.getInstance();
        outPutPizza = new Pizza(vegPizza);
        outPutPizza.setId(1);

        Mockito.when(mockedDao.addItem(Matchers.any())).thenReturn(outPutPizza);
        HashSet<MenuItem.Type> types = new HashSet<>();
        types.add(MenuItem.Type.PIZZA);
        Mockito.when(mockedDao.getAllItemType()).thenReturn(types);
        Mockito.when(mockedDao.getItem(outPutPizza.getId())).thenReturn(java.util.Optional.ofNullable(outPutPizza));
        Mockito.when(mockedDao.getItems(MenuItem.Type.PIZZA)).thenReturn(Arrays.asList(outPutPizza));
        Mockito.when(mockedDao.updateItem(Matchers.any())).thenReturn(outPutPizza);
    }

    @Test
    public void testGetAllItemType() {
        Assert.assertEquals(1, service.getAllItemType().size());
    }

    @Test(expected = InvalidDataException.class)
    public void testAddItemInvalidInput() {
        service.addItem(new Topping(1));
    }

    @Test
    public void testAddItem() {
        MenuItem addedPizza = service.addItem(vegPizza);
        Assert.assertEquals(outPutPizza.getId(), addedPizza.getId());
        Assert.assertEquals(outPutPizza.getName(), addedPizza.getName());
        Assert.assertEquals(outPutPizza.getPrice(), addedPizza.getPrice());
    }

    @Test(expected = InvalidDataException.class)
    public void testAddSameItemAgain(){
        service.addItem(outPutPizza);
    }

    @Test
    public void testGetItems() {
        List<MenuItem> items = service.getItems(MenuItem.Type.PIZZA);
        Assert.assertEquals(1, items.size());
        Assert.assertEquals(outPutPizza.getName(), items.get(0).getName());
    }

    @Test
    public void testGetItem() {
        Optional<MenuItem> item = service.getItem(outPutPizza.getId());
        Assert.assertTrue(item.isPresent());
        Assert.assertEquals(outPutPizza.getName(), item.get().getName());
        Assert.assertEquals(outPutPizza.getPrice(), item.get().getPrice());
    }

    @Test
    public void testUpdateItem() {
        MenuItem updatedPizza = service.updateItem(new Pizza.Builder(outPutPizza.getId()).setPrice(20f).build());
        Assert.assertEquals(outPutPizza.getPrice(), updatedPizza.getPrice());
    }

    @Test(expected = InvalidOrderException.class)
    public void placeOrderAndUpdateItemInventoryInvalidInput() {
        service.placeOrderAndUpdateItemInventory(Collections.emptyList());
    }

    @Test
    public void testPlaceOrderAndUpdateItemInventory() {
        service.placeOrderAndUpdateItemInventory(Arrays.asList(outPutPizza));
        Mockito.verify(mockedWriteLock, Mockito.times(2)).lock();
        Mockito.verify(mockedWriteLock, Mockito.times(2)).unlock();
    }

    @Test
    public void testValidateStock() {
        service.validateStock(outPutPizza);
        Mockito.verify(mockedReadLock, Mockito.times(2)).lock();
        Mockito.verify(mockedReadLock, Mockito.times(2)).unlock();
    }
}
