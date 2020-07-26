package com.pizzeria.store.service;

import com.pizzeria.store.businessrule.offer.service.OfferService;
import com.pizzeria.store.entity.*;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.service.impl.ItemServiceImpl;
import com.pizzeria.store.service.impl.OrderServiceImpl;
import com.pizzeria.store.service.impl.OrderValidatorServiceImpl;
import com.pizzeria.store.service.impl.VendorServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ItemServiceImpl.class, OrderValidatorServiceImpl.class, OfferService.class})
public class OrderServiceTest {

    private OrderService service = null;
    private ItemServiceImpl mockedItemService = null;
    private OrderValidatorServiceImpl mockedOrderValidatorServiceImpl = null;
    private OfferService mockedOfferService = null;
    private Order validOrder = null;
    private Order invalidOrder = null;

    @Before
    public void setup(){
        PowerMockito.mockStatic(ItemServiceImpl.class);
        mockedItemService = Mockito.mock(ItemServiceImpl.class);
        PowerMockito.when(ItemServiceImpl.getInstance()).thenReturn(mockedItemService);

        PowerMockito.mockStatic(OrderValidatorServiceImpl.class);
        mockedOrderValidatorServiceImpl = Mockito.mock(OrderValidatorServiceImpl.class);
        PowerMockito.when(OrderValidatorServiceImpl.getInstance()).thenReturn(mockedOrderValidatorServiceImpl);

        PowerMockito.mockStatic(OfferService.class);
        mockedOfferService = Mockito.mock(OfferService.class);
        PowerMockito.when(OfferService.getInstance()).thenReturn(mockedOfferService);

        service = OrderServiceImpl.getInstance();
        Cart.Builder cartBuilder = new Cart.Builder();
        cartBuilder.forUser("test");
        Pizza pizza = new Pizza.Builder().withName("VegPizza").setPrice(10f).setSize(Pizza.Size.LARGE).setQuantity(1).setPizzaType(Pizza.Type.VEG).setCrust(new Crust(1)).build();
        cartBuilder.addItem(pizza);
        validOrder = new Order(cartBuilder.build());

        invalidOrder = new Order(null);
        Mockito.doThrow(new InvalidOrderException("invalid order")).when(mockedOrderValidatorServiceImpl).validate(invalidOrder);
    }

    @Test(expected = InvalidOrderException.class)
    public void testPlaceOrderInvalidOrderCase(){
        service.placeOrder(new Order(null));
    }

    @Test
    public void testPlaceOrder(){
        Assert.assertEquals(Order.Status.PLACED, service.placeOrder(validOrder).getStatus());
    }


}
