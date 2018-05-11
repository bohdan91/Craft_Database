package main.java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.Queue;

public class Server implements Runnable {
    private final Socket m_socket;
    private final int m_num;
    private static QueryClass db;

    Server(Socket socket, int num) {
        m_socket = socket;
        m_num = num;

        Thread handler = new Thread(this, "handler-" + m_num);
        handler.start();
    }

    public void run() {
        try {
            try {
                System.out.println(m_num + " Connected.");
                BufferedReader in = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
                PrintWriter out = new PrintWriter(m_socket.getOutputStream(), true);

                    String line = in.readLine();
                    System.out.println("line: " + line);
                    if (line == null) {
                        System.out.println(m_num + " Closed.");
                    } else if(line.equalsIgnoreCase("getInventoryJSON")){
                        System.out.println("getInventory");
                        out.println(db.getInventoryJSON());
                    }else if(line.equalsIgnoreCase("getClientJSON")){
                        out.println(db.getClientJSON());
                    } else if(line.equalsIgnoreCase("getFinishesJSON")){
                        out.println(db.getFinishesJSON());
                    }else if(line.equalsIgnoreCase("saveInventoryItem")){
                        String json = in.readLine();
                        int id = db.saveInventoryItem(json);
                        out.println(id);
                    }else if(line.equalsIgnoreCase("saveClient")){
                        String json = in.readLine();
                        db.saveClient(json);
                    }else if(line.equalsIgnoreCase("saveFinish")){
                        String json = in.readLine();
                        db.saveFinish(json);
                    } else if(line.equalsIgnoreCase("removeInventoryItem")){
                        int id = Integer.parseInt(in.readLine());
                        db.removeInventoryItem(id);
                    }
                    else if(line.equalsIgnoreCase("removeClient")){
                        int id = Integer.parseInt(in.readLine());
                        db.removeClient(id);
                    } else if(line.equalsIgnoreCase("removeFinish")){
                        int id = Integer.parseInt(in.readLine());
                        db.removeFinish(id);

                    } else if(line.equalsIgnoreCase("saveItemFinishes")){
                        String json = in.readLine();
                        db.saveItemFinishes(json);
                    } else if(line.equalsIgnoreCase("getItemFinishesJSON")){
                        int id = Integer.parseInt(in.readLine());
                        String json = db.getItemFinishesJSON(id);
                        out.println(json);

                    }else if(line.equalsIgnoreCase("getReceiptJSON")){
                        String json = db.getReceiptJSON();
                        out.println(json);
                    } else if(line.equalsIgnoreCase("getReceiptItems")){
                        int id = Integer.parseInt(in.readLine());
                        String json = db.getReceiptItems(id);
                        out.println(json);
                    } else if(line.equalsIgnoreCase("saveReceipt")){
                        String json = in.readLine();
                        db.saveReceipt(json);
                    }else if(line.equalsIgnoreCase("saveReceiptSales")){
                        int receiptId = Integer.parseInt(in.readLine());
                        String itemsJson = in.readLine();
                        db.saveReceiptSales(receiptId, itemsJson);
                    }else if(line.equalsIgnoreCase("removeReceipt")){
                        int id = Integer.parseInt(in.readLine());
                        db.removeReceipt(id);
                    }
                    else {
                        System.out.println(m_num + " Read: " + line);
                    }

            } finally {
                m_socket.close();
            }
        } catch (IOException e) {
            System.out.println(m_num + " Error: " + e.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        db = QueryClass.getInstance();
        int port = 5303;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        System.out.println("Accepting connections on port: " + port);
        int nextNum = 1;
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            Thread.sleep(50);
            new Server(socket, nextNum++);
        }
    }
}