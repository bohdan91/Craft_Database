package main.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.Models.*;

import java.lang.reflect.Type;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class QueryClass {
    private static QueryClass instance; //instance of singleton
    private Gson gson;
    private Connection con;
    private String dbDriver = "com.mysql.jdbc.Driver";
    private String dbAddress = "jdbc:mysql://localhost:3306/mydb";
    private String dbUser = "root";
    private String dbPass = "blueteam";

//    private String dbDriver = "com.mysql.jdbc.Driver";
//    private String dbAddress = "jdbc:mysql://localhost:3306/wight20";
//    private String dbUser = "wight20";
//    private String dbPass = "sunglasses";


    private QueryClass() {
        gson = new Gson();
        try {
            Class.forName(dbDriver);
            con = DriverManager.getConnection(dbAddress, dbUser, dbPass);
        } catch (ClassNotFoundException ce) {
            throw new RuntimeException("JDBC driver not found, jar is probably missing or in wrong folder");
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    public static QueryClass getInstance(){
        if(instance == null){
            instance = new QueryClass();
        }
        return instance;
    }

    public String getInventoryJSON(){
        String query = "SELECT * FROM inventory";
        ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();

        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()){
                InventoryItem item = new InventoryItem();

                item.setItem_id(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setPicture_id(rs.getInt("picture_id"));
                item.setMfg_price(rs.getBigDecimal("mfg_price").doubleValue());
                item.setRetail_price(rs.getBigDecimal("retail_price").doubleValue());
                item.setWholesale_price(rs.getBigDecimal("wholesale_price").doubleValue());

                items.add(item);
            }

        } catch (SQLException se){
            se.printStackTrace();
        }
        String json = gson.toJson(items);

        return json;
    }

    public String getClientJSON(){
        String query = "SELECT * FROM client";
        ArrayList<Client> clients = new ArrayList<Client>();

        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()){
                Client client = new Client();

                client.setClient_id(rs.getInt("client_id"));
                client.setFull_name(rs.getString("full_name"));
                client.setFull_address(rs.getString("full_address"));
                client.setEmail("email");

                clients.add(client);
            }

        } catch (SQLException se){
            se.printStackTrace();
        }
        String json = gson.toJson(clients);

        return json;
    }

    public String getFinishesJSON(){
        String query = "SELECT * FROM finish";
        ArrayList<Finish> finishes = new ArrayList<Finish>();

        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()){
                Finish finish = new Finish();
                finish.setFinish_id(rs.getInt("finish_id"));
                finish.setColor(rs.getString("color"));
                finish.setManufacturer(rs.getString("manufacturer"));
                finish.setFinish_type(rs.getString("finish_type"));

                finishes.add(finish);

            }

        } catch (SQLException se){
            se.printStackTrace();
        }
        String json = gson.toJson(finishes);

        return json;
    }

    public String getItemFinishesJSON(int id){
        String query = "select inventory.name, finish.color, finish.finish_type " +
                "from inventory join item_finishes on inventory.item_id = item_finishes.item_id join finish on item_finishes.finish_id = finish.finish_id " +
                "where inventory.item_id = ?";

        ArrayList<ItemFinishes> itemFinishes = new ArrayList<ItemFinishes>();

        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, id);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()){
                ItemFinishes itf = new ItemFinishes();
                itf.setItem_name(rs.getString("name"));
                itf.setFinish_color(rs.getString("color"));
                itf.setFinish_type(rs.getString("finish_type"));

                itemFinishes.add(itf);

            }

        } catch (SQLException se){
            se.printStackTrace();
        }
        String json = gson.toJson(itemFinishes);

        return json;
    }

    public String getReceiptJSON(){
        String query = "SELECT receipt.receipt_number, client.client_id ,client.full_name, receipt.date, receipt.sale_total"
                + " FROM receipt JOIN client ON receipt.client_id = client.client_id";

        ArrayList<Receipt> receipts = new ArrayList<Receipt>();

        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()){
                Receipt receipt = new Receipt();
                receipt.setReceipt_number(rs.getInt("receipt_number"));
                receipt.setClient_id(rs.getInt("client_id"));
                receipt.setClient_name(rs.getString("full_name"));
                receipt.setSale_total(rs.getDouble("sale_total"));
                receipt.setDate(rs.getDate("date").toString());


                receipts.add(receipt);

            }

        } catch (SQLException se){
            se.printStackTrace();
        }
        String json = gson.toJson(receipts);

        return json;
    }

    public String getReceiptItems(int id){
        String query = "SELECT inventory.item_id, inventory.picture_id, inventory.name, inventory.mfg_price, inventory.retail_price, inventory.wholesale_price"
                + " FROM sales JOIN inventory ON sales.item_id = inventory.item_id WHERE receipt_number = ?";

        ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();

        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, id);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()){
                InventoryItem item = new InventoryItem();

                item.setItem_id(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setPicture_id(rs.getInt("picture_id"));
                item.setMfg_price(rs.getBigDecimal("mfg_price").doubleValue());
                item.setRetail_price(rs.getBigDecimal("retail_price").doubleValue());
                item.setWholesale_price(rs.getBigDecimal("wholesale_price").doubleValue());

                items.add(item);
            }

        } catch (SQLException se){
            se.printStackTrace();
        }
        String json = gson.toJson(items);

        return json;
    }


    public int saveInventoryItem(String json){
        InventoryItem item = gson.fromJson(json, InventoryItem.class);
        String query = "INSERT INTO inventory (item_id, name, picture_id, mfg_price, retail_price, wholesale_price)" +
                " VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS );
            ps.setInt(1, item.getItem_id());
            ps.setString(2, item.getName());
            ps.setInt(3, item.getPicture_id());
            ps.setDouble(4, item.getMfg_price());
            ps.setDouble(5, item.getRetail_price());
            ps.setDouble(6, item.getWholesale_price());

            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                int last_inserted_id = rs.getInt(1);
                return last_inserted_id;
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }


    public void saveClient(String json){
        Client client = gson.fromJson(json, Client.class);
        String query = "INSERT INTO client (full_name, full_address, email) VALUES (?,?,?)";

        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, client.getFull_name());
            ps.setString(2, client.getFull_address());
            ps.setString(3, client.getEmail());

            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveFinish(String json){
        Finish finish = gson.fromJson(json, Finish.class);
        String query = "INSERT INTO finish (color, manufacturer, finish_type) VALUES (?,?,?)";

        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, finish.getColor());
            ps.setString(2, finish.getManufacturer());
            ps.setString(3, finish.getFinish_type());
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveReceipt(String json){
        Receipt receipt = gson.fromJson(json, Receipt.class);
        String query = "INSERT INTO receipt (client_id, date, sale_total, receipt_number) VALUES (?,?,?,?)";

        try{
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.ENGLISH);
            Date parsed = sdf.parse(sdf.format(today));
            java.sql.Date sql = new java.sql.Date(parsed.getTime());

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, receipt.getClient_id());
            ps.setDate(2, sql);
            ps.setDouble(3, receipt.getSale_total());
            ps.setInt(4, receipt.getReceipt_number());
            ps.execute();


        } catch(SQLException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }

    }

    public void saveReceiptSales(int recId, String itemsJson){
        String query = "INSERT INTO sales (receipt_number, item_id) VALUES (?, ?)";

        Type listType = new TypeToken<ArrayList<InventoryItem>>(){}.getType();
        ArrayList<InventoryItem> items =  gson.fromJson(itemsJson, listType);

        for(InventoryItem item : items) {
            try {
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, recId);
                ps.setInt(2, item.getItem_id());
                System.out.println("recId: " + recId);
                System.out.println("itemId: " + item.getItem_id());
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public void removeReceipt(int id){
        //remove relation with finishes first
        String removeQuery = "DELETE FROM sales WHERE receipt_number = ?";
        try{
            PreparedStatement ps = con.prepareStatement(removeQuery);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }


        String query = "DELETE FROM receipt WHERE receipt_number = ?";

        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeInventoryItem(int id){
        //remove relation with finishes first
        String removeQuery = "DELETE FROM item_finishes WHERE item_id = ?";
        try{
            PreparedStatement ps = con.prepareStatement(removeQuery);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }


        String query = "DELETE FROM inventory WHERE item_id = ?";

        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeClient(int id){
        String query = "DELETE FROM client WHERE client_id = ?";

        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeFinish(int id){
        String query = "DELETE FROM finish WHERE finish_id = ?";

        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveItemFinishes(String json){
        Type type = new TypeToken<HashMap<Integer, ArrayList<Integer>> >(){}.getType();
        HashMap<Integer, ArrayList<Integer>>  finishes = gson.fromJson(json, type);

        String query = "INSERT INTO item_finishes (item_id, finish_id) VALUES (?, ?)";
        Iterator<Integer> it = finishes.keySet().iterator();
        while(it.hasNext()){
            int item_id = it.next();
            ArrayList<Integer> finishesList = finishes.get(item_id);
            for(Integer i : finishesList) {
                try {
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, item_id);
                    ps.setInt(2, i);
                    System.out.println("itemID: " + item_id);
                    System.out.println("FinishId: " + i);
                    ps.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static void main(String[] args) {
        QueryClass ins = QueryClass.getInstance();
        System.out.println(ins.getInventoryJSON());
    }


}
