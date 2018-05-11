package FXML;

import Connection.SocketConnection;
import Models.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientAddController {

    @FXML
    private Button saveBtn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    void saveBtnPressed(ActionEvent event) {
        Client client = new Client();

        client.setFull_name(nameField.getText());
        client.setFull_address(addressField.getText());
        client.setEmail(emailField.getText());

        SocketConnection sc = SocketConnection.getInstance();
        sc.saveClient(client);

        //close the window
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

}
