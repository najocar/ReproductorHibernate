package com.group.reproductorjava.controller;

import com.group.reproductorjava.model.DAOs.UsuarioDAO;
import com.group.reproductorjava.model.DTOs.ControlDTO;
import com.group.reproductorjava.HelloApplication;
import com.group.reproductorjava.model.Entity.Usuario;
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

    @FXML
    private void login() throws IOException {
        String nickname = UserField.getText();
        if (nickname.isEmpty()) {
            showError("El campo del nickname está vacío");
        } else {

//            UsuarioDAO UDAO=new UsuarioDAO(1);
            List<Usuario> users= UsuarioDAO.getAllUsuarios();
            for (Usuario usuario : users) {
                if (nickname.equals(usuario.getName())) {
                    ControlDTO.setUser(usuario);
                    HelloApplication.setRoot("Home-view");
                    break; // Si ya encontramos a Raúl, no es necesario seguir buscando
                }
                else{
                    showError("no se ha encontrado el nickname");
                }
            }
        }
    }
    @FXML
    private void register() throws IOException {
        System.out.println("no está implementado");
    }

    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Log-in Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
