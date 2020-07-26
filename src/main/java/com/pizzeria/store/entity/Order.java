package com.pizzeria.store.entity;

public class Order {
    private Cart cart;
    private Float discount = 0f;
    private Status status;

    public Order(Cart cart){
        this.cart = cart;
        this.status = Status.NEW_ORDER;
    }

    public Float getTotal(){
        return cart.getTotal() - this.discount;
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

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public enum Status {
        PLACED, NEW_ORDER;
    }
}
