package FXML;

import Connection.SocketConnection;
import Models.Client;
import Models.Finish;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FinishAddController {

    @FXML
    private TextField colorField;

    @FXML
    private TextField mfgField;

    @FXML
    private TextField typeField;

    @FXML
    void saveBtnPressed(ActionEvent event) {

        Finish finish = new Finish();

        finish.setColor(colorField.getText());
        finish.setManufacturer(mfgField.getText());
        finish.setFinish_type(typeField.getText());

        SocketConnection sc = SocketConnection.getInstance();
        sc.saveFinish(finish);

        //close the window
        Stage stage = (Stage) colorField.getScene().getWindow();
        stage.close();
    }

}