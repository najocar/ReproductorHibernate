package com.group.reproductorjava.controller;

import com.group.reproductorjava.model.DAOs.UsuarioDAO;
import com.group.reproductorjava.model.DTOs.ControlDTO;
import com.group.reproductorjava.HelloApplication;
import com.group.reproductorjava.model.Entity.Usuario;
import com.group.reproductorjava.utils.LoggerClass;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class LoginViewController {

    @FXML
    private TextField UserField;

    @FXML
    private PasswordField PassField;

    @FXML
    private Button login_btn;

    @FXML
    private Button register_btn;

    static LoggerClass logger = new LoggerClass(LoginViewController.class.getName());

    @FXML
    private void login() {
        try {
            String nickname = UserField.getText();

            if (nickname.isEmpty() || nickname.isBlank()) {
                showError("El campo del nickname está vacío");
                return;
            }

            Usuario aux = findUser(nickname);

            if (aux == null) {
                showError("No se ha encontrado nickname");
                return;
            }

            ControlDTO.setUser(aux);
            home();

            logger.info("Login success");

        } catch (Exception err) {
            logger.warning("Unknown error in login method");
            logger.warning(err.getMessage());
        }
    }
    @FXML
    private void register() throws IOException {
        try {
            HelloApplication.setRoot("RegisterView");
        } catch (Exception err) {
            logger.warning("Error when going to Register view");
            logger.warning(err.getMessage());
        }
    }

    @FXML
    private void home() throws IOException {
        try {
            HelloApplication.setRoot("Home-view");
        } catch (Exception err) {
            logger.warning("Error when going to Home view");
            logger.warning(err.getMessage());
        }
    }

    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Log-in Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private Usuario findUser(String username) {
        List<Usuario> userList = UsuarioDAO.getAllUsuarios();
        for (Usuario user: userList) {
            if (user.getName().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
