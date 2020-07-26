package com.pizzeria.store.businessrule.validator;

import com.pizzeria.store.businessrule.validator.impl.NonVegPizzaToppingValidator;
import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.exception.InvalidOrderException;
import org.junit.Test;

public class NonVegPizzaToppingValidatorTest {
    RuleValidator<Pizza> validator = new NonVegPizzaToppingValidator();
    private final Pizza nonvegPizza = new Pizza.Builder().withName("NonVegPizza").setPrice(10f).setQuantity(1).setPizzaType(Pizza.Type.NON_VEG).setCrust(new Crust(1)).build();
    private final Topping chickenTopping = new Topping("Chicken", 10f, Topping.Type.NON_VEG, 1);
    private final Topping rostedChickenTopping = new Topping("Rosted Chicken", 15f, Topping.Type.NON_VEG, 1);
    private final Topping tomatoTopping = new Topping("Tomato", 5f, Topping.Type.VEG, 1);
    private final Pizza withVegTopping = new ToppingDecorator(nonvegPizza, tomatoTopping);
    private final Pizza withNonvegTopping = new ToppingDecorator(nonvegPizza, chickenTopping);
    private final Pizza withVegAndNonVegToppings = new ToppingDecorator(withVegTopping, chickenTopping);
    private final Pizza withTwoNonvegTopping = new ToppingDecorator(withNonvegTopping, rostedChickenTopping);
    private final Pizza withVegAndTwoNonvegTopping = new ToppingDecorator(withVegAndNonVegToppings, rostedChickenTopping);



    @Test(expected = InvalidOrderException.class)
    public void testInvalidPizza(){
        validator.validate(withTwoNonvegTopping);
    }

    @Test(expected = InvalidOrderException.class)
    public void testInvalidPizza1(){
        validator.validate(withVegAndTwoNonvegTopping);
    }

    @Test
    public void testValidPizza(){
        validator.validate(withVegTopping);
    }

    @Test
    public void testValidPizza1(){
        validator.validate(withNonvegTopping);
    }

    @Test
    public void testValidPizza2(){
        validator.validate(withVegAndNonVegToppings);
    }
}
