package main.java.Models;

public class Finish {
    private int finish_id;
    private String color;
    private String manufacturer;
    private String finish_type;

    public Finish(){

    }

    public int getFinish_id() {
        return finish_id;
    }

    public void setFinish_id(int finish_id) {
        this.finish_id = finish_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getFinish_type() {
        return finish_type;
    }

    public void setFinish_type(String finish_type) {
        this.finish_type = finish_type;
    }
}
