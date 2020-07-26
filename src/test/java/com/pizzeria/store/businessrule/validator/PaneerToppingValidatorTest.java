package com.pizzeria.store.businessrule.validator;

import com.pizzeria.store.businessrule.validator.impl.PaneerToppingValidator;
import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.exception.InvalidOrderException;
import org.junit.Test;

public class PaneerToppingValidatorTest {
    RuleValidator<Pizza> validator = new PaneerToppingValidator();
    private final Pizza nonvegPizza = new Pizza.Builder().withName("NonVegPizza").setPrice(10f).setQuantity(1).setPizzaType(Pizza.Type.NON_VEG).setCrust(new Crust(1)).build();
    private final Topping chickenTopping = new Topping("Chicken", 10f, Topping.Type.NON_VEG, 1);
    private final Topping rostedChickenTopping = new Topping("Rosted Chicken", 15f, Topping.Type.NON_VEG, 1);
    private final Topping tomatoTopping = new Topping("Tomato", 5f, Topping.Type.VEG, 1);
    private final Topping paneerTopping = new Topping("Paneer", 10f, Topping.Type.VEG, 1);
    private final Pizza withVegTopping = new ToppingDecorator(nonvegPizza, tomatoTopping);
    private final Pizza withNonvegTopping = new ToppingDecorator(nonvegPizza, chickenTopping);
    private final Pizza withPaneerAndNonVegToppings = new ToppingDecorator(withVegTopping, paneerTopping);
    private final Pizza withPaneerTopping = new ToppingDecorator(nonvegPizza, paneerTopping);


    @Test(expected = InvalidOrderException.class)
    public void testInvalidPizza() {
        validator.validate(withPaneerTopping);
    }

    @Test(expected = InvalidOrderException.class)
    public void testInvalidPizza1() {
        validator.validate(withPaneerAndNonVegToppings);
    }

    @Test
    public void testValidPizza() {
        validator.validate(withVegTopping);
    }

    @Test
    public void testValidPizza1() {
        validator.validate(withNonvegTopping);
    }

}
