package com.pizzeria.store.businessrule.offer;

import com.pizzeria.store.businessrule.offer.impl.LargePizzaOfferRule;
import com.pizzeria.store.entity.Crust;
import com.pizzeria.store.entity.Pizza;
import com.pizzeria.store.entity.Topping;
import com.pizzeria.store.entity.decorator.ToppingDecorator;
import org.junit.Assert;
import org.junit.Test;

public class LargePizzaOfferRuleTest {

    private OfferRule<Pizza> offerRule = new LargePizzaOfferRule();
    public final Pizza largeVegPizza = new Pizza.Builder().withName("VegPizza").setPrice(10f).setSize(Pizza.Size.LARGE).setQuantity(1).setPizzaType(Pizza.Type.VEG).setCrust(new Crust(1)).build();
    private final Topping tomatoTopping = new Topping("Tomato", 5f, Topping.Type.VEG, 1);
    private final Topping oliveTopping = new Topping("Olive", 10f, Topping.Type.VEG, 1);
    private final Topping paneerTopping = new Topping("Paneer", 15f, Topping.Type.VEG, 1);

    public final Pizza withTomatoToping = new ToppingDecorator(largeVegPizza, tomatoTopping);
    public final Pizza withTwoToping = new ToppingDecorator(withTomatoToping, oliveTopping);
    public final Pizza withThreeToping = new ToppingDecorator(withTwoToping, paneerTopping);

    @Test
    public void testApplyOffer(){
        Assert.assertEquals(0f,offerRule.applyOffer(largeVegPizza).floatValue(), 0f);
    }

    @Test
    public void testApplyOffer1(){
        Assert.assertEquals(5f,offerRule.applyOffer(withTomatoToping).floatValue(), 0f);
    }

    @Test
    public void testApplyOffer2(){
        Assert.assertEquals(15f,offerRule.applyOffer(withTwoToping).floatValue(), 0f);
    }

    @Test
    public void testApplyOffer3(){
        Assert.assertEquals(25f,offerRule.applyOffer(withThreeToping).floatValue(), 0f);
    }
}
