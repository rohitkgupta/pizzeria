package com.pizzeria.store.entity;

public class Pizza extends MenuItem {

    private Type pizzaType;
    private Crust crust;
    private Size size;

    public Pizza(Integer id, Integer quantity, Crust crust) {
        super(id, MenuItem.Type.PIZZA, quantity);
        crust.setQuantity(this.getQuantity());
        this.crust = crust;
    }

    public Pizza(String name, Float price, Size size, Type pizzaType, Integer quantity) {
        super(MenuItem.Type.PIZZA, name, price, quantity);
        this.size = size;
        this.pizzaType = pizzaType;
    }

    public Pizza(Pizza pizza) {
        super(pizza);
        if (pizza != null) {
            this.pizzaType = pizza.pizzaType;
            this.crust = pizza.crust;
            this.size = pizza.size;
        }
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " (" + this.pizzaType.getName() + ") " + (this.crust != null ? "Crust(" + this.crust.getName() + ")" : "") + " " +
                (this.size != null ? "Size(" + this.size + ")" : "");
    }

    public Type getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(Type type) {
        this.pizzaType = type;
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

    public static class Builder{
        private Integer id;
        private String name;
        private String description;
        private Float price;
        private Integer quantity;
        private Type pizzaType;
        private Crust crust;
        private Size size;

        public Builder(Integer id){
            this.id = id;
        }

        public Builder(){
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPrice(Float price) {
            this.price = price;
            return this;
        }

        public Builder setQuantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setPizzaType(Type pizzaType) {
            this.pizzaType = pizzaType;
            return this;
        }

        public Builder setCrust(Crust crust) {
            this.crust = crust;
            return this;
        }

        public Builder setSize(Size size) {
            this.size = size;
            return this;
        }

        public Pizza build(){
            Pizza pizza = new Pizza(this.id, (this.quantity != null? this.quantity : 1), this.crust);
            pizza.size = this.size;
            pizza.pizzaType = this.pizzaType;
            pizza.setPrice(this.price);
            pizza.setName(this.name);
            pizza.setDescription(this.description);
            return pizza;
        }
    }
}
