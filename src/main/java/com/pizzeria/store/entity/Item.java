package com.pizzeria.store.entity;

public class Item {
    private Integer id;
    private Type type;
    private String name;
    private String description;
    private Float price;
    private Integer quantity;

    public Item(Type type, String name, Float price) {
        this.type = type;
        this.name = name;
        this.price = price;
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

    protected void setType(Type type) {
        this.type = type;
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

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public static enum Type {
        PIZZA, TOPPING, SIDES;
    }
}
