package com.pizzeria.store.businessrule.validator;

import com.pizzeria.store.businessrule.validator.impl.PizzaCrustValidator;
import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.exception.InvalidOrderException;
import org.junit.Test;

public class PizzaCrustValidatorTest {
    RuleValidator<Pizza> validator = new PizzaCrustValidator();
    private final Pizza vegPizzaWithoutCrust = new Pizza("Farmhouse Pizza", 200f, Pizza.Size.MEDIUM, Pizza.Type.VEG, 2);
    private final Topping chickenTopping = new Topping("Chicken", 10f, Topping.Type.NON_VEG, 1);
    private final Topping tomatoTopping = new Topping("Tomato", 5f, Topping.Type.VEG, 1);
    private final Pizza withVegTopping = new ToppingDecorator(vegPizzaWithoutCrust, tomatoTopping);
    private final Pizza pizzWithCrust = new Pizza.Builder().withName("VegPizza").setPrice(10f).setQuantity(1).setPizzaType(Pizza.Type.VEG).setCrust(new Crust(1)).build();




    @Test(expected = InvalidOrderException.class)
    public void testInvalidPizzaWithNoCrust(){
        validator.validate(vegPizzaWithoutCrust);
    }

    @Test(expected = InvalidOrderException.class)
    public void testInvalidPizzaWithNoCrust1(){
        validator.validate(withVegTopping);
    }

    @Test
    public void testValidPizza(){
        validator.validate(pizzWithCrust);
    }

    @Test(expected = InvalidOrderException.class)
    public void testInvalidPizzaWithMultipleCrust(){
        Pizza pizzWithCrust = new Pizza.Builder().withName("VegPizza").setPrice(10f).setQuantity(1).setPizzaType(Pizza.Type.VEG).setCrust(new Crust(1)).build();
        pizzWithCrust.getCrust().setQuantity(2);
        validator.validate(pizzWithCrust);
    }

}
