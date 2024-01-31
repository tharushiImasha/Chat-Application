package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtName;

    static String name;

    @FXML
    void btnSubmitOnAction(ActionEvent event) throws IOException {

        name = txtName.getText();

        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/clientChat.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();

        stage.setTitle("Chat");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) throws IOException {
        btnSubmitOnAction(event);
    }

}
