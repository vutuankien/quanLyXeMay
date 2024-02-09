package org.example.quanlyxemay;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("quản lý xe máy!");
        stage.setScene(scene);
        stage.show();
        System.out.println("load window");
    }

    public static void main(String[] args) {
        launch();
    }
}