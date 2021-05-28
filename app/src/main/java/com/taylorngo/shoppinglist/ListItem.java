package com.taylorngo.shoppinglist;

public class ListItem {
    private String name;
    private String category;
    private String description;
    private double price;
    private boolean purchased;

    public ListItem(){
        this.name = "Item 1";
        this.category = "Food";
        this.description = "Meh";
        this.price = 6.6;
        this.purchased = false;
    }

    public ListItem(String name, String category, String description, double price, boolean purchased){
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.purchased = purchased;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
