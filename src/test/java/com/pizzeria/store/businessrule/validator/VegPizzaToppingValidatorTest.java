package com.pizzeria.store.businessrule.validator;

import com.pizzeria.store.businessrule.validator.impl.NonVegPizzaToppingValidator;
import com.pizzeria.store.businessrule.validator.impl.VegPizzaToppingValidator;
import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import com.pizzeria.store.exception.InvalidOrderException;
import org.junit.Test;

public class VegPizzaToppingValidatorTest {
    RuleValidator<Pizza> validator = new VegPizzaToppingValidator();
    private final Pizza vegPizza = new Pizza("Farmhouse Pizza", 200f, Pizza.Size.MEDIUM, Pizza.Type.VEG, 2);
    private final Topping chickenTopping = new Topping("Chicken", 10f, Topping.Type.NON_VEG, 1);
    private final Topping tomatoTopping = new Topping("Tomato", 5f, Topping.Type.VEG, 1);
    private final Topping oliveTopping = new Topping("Olive", 5f, Topping.Type.VEG, 1);
    private final Pizza withVegTopping = new ToppingDecorator(vegPizza, tomatoTopping);
    private final Pizza withTwoVegTopping = new ToppingDecorator(withVegTopping, oliveTopping);
    private final Pizza withNonvegTopping = new ToppingDecorator(vegPizza, chickenTopping);
    private final Pizza withVegAndNonVegToppings = new ToppingDecorator(withVegTopping, chickenTopping);



    @Test(expected = InvalidOrderException.class)
    public void testInvalidPizza(){
        validator.validate(withNonvegTopping);
    }

    @Test(expected = InvalidOrderException.class)
    public void testInvalidPizza1(){
        validator.validate(withVegAndNonVegToppings);
    }

    @Test
    public void testValidPizza(){
        validator.validate(withVegTopping);
    }

    @Test
    public void testValidPizza1(){
        validator.validate(withTwoVegTopping);
    }

}
