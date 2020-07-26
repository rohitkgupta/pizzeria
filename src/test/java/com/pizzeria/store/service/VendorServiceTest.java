package com.pizzeria.store.service;

import com.pizzeria.store.entity.Sides;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.service.impl.ItemServiceImpl;
import com.pizzeria.store.service.impl.VendorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ItemServiceImpl.class)
public class VendorServiceTest {

    private VendorService service = null;
    private Sides notAvailableItem = null;
    private Sides availableItem = null;
    private ItemServiceImpl mockedItemService = null;

    @Before
    public void setup(){
        PowerMockito.mockStatic(ItemServiceImpl.class);
        mockedItemService = Mockito.mock(ItemServiceImpl.class);
        PowerMockito.when(ItemServiceImpl.getInstance()).thenReturn(mockedItemService);

        service = VendorServiceImpl.getInstance();

        notAvailableItem = new Sides(1, 1);
        availableItem = new Sides(2, 1);
        availableItem.setName("Coke");
        Mockito.when(mockedItemService.getItem(availableItem.getId())).thenReturn(Optional.of(availableItem));
        Mockito.when(mockedItemService.getItem(notAvailableItem.getId())).thenReturn(Optional.empty());
        Mockito.when(mockedItemService.updateItem(availableItem)).thenReturn(availableItem);
    }

    @Test(expected = InvalidDataException.class)
    public void testRestockInvalidInput(){
        service.restockItem(notAvailableItem.getId(), 1);
    }

    @Test
    public void testRestockValidInput(){
        service.restockItem(availableItem.getId(), 1);
        //Mockito.verify(mockedItemService, Mockito.times(1)).updateItem(availableItem);
    }

    @Test(expected = InvalidDataException.class)
    public void testUpdatePriceInvalidInput(){
        service.updatePrice(notAvailableItem.getId(), 10f);
    }

    @Test
    public void testUpdatePriceValidInput(){
        service.updatePrice(availableItem.getId(), 10f);
        //Mockito.verify(mockedItemService, Mockito.times(1)).updateItem(availableItem);
    }

}
