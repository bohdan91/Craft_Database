package FXML;

import Connection.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainController {

    //***************** Menu ******************************
    @FXML
    private ListView<String> menuList;
    //******************************************************



    //***************** Client Table **********************
    @FXML
    private TableView<Client> clientTable;

    @FXML
    private TableColumn<Client, Integer> client_id;

    @FXML
    private TableColumn<Client, String> client_name;

    @FXML
    private TableColumn<Client, String> client_address;

    @FXML
    private TableColumn<Client, String> client_email;

    private ArrayList<Client> clients;

    //******************************************************

    //***************** Inventory Table ********************
    @FXML
    private TableView<InventoryItem> inventoryTable;
    @FXML
    private TableColumn<InventoryItem, Integer> item_id;

    @FXML
    private TableColumn<InventoryItem, String> item_name;

    @FXML
    private TableColumn<InventoryItem, Double> mfg_price;

    @FXML
    private TableColumn<InventoryItem, Double> retail_price;

    @FXML
    private TableColumn<InventoryItem, Double> wholesale_price;

    private ArrayList<InventoryItem> inventoryItems;

    //******************************************************

    //***************** Finishes Table Table ********************
    @FXML
    private TableView<Finish> finishesTable;

    @FXML
    private TableColumn<Finish, Integer> finish_id;

    @FXML
    private TableColumn<Finish, String> finish_color;

    @FXML
    private TableColumn<Finish, String> finish_manufacturer;

    @FXML
    private TableColumn<Finish, String> finish_type;

    //******************************************************

    //***************** Item-Finishes Table ********************
    @FXML
    private TableView<ItemFinishes> itemFinishesTable;

    @FXML
    private TableColumn<ItemFinishes, String> itemFinishName;

    @FXML
    private TableColumn<ItemFinishes, String> itemFinishColor;

    @FXML
    private TableColumn<ItemFinishes, String> itemFinishType;

    //******************************************************

    //***************** Receipts Table ********************
    @FXML
    private TableView<Receipt> receiptTable;

    @FXML
    private TableColumn<Receipt, Integer>  receiptNumber;

    @FXML
    private TableColumn<Receipt, String>  receiptClientName;

    @FXML
    private TableColumn<Receipt, Double>  receiptTotal;

    @FXML
    private TableColumn<Receipt, String>  receiptDate;

    //******************************************************


    @FXML
    private TextField searchField;

    @FXML
    private Label clearLabel;


    @FXML
    void initialize() {
        //Adding items to side menu list for table options
        ObservableList<String> items = FXCollections.observableArrayList(
                "Inventory", "Clients", "Finishes", "Receipts");
        menuList.setItems(items);

        setUpAllTables();

        //auto select Inventory table as default
        menuList.getSelectionModel().select("Inventory");
        updateInventoryTable();
        inventoryTable.setVisible(true);
    }

    @FXML
    private void listItemSelected(){
        //clear search field
        searchField.setText("");
        searchTyped();

        hideAllTables();

        //Checking for selected item in menu list and showing corresponding table
        String selection = menuList.getSelectionModel().getSelectedItem();
        if(selection.equals("Inventory")){
            updateInventoryTable();
            inventoryTable.setVisible(true);
        } else if(selection.equals("Clients")){
            updateClientTable();
            clientTable.setVisible(true);
        } else if(selection.equals("Finishes")){
            updateFinishTable();
            finishesTable.setVisible(true);
        } else if(selection.equals("Receipts")){
            updateReceiptTable();
            receiptTable.setVisible(true);
        }
    }

    private void setUpAllTables(){
        inventoryTableSetUp();
        clientTableSetUp();
        finishTableSetUp();
        itemFinishTableSetUp();
        receiptTableSetUp();
    }

    private void hideAllTables(){
        //hiding all tables
        inventoryTable.setVisible(false);
        clientTable.setVisible(false);
        finishesTable.setVisible(false);
        itemFinishesTable.setVisible(false);
        receiptTable.setVisible(false);
    }

    //These are methods to load and update each table
    public void updateInventoryTable(){
        inventoryItems = SocketConnection.getInstance().getInventoryItems();
        ObservableList<InventoryItem> list = FXCollections.observableArrayList();
        list.addAll(inventoryItems);

        inventoryTable.setItems(list);
    }

    private void updateClientTable(){
        clients = SocketConnection.getInstance().getClientJSON();
        ObservableList<Client> list = FXCollections.observableArrayList();
        list.addAll(clients);
        clientTable.setItems(list);
    }

    private void updateFinishTable(){
        ArrayList<Finish> finishes = SocketConnection.getInstance().getFinishes();
        ObservableList<Finish> list = FXCollections.observableArrayList();
        list.addAll(finishes);
        finishesTable.setItems(list);
    }

    private void updateReceiptTable(){
        ArrayList<Receipt> receipts = SocketConnection.getInstance().getReceiptJSON();
        ObservableList<Receipt> list = FXCollections.observableArrayList();
        list.addAll(receipts);
        receiptTable.setItems(list);
    }



    //These are methods to link variable names to columns of each table
    private void inventoryTableSetUp(){

        item_id.setCellValueFactory(new PropertyValueFactory<InventoryItem, Integer>("item_id"));
        item_name.setCellValueFactory(new PropertyValueFactory<InventoryItem, String>("name"));
        mfg_price.setCellValueFactory(new PropertyValueFactory<InventoryItem, Double>("mfg_price"));
        retail_price.setCellValueFactory(new PropertyValueFactory<InventoryItem, Double>("retail_price"));
        wholesale_price.setCellValueFactory(new PropertyValueFactory<InventoryItem, Double>("wholesale_price"));

        item_id.prefWidthProperty().bind(inventoryTable.widthProperty().multiply(0.1));
        item_name.prefWidthProperty().bind(inventoryTable.widthProperty().multiply(0.54));
        mfg_price.prefWidthProperty().bind(inventoryTable.widthProperty().multiply(0.15));
        retail_price.prefWidthProperty().bind(inventoryTable.widthProperty().multiply(0.1));
        wholesale_price.prefWidthProperty().bind(inventoryTable.widthProperty().multiply(0.11));

        makeHeaderWrappable(mfg_price);
        makeHeaderWrappable(retail_price);
        makeHeaderWrappable(wholesale_price);
    }

    private void clientTableSetUp(){
        client_id.setCellValueFactory(new PropertyValueFactory<Client, Integer>("client_id"));
        client_name.setCellValueFactory(new PropertyValueFactory<Client, String>("full_name"));
        client_address.setCellValueFactory(new PropertyValueFactory<Client, String>("full_address"));
        client_email.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));

    }

    private void finishTableSetUp(){
        finish_id.setCellValueFactory(new PropertyValueFactory<Finish, Integer>("finish_id"));
        finish_color.setCellValueFactory(new PropertyValueFactory<Finish, String>("color"));
        finish_manufacturer.setCellValueFactory(new PropertyValueFactory<Finish, String>("manufacturer"));
        finish_type.setCellValueFactory(new PropertyValueFactory<Finish, String>("finish_type"));

    }

    private void itemFinishTableSetUp(){
        itemFinishName.setCellValueFactory(new PropertyValueFactory<ItemFinishes, String>("item_name"));
        itemFinishColor.setCellValueFactory(new PropertyValueFactory<ItemFinishes, String>("finish_color"));
        itemFinishType.setCellValueFactory(new PropertyValueFactory<ItemFinishes, String>("finish_type"));
    }

    private void receiptTableSetUp(){
        receiptNumber.setCellValueFactory(new PropertyValueFactory<Receipt, Integer>("receipt_number"));
        receiptClientName.setCellValueFactory(new PropertyValueFactory<Receipt, String>("client_name"));
        receiptDate.setCellValueFactory(new PropertyValueFactory<Receipt, String>("date"));
        receiptTotal.setCellValueFactory(new PropertyValueFactory<Receipt, Double>("sale_total"));
    }

    //This method converts table column name into multiple row name
    private void makeHeaderWrappable(TableColumn col) {
        Label label = new Label(col.getText());
        label.setStyle("-fx-padding: 8px;");
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        StackPane stack = new StackPane();
        stack.getChildren().add(label);
        stack.prefWidthProperty().bind(col.widthProperty().subtract(5));
        label.prefWidthProperty().bind(stack.prefWidthProperty());
        col.setText(null);
        col.setGraphic(stack);
    }


    @FXML
    private void addInventoryItemForm(ActionEvent event) {
        //load form window
        try {
            FXMLLoader loader =  new FXMLLoader(getClass().getClassLoader().getResource("InventoryAdd.fxml"));
            AnchorPane pane = loader.load();
            Stage addForm = new Stage();
            addForm.setTitle("Add New Inventory Item");
            addForm.setScene(new Scene(pane, 440, 600));
            addForm.setResizable(false);

            InventoryAddController controller = loader.getController();
            controller.setMainController(this);

            addForm.showAndWait();
            updateInventoryTable();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    private void addClientForm(){
        //load form window
        try {
            FXMLLoader loader =  new FXMLLoader(getClass().getClassLoader().getResource("ClientAdd.fxml"));
            AnchorPane pane = loader.load();
            Stage addForm = new Stage();
            addForm.setTitle("Add New Client");
            addForm.setScene(new Scene(pane, 400, 550));
            addForm.setResizable(false);

            ClientAddController controller = loader.getController();
            //controller.setMainController(this);

            addForm.showAndWait();
            updateClientTable();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void addFinishForm(){
        //load form window
        try {
            FXMLLoader loader =  new FXMLLoader(getClass().getClassLoader().getResource("FinishAdd.fxml"));
            AnchorPane pane = loader.load();
            Stage addForm = new Stage();
            addForm.setTitle("Add New Finish");
            addForm.setScene(new Scene(pane, 400, 550));
            addForm.setResizable(false);

            FinishAddController controller = loader.getController();
            //controller.setMainController(this);

            addForm.showAndWait();
            updateFinishTable();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void addReceiptForm(){
        //load form window
        try {
            FXMLLoader loader =  new FXMLLoader(getClass().getClassLoader().getResource("ReceiptAdd.fxml"));
            AnchorPane pane = loader.load();
            Stage addForm = new Stage();
            addForm.setTitle("Add Receipt");
            addForm.setScene(new Scene(pane, 440, 600));
            addForm.setResizable(false);

            addForm.showAndWait();
            updateReceiptTable();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    private void removeInventoryItem(){
        InventoryItem item = inventoryTable.getSelectionModel().getSelectedItem();
        SocketConnection.getInstance().removeInventoryItem(item.getItem_id());
        updateInventoryTable();
    }
    @FXML
    private void removeClient(){
        Client client = clientTable.getSelectionModel().getSelectedItem();
        SocketConnection.getInstance().removeClient(client.getClient_id());
        updateClientTable();
    }
    @FXML
    private void removeFinish(){
        Finish finish = finishesTable.getSelectionModel().getSelectedItem();
        SocketConnection.getInstance().removeFinish(finish.getFinish_id());
        updateFinishTable();
    }

    @FXML
    private void removeReceipt(){
        Receipt receipt = receiptTable.getSelectionModel().getSelectedItem();
        SocketConnection.getInstance().removeReceipt(receipt.getReceipt_number());
        updateReceiptTable();
    }

    @FXML
    private void clearLabelPressed(){
        searchField.setText("");
        searchTyped();
    }

    @FXML
    private void searchTyped(){

        String searchKey = searchField.getText();
        if (searchKey.equalsIgnoreCase("")) {
            clearLabel.setDisable(true);
        } else {
            clearLabel.setDisable(false);
        }

        if(inventoryTable.isVisible()) {
            ArrayList<InventoryItem> matchItems = new ArrayList<>();
            for (InventoryItem item : inventoryItems) {
                if (item.getName().toLowerCase().contains(searchKey.toLowerCase())) {
                    matchItems.add(item);
                }
            }

            ObservableList<InventoryItem> list = FXCollections.observableArrayList();
            list.addAll(matchItems);
            inventoryTable.setItems(list);

        } else if(clientTable.isVisible()){
            ArrayList<Client> matchItems = new ArrayList<>();
            for (Client item : clients) {
                if (item.getFull_name().toLowerCase().contains(searchKey.toLowerCase())) {
                    matchItems.add(item);
                }
            }

            ObservableList<Client> list = FXCollections.observableArrayList();
            list.addAll(matchItems);
            clientTable.setItems(list);
        }

    }



    @FXML
    private void showItemFinishes(){
        //get id of selected row
        int id = inventoryTable.getSelectionModel().getSelectedItem().getItem_id();

        //load related ItemFinishes
        ArrayList<ItemFinishes> itemFinishes = SocketConnection.getInstance().getItemFinishesJSON(id);
        //load the table
        ObservableList<ItemFinishes> list = FXCollections.observableArrayList();
        list.addAll(itemFinishes);
        itemFinishesTable.setItems(list);

        hideAllTables();
        itemFinishesTable.setVisible(true);
    }

    @FXML
    private void showItemsMenu(){
        int id = receiptTable.getSelectionModel().getSelectedItem().getReceipt_number();
        hideAllTables();
        ArrayList<InventoryItem> items = SocketConnection.getInstance().getReceiptItems(id);
        ObservableList<InventoryItem> list = FXCollections.observableArrayList();
        list.addAll(items);
        inventoryTable.setItems(list);
        inventoryTable.setVisible(true);
    }


}
