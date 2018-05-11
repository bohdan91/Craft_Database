package main.java.Models;

public class InventoryItem {

    private int item_id;
    private String name;
    private int picture_id;
    private double mfg_price;
    private double retail_price;
    private double wholesale_price;


    public InventoryItem(int item_id, String name, int picture_id, double mfg_price, double retail_price, double wholesale_price) {
        this.item_id = item_id;
        this.name = name;
        this.picture_id = picture_id;
        this.mfg_price = mfg_price;
        this.retail_price = retail_price;
        this.wholesale_price = wholesale_price;
    }

    public InventoryItem(){
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(int picture_id) {
        this.picture_id = picture_id;
    }

    public double getMfg_price() {
        return mfg_price;
    }

    public void setMfg_price(double mfg_price) {
        this.mfg_price = mfg_price;
    }

    public double getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(double retail_price) {
        this.retail_price = retail_price;
    }

    public double getWholesale_price() {
        return wholesale_price;
    }

    public void setWholesale_price(double wholesale_price) {
        this.wholesale_price = wholesale_price;
    }
}
