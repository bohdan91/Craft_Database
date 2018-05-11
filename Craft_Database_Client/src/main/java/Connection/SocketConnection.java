package Connection;

import Models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SocketConnection {
    private static SocketConnection sc;
    private String host;
    private int port;
    private Gson gson;

    private SocketConnection(String host, int port){
        this.host = host;
        this.port = port;
        gson = new Gson();
    }

    public static SocketConnection getInstance(){
        if(sc == null){
            //sc = new SocketConnection("129.3.20.26", 5303);
            sc = new SocketConnection("localhost", 5303);
        }
        return sc;
    }

    public ArrayList<Client> getClientJSON(){
        ArrayList<Client> items = new ArrayList<Client>();
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            out.println("getClientJSON");
            String response = in.readLine();
            Type listType = new TypeToken<ArrayList<Client>>(){}.getType();
            items =  gson.fromJson(response, listType);

        }catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }

    public ArrayList<InventoryItem> getInventoryItems(){
        ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            out.println("getInventoryJSON");
            String response = in.readLine();
            Type listType = new TypeToken<ArrayList<InventoryItem>>(){}.getType();
            items =  gson.fromJson(response, listType);

        }catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }

    public ArrayList<Finish> getFinishes(){
        ArrayList<Finish> items = new ArrayList<Finish>();
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            out.println("getFinishesJSON");
            String response = in.readLine();
            Type listType = new TypeToken<ArrayList<Finish>>(){}.getType();
            items =  gson.fromJson(response, listType);

        }catch (IOException e){
            e.printStackTrace();
        }
        return items;
    }

    public int saveInventoryItem(InventoryItem item){
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("saveInventoryItem");
            out.println(gson.toJson(item));
            int id = Integer.parseInt(in.readLine());
            return id;

        } catch (IOException e){
            e.printStackTrace();
        }
        return -1;
    }

    public void saveClient(Client client){
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("saveClient");
            out.println(gson.toJson(client));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveFinish(Finish finish){
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("saveFinish");
            out.println(gson.toJson(finish));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveReceipt(Receipt receipt){
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("saveReceipt");
            out.println(gson.toJson(receipt));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveReceiptSales(int receiptId, ArrayList<InventoryItem> items){
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("saveReceiptSales");
            out.println(receiptId);
            out.println(gson.toJson(items));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void removeInventoryItem(int id){
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("removeInventoryItem");
            out.println(id);

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void removeClient(int id){
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("removeClient");
            out.println(id);

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void removeFinish(int id){
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("removeFinish");
            out.println(id);

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void removeReceipt(int receiptId){
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("removeReceipt");
            out.println(receiptId);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveItemFinishes(HashMap<Integer, ArrayList<Integer>>  finishes){
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("saveItemFinishes");
            out.println(gson.toJson(finishes));

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<ItemFinishes> getItemFinishesJSON(int id){
        ArrayList<ItemFinishes> itemFinishes = new ArrayList<ItemFinishes>();
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            out.println("getItemFinishesJSON");
            out.println(id);
            String response = in.readLine();
            Type listType = new TypeToken<ArrayList<ItemFinishes>>(){}.getType();
            itemFinishes =  gson.fromJson(response, listType);

        }catch (IOException e){
            e.printStackTrace();
        }
        return itemFinishes;
    }

    public ArrayList<Receipt> getReceiptJSON(){
        ArrayList<Receipt> receipts = new ArrayList<>();
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            out.println("getReceiptJSON");
            String response = in.readLine();
            Type listType = new TypeToken<ArrayList<Receipt>>(){}.getType();
            receipts =  gson.fromJson(response, listType);

        }catch (IOException e){
            e.printStackTrace();
        }
        return receipts;
    }

    public ArrayList<InventoryItem> getReceiptItems(int id){
        ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            out.println("getReceiptItems");
            out.println(id);
            String response = in.readLine();
            Type listType = new TypeToken<ArrayList<InventoryItem>>(){}.getType();
            items =  gson.fromJson(response, listType);

        }catch (IOException e){
            e.printStackTrace();
        }
        return items;

    }





    public static void main(String[] args) {
        SocketConnection sc = SocketConnection.getInstance();
        sc.getInventoryItems();
    }
}
