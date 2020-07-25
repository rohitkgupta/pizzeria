package com.pizzeria.store.entity;

public class Item {
    private Integer id;
    private Type type;
    private String name;
    private String description;
    private Float price;
    private Integer quantity;

    public Item(Integer id, Integer quantity){
        this.id = id;
        this.quantity = quantity;
    }
    public Item(Type type, String name, Float price, Integer quantity) {
        this.type = type;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Item(Item item) {
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

    public static enum Type {
        PIZZA, TOPPING, SIDES;
    }
}
