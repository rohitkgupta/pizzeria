package com.pizzeria.store.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String userName;
    private List<Item> items;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Item> getItems() {
        if (items == null){
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    public Float getTotal() {
        Float total = 0f;
        for (Item item: this.getItems()) {
            if (item.getPrice() != null) {
                total = total + item.getPrice();
            }
        }
        return total;
    }
}
