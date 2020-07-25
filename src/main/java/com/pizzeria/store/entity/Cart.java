package com.pizzeria.store.entity;

import java.util.ArrayList;
import java.util.LinkedList;
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

    public static class Builder {
        private String userName;
        private List<Item> items = new LinkedList<>();

        public Builder forUser(String name){
            this.userName = name;
            return this;
        }

        public Builder addItem(Item item){
            this.items.add(item);
            return this;
        }

        public Cart build(){
            Cart cart = new Cart();
            cart.userName = this.userName;
            cart.items = this.items;
            return cart;
        }
    }
}
