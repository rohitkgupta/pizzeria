package com.pizzeria.store.entity;

public class Order {
    private Cart cart;
    private Status status;

    public Order(Cart cart){
        this.cart = cart;
        this.status = Status.NEW_ORDER;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        PLACED, NEW_ORDER;
    }
}
