package FXML;

import Connection.SocketConnection;
import Models.Finish;
import Models.InventoryItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryAddController {

    @FXML
    private Button addButton;

    @FXML
    private Button cleraBtn;

    @FXML
    private TextField itemNameField;

    @FXML
    private TextField mfgPriceField;

    @FXML
    private TextField retailPriceField;

    @FXML
    private TextField wholesalePriceField;


    @FXML
    private ChoiceBox<String> finishesChoice;

    @FXML
    private ListView<String> availableFinishesList;

    private MainController mainController;

    private ObservableList<String> availableFinishes;
    private ArrayList<Integer> finishIds;


    @FXML
    void initialize() {
        //load finishes
        ArrayList<Finish> finishes = SocketConnection.getInstance().getFinishes();
        ObservableList<String> list = FXCollections.observableArrayList();
        for(Finish finish : finishes){
            list.add(finish.getFinish_id() + "/" + finish.getColor() + "/" + finish.getManufacturer() + "/" + finish.getFinish_type());
        }
        finishesChoice.setItems(list);

        availableFinishes = FXCollections.observableArrayList();
        finishIds = new ArrayList<>();

    }

    @FXML
    void addBtnPressed(ActionEvent event) {
        //create and write new item
        InventoryItem newItem = new InventoryItem();

        try {
            newItem.setName(itemNameField.getText());
            newItem.setMfg_price(Double.parseDouble(mfgPriceField.getText()));
            newItem.setRetail_price(Double.parseDouble(retailPriceField.getText()));
            newItem.setWholesale_price(Double.parseDouble(wholesalePriceField.getText()));
            SocketConnection sc = SocketConnection.getInstance();
            int lastId = sc.saveInventoryItem(newItem);

            mainController.updateInventoryTable();

            HashMap<Integer, ArrayList<Integer>> finishItem = new HashMap<>();

            finishItem.put(lastId, finishIds);
            sc.saveItemFinishes(finishItem);

            //close the window
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        }catch (NumberFormatException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Bad Format");
            alert.setHeaderText("Bad Price format");
            alert.setContentText("");

            alert.showAndWait();
        }

    }

    @FXML
    void clearBtnPressed(ActionEvent event) {
        itemNameField.setText("");
        mfgPriceField.setText("");
        retailPriceField.setText("");
        wholesalePriceField.setText("");

    }

    public void setMainController(MainController controller){
        this.mainController = controller;
    }

    @FXML
    private void addFinishBtnPressed(){
        String line = finishesChoice.getSelectionModel().getSelectedItem();
        if(!line.equals("") && !availableFinishes.contains(line)){
            availableFinishes.add(line);
            availableFinishesList.setItems(availableFinishes);

            int finish_id = Integer.parseInt(line.substring(0, line.indexOf("/")));
            finishIds.add(finish_id);

        }


    }

}
