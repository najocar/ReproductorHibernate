package com.group.reproductorjava.controller;

import com.group.reproductorjava.model.DAOs.ListaDAO;
import com.group.reproductorjava.model.DAOs.UsuarioDAO;
import com.group.reproductorjava.model.DTOs.ControlDTO;
import com.group.reproductorjava.model.Entity.Lista;
import com.group.reproductorjava.HelloApplication;
import com.group.reproductorjava.model.Entity.Usuario;
import com.group.reproductorjava.utils.LoggerClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    @FXML
    private TableView<Lista> ListTable;
    @FXML
    private TableColumn NameColumn;
    @FXML
    private TableColumn DescriptionColumn;

    @FXML
    private TableColumn SuscriptionNameColumn;
    @FXML
    private TableColumn SuscriptionDescriptionColumn;

    @FXML
    private TableView<Lista> SuscriptionTable;

    @FXML
    private Label userName;

    @FXML
    private ImageView image;



    private ObservableList<Lista> listas;
    private ObservableList<Lista> suscriptions;

    UsuarioDAO userDao = new UsuarioDAO(2); //cambiar esto por el id del usuario loggeado
    static LoggerClass logger = new LoggerClass(HomeViewController.class.getName());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        listas = FXCollections.observableArrayList();
        suscriptions = FXCollections.observableArrayList();
        this.NameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        this.DescriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));

        this.SuscriptionNameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        this.SuscriptionDescriptionColumn.setCellValueFactory(new PropertyValueFactory("description"));

        generateListTable();
        generateSuscriptionTable();
        setInfoUser();
    }

    @FXML
    public void generateListTable() {

        listas.setAll(ListaDAO.getListByUser(ControlDTO.getUser()));
        this.ListTable.setItems(listas);
    }

    @FXML
    public void generateSuscriptionTable() {
        suscriptions.setAll(ControlDTO.getUser().getSubscriptionList());
        this.SuscriptionTable.setItems(suscriptions);
    }

    @FXML
    public void setInfoUser() {
        userName.setText(ControlDTO.getUser().getName());

            Usuario currentUser = ControlDTO.getUser();
            try {
                String imagePath = currentUser.getPhoto();
                InputStream inputStream = getClass().getResourceAsStream(imagePath);
                System.out.println(imagePath);

                if (inputStream != null) {
                    Image image1 = new Image(inputStream);
                    image.setImage(null);
                    image.setImage(image1);
                } else {
                    logger.warning("Error al cargar la imagen. InputStream es nulo. \n");
                }
            } catch (Exception e) {
                e.printStackTrace();
        }
    }

    @FXML
    public void goLogin() {
        try {
            HelloApplication.setRoot("LoginView");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goList() {
        if (selectList()!=null) {
            try {
                ControlDTO.setLista(selectList());
                HelloApplication.setRoot("CancionListView");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void newList() {
        try {
            HelloApplication.setRoot("newList");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void goAllListView() {
        try {
            HelloApplication.setRoot("AllListView");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void deleteList() {
        if (selectList()!=null) {
            new ListaDAO(selectList()).deleteLista(selectList());
//            listas.remove(selectList());
            generateListTable();
        }
    }

    @FXML
    public Lista selectList(){
        Lista result = null;
        Lista aux = this.ListTable.getSelectionModel().getSelectedItem();
        if (aux != null){
            result = aux;
        }
        return result;
    }

    @FXML
    public Lista selectSuscription(){
        Lista result = null;
        Lista aux = this.SuscriptionTable.getSelectionModel().getSelectedItem();
        if (aux != null){
            result = aux;
        }
        return result;
    }

    @FXML
    public void unsuscribe() {
        Lista list = selectSuscription();
        if(list == null || ControlDTO.getUser() == null) return;
        UsuarioDAO aux = new UsuarioDAO(ControlDTO.getUser());
        aux.removeSubscription(list);
        aux.saveUsuario();
        generateSuscriptionTable();
    }
}
