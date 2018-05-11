package Models;

public class Receipt {
    private int receipt_number;
    private String client_name;
    private int client_id;
    private String date;
    private double sale_total;

    public int getReceipt_number() {
        return receipt_number;
    }

    public void setReceipt_number(int receipt_number) {
        this.receipt_number = receipt_number;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getSale_total() {
        return sale_total;
    }

    public void setSale_total(double sale_total) {
        this.sale_total = sale_total;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
}
