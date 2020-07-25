package com.pizzeria.store.entity;

public class Pizza extends Item {

    private Type pizzaType;
    private Crust crust;
    private Size size;

    public Pizza(String name, Float price, Size size, Type pizzaType) {
        super(Item.Type.PIZZA, name, price);
        this.size = size;
        this.pizzaType = pizzaType;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (" + this.pizzaType.getName() + ") " + (this.crust != null ? "Crust(" + this.crust.getName() + ")" : "") + " " +
                (this.size != null ? "Size(" + this.size + ")" : "");
    }

    public Type getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(Type pizzaType) {
        this.pizzaType = pizzaType;
    }

    public Crust getCrust() {
        return crust;
    }

    public void setCrust(Crust crust) {
        this.crust = crust;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public enum Size {
        REGULAR, MEDIUM, LARGE;
    }

    public enum Type {
        NON_VEG("Non-Vegetarian"), VEG("Vegetarian");

        private String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
