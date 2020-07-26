package com.pizzeria.store.service;

import com.pizzeria.store.businessrule.validator.service.RuleValidatorService;
import com.pizzeria.store.entity.*;
import com.pizzeria.store.exception.InvalidDataException;
import com.pizzeria.store.exception.InvalidOrderException;
import com.pizzeria.store.service.impl.OrderValidatorServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RuleValidatorService.class)
public class OrderValidatorServiceTest {

    private OrderValidatorService service = null;

    @Before
    public void setup(){
        PowerMockito.mockStatic(RuleValidatorService.class);
        RuleValidatorService mockedRuleValidatorService = Mockito.mock(RuleValidatorService.class);
        PowerMockito.when(RuleValidatorService.getInstance()).thenReturn(mockedRuleValidatorService);
        service = OrderValidatorServiceImpl.getInstance();
    }

    @Test(expected = InvalidDataException.class)
    public void testValidateInvalidInput(){
        service.validate(null);
    }

    @Test(expected = InvalidOrderException.class)
    public void testValidateInvalidOrder(){
        service.validate(new Order(new Cart.Builder().forUser("test").addItem(new Sides(1, 1)).build()));
    }

    @Test
    public void testValidate(){
        Pizza build = new Pizza.Builder().withName("VegPizza").setPrice(10f).setQuantity(1).setPizzaType(Pizza.Type.VEG).setCrust(new Crust(1)).build();
        Order order = new Order(new Cart.Builder().forUser("test").addItem(build).build());
        service.validate(order);
    }

}
