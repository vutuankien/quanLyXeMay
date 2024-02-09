package org.example.quanlyxemay;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {
    @FXML private Scene scene;
    @FXML private Parent root;
    @FXML private Stage stage;

    @FXML private TextField username;
    @FXML private TextField password;


    public void login(ActionEvent e) throws IOException {
        if(username.getText() == null || password.getText() == null){
            System.out.println("login fail");
            showAlert(null,"please fill all the blank","login error", Alert.AlertType.ERROR);
        } else if (username.getText().toLowerCase().equals("admin") && password.getText().equalsIgnoreCase("123456")) {
            root = FXMLLoader.load(getClass().getResource("adminPage.fxml"));
            scene = new Scene(root);
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setTitle("admin page");
            stage.setScene(scene);
            stage.show();
            System.out.println("login done");
            showAlert(null,"login successfully","login", Alert.AlertType.INFORMATION);
        }
        else{
            System.out.println("login fail");
            showAlert(null,"login fail","login", Alert.AlertType.ERROR);
        }
    }
    public void showAlert(String header, String content, String title, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setContentText(content);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
