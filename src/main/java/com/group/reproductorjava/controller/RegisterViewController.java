package com.group.reproductorjava.controller;

import com.group.reproductorjava.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterViewController implements Initializable {

    @FXML
    private TextField userField, passField;

    @FXML
    private Button register_btn, goLogin_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void goLogin() {
        try {
            HelloApplication.setRoot("LoginView");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void register() {
        return;
    }
}
