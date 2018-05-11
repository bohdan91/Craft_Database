package main.java.Models;

public class Sale {
    private int sale_id;
    private int receipt_number;
    private String inventory_name;
    private Double mfg_price;
    private Double retail_price;
    private Double wholesale_price;

    public Sale(){

    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public int getReceipt_number() {
        return receipt_number;
    }

    public void setReceipt_number(int receipt_number) {
        this.receipt_number = receipt_number;
    }

    public String getInventory_name() {
        return inventory_name;
    }

    public void setInventory_name(String inventory_name) {
        this.inventory_name = inventory_name;
    }

    public Double getMfg_price() {
        return mfg_price;
    }

    public void setMfg_price(Double mfg_price) {
        this.mfg_price = mfg_price;
    }

    public Double getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(Double retail_price) {
        this.retail_price = retail_price;
    }

    public Double getWholesale_price() {
        return wholesale_price;
    }

    public void setWholesale_price(Double wholesale_price) {
        this.wholesale_price = wholesale_price;
    }
}
