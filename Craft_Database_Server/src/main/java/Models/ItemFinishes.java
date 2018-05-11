package main.java.Models;

public class ItemFinishes {
    private String item_name;
    private String finish_color;
    private String finish_type;

    public ItemFinishes(){

    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getFinish_color() {
        return finish_color;
    }

    public void setFinish_color(String finish_color) {
        this.finish_color = finish_color;
    }

    public String getFinish_type() {
        return finish_type;
    }

    public void setFinish_type(String finish_type) {
        this.finish_type = finish_type;
    }
}
