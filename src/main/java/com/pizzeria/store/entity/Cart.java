package com.pizzeria.store.entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Cart {
    private String userName;
    private List<MenuItem> items;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<MenuItem> getItems() {
        if (items == null){
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public Float getTotal() {
        Float total = 0f;
        for (MenuItem item: this.getItems()) {
            if (item.getPrice() != null) {
                total = total + (item.getPrice() * item.getQuantity());
            }
        }
        return total;
    }

    public static class Builder {
        private String userName;
        private List<MenuItem> items = new LinkedList<>();

        public Builder forUser(String name){
            this.userName = name;
            return this;
        }

        public Builder addItem(MenuItem item){
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
