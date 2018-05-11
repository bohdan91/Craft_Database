package FXML;

import Connection.SocketConnection;
import Models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.SocketException;
import java.util.ArrayList;

public class ReceiptAddController {



    @FXML
    private ChoiceBox<String> receiptCustomer;

    @FXML
    private ChoiceBox<String> receiptItem;

    @FXML
    private ChoiceBox<String> priceChoice;

    @FXML
    private Button receiptAddItemBtn;

    @FXML
    private Button receiptCheckoutBtn;

    @FXML
    private ListView<String> receiptItemList;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label taxLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private TextField receiptNumberField;

    private ObservableList<String> itemsSelected;

    private ArrayList<InventoryItem> items;

    private  ArrayList<InventoryItem> itemsInList;

    @FXML
    void initialize() {

        ArrayList<Client> clients = SocketConnection.getInstance().getClientJSON();
        ObservableList<String> clientList = FXCollections.observableArrayList();

        for(Client client : clients){
            clientList.add(client.getClient_id() + "/" + client.getFull_name());
        }
        receiptCustomer.setItems(clientList);

        items = SocketConnection.getInstance().getInventoryItems();
        ObservableList<String> itemList = FXCollections.observableArrayList();

        for(InventoryItem item : items){
            itemList.add(item.getItem_id() + "/" + item.getName() + "/$" + item.getMfg_price() + "/$" + item.getRetail_price() +
            "/$" + item.getWholesale_price());
        }
        receiptItem.setItems(itemList);

        receiptItem.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                itemSelected(newValue.toString());
            }

        });

        // force the field to be numeric only
        receiptNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    receiptNumberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        itemsSelected = FXCollections.observableArrayList();
        itemsInList = new ArrayList<>();

    }


    @FXML
    void addItemPressed(ActionEvent event) {
        String itemChosen = receiptItem.getSelectionModel().getSelectedItem();
        String price = priceChoice.getSelectionModel().getSelectedItem();


        if(itemChosen != null && price != null ) {
            int chosenId = Integer.parseInt(itemChosen.substring(0, itemChosen.indexOf("/")));

            for(InventoryItem item : items){
                if(item.getItem_id() == chosenId){
                        String lineToAdd = item.getItem_id() + "/" + item.getName() + "/" + price;
                        itemsSelected.add(lineToAdd);
                        receiptItemList.setItems(itemsSelected);
                        itemsInList.add(item);
                }
            }

            //calculate prices
            double subtotal = 0.0;
            for(String line : receiptItemList.getItems()){
                int id = Integer.parseInt(line.substring(0, line.indexOf("/")));

                InventoryItem item = getItemById(id);
                double priceToadd;
                if(line.contains("Retail")){
                    priceToadd = item.getRetail_price();
                }else if(line.contains("Wholesale")){
                    priceToadd = item.getWholesale_price();
                }else {
                    System.out.println("error");
                    return;
                }
                subtotal += priceToadd;

                double tax = subtotal * 0.08;
                double total = subtotal + tax;

                subtotalLabel.setText(Double.toString(subtotal));
                taxLabel.setText(Double.toString(tax));
                totalLabel.setText(Double.toString(total));
            }
        }
    }

    private InventoryItem getItemById(int id){
        for(InventoryItem item : items){
            if(item.getItem_id() == id){
                return item;
            }
        }
        return null;
    }

    @FXML
    void checkoutPressed(ActionEvent event) {
        String customer = receiptCustomer.getSelectionModel().getSelectedItem();
        String totalPrice = totalLabel.getText();
        String receiptNumberText = receiptNumberField.getText();

        if(customer != null && !totalPrice.equals("0") && receiptNumberText != null){
            int receiptId = Integer.parseInt(receiptNumberText);
            Receipt receipt = new Receipt();

            receipt.setClient_id(Integer.parseInt(customer.substring(0, customer.indexOf("/"))));
            receipt.setSale_total(Double.parseDouble(totalPrice));
            receipt.setReceipt_number(receiptId);
            SocketConnection.getInstance().saveReceipt(receipt);

            SocketConnection.getInstance().saveReceiptSales(receiptId, itemsInList);

            //close the window
            Stage stage = (Stage) totalLabel.getScene().getWindow();
            stage.close();
        }

    }

    void itemSelected(String selectionId){
        if(selectionId != null) {
            int selId = Integer.parseInt(selectionId);
            String line = receiptItem.getItems().get(selId);
            int itemId = Integer.parseInt(line.substring(0, line.indexOf("/")));

            ObservableList<String> prices = FXCollections.observableArrayList();
            for(InventoryItem item : items){
                if(item.getItem_id() == itemId){
                    prices.add("$" + item.getRetail_price() + " (Retail)");
                    prices.add("$" + item.getWholesale_price() + " (Wholesale)");

                    priceChoice.setItems(prices);
                    break;
                }
            }
        }

    }

}
