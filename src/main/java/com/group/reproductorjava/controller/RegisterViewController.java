package com.group.reproductorjava.controller;

import com.group.reproductorjava.HelloApplication;
import com.group.reproductorjava.model.DAOs.UsuarioDAO;
import com.group.reproductorjava.model.DTOs.ControlDTO;
import com.group.reproductorjava.model.Entity.Usuario;
import com.group.reproductorjava.utils.LoggerClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterViewController implements Initializable {

    @FXML
    private TextField userField, passField;

    @FXML
    private Button register_btn, goLogin_btn;

    static LoggerClass logger = new LoggerClass(RegisterViewController.class.getName());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void goLogin() {
        try {
            HelloApplication.setRoot("LoginView");
        } catch (IOException e) {
            logger.warning("Error go to LoginView");
        }
    }

    @FXML
    private void register() {
        try {
            String username = userField.getText();
            if((username == null || username.isEmpty() || username.isBlank()) || !(checkUsername(username))) return;

            Usuario user = new Usuario(username, "email", "photo", 1);
            UsuarioDAO userDAO = new UsuarioDAO(user);
            userDAO.saveUsuario();

            ControlDTO.setUser(userDAO);

            logger.info("Register process success");

            HelloApplication.setRoot("Home-view");
        } catch (IOException err) {
            logger.warning("Error navigate to Home-View");
            logger.warning(err.getMessage());
        } catch (Exception err) {
            logger.warning("Error in register process");
            logger.warning(err.getMessage());
        }
    }

    private boolean checkUsername(String username) {
        List<Usuario> userList = UsuarioDAO.getAllUsuarios();
        for (Usuario user: userList) {
            if (user.getName().equals(username)) {
               return false;
            }
        }
        return true;
    }
}
