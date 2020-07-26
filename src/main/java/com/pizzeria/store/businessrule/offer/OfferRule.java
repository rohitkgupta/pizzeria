package com.pizzeria.store.businessrule.offer;

import com.pizzeria.store.entity.MenuItem;

public interface OfferRule<T extends MenuItem> {
    Float applyOffer(T item);
}
