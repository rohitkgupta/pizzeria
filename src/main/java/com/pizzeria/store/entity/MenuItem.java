package com.pizzeria.store.entity;

import java.util.Objects;

public class MenuItem {
    private Integer id;
    private Type type;
    private String name;
    private String description;
    private Float price;
    private Integer quantity;

    protected MenuItem(Integer id, Type type, Integer quantity){
        this.id = id;
        this.type = type;
        this.quantity = quantity;
    }
    protected MenuItem(Type type, String name, Float price, Integer quantity) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public MenuItem(MenuItem item) {
        super();
        if (item != null) {
            this.id = item.id;
            this.type = item.type;
            this.name = item.name;
            this.description = item.description;
            this.price = item.price;
            this.quantity = item.quantity;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        if (description != null) {
            return description;
        } else {
            return name;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public enum Type {
        PIZZA, TOPPING, SIDES, CRUST;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(id, menuItem.id) &&
                type == menuItem.type &&
                Objects.equals(name, menuItem.name);
    }

}
