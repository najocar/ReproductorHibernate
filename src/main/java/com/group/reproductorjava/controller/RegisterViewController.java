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
        String username = userField.getText();
        if(username == null || username.isEmpty() || username.isBlank()) return;

        // comprobar que no exista ningun usuario mas con ese username

        // guardar el usuario en la base de datos

        // settear el usuario en el ControlDTO para poder utilizarlo en las distintas vistas

        // navegar hasta la vista home

        return;
    }
}
