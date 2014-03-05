package com.github.txtrpg.core;

/**
 * @author gushakov
 */
public class Item extends Entity implements Movable {

    private int weight;

    private int price;

    public Item() {
    }

    public Item(String name, String description) {
        super(name, description);
        this.weight = 0;
        this.price = 0;
    }

    public Item(String name, String description, int weight, int price) {
        super(name, description);
        this.weight = weight;
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
