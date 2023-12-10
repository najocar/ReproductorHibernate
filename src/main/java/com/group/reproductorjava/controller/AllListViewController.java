package com.group.reproductorjava.controller;

import com.group.reproductorjava.HelloApplication;
import com.group.reproductorjava.model.DAOs.ListaDAO;
import com.group.reproductorjava.model.DAOs.UsuarioDAO;
import com.group.reproductorjava.model.DTOs.ControlDTO;
import com.group.reproductorjava.model.Entity.Lista;
import com.group.reproductorjava.utils.LoggerClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AllListViewController implements Initializable {

    @FXML
    private TableView<Lista> listTable;

    @FXML
    private TableColumn colName, colDescription;

    @FXML
    private Label userName;

    private ObservableList<Lista> listaList;

    static LoggerClass logger = new LoggerClass(AllListViewController.class.getName());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaList = FXCollections.observableArrayList();
        this.colName.setCellValueFactory(new PropertyValueFactory("name"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory("description"));

        loadTable();
        setInfoUser();
    }

    private void loadTable() {
        listaList.clear();
        List<Lista> aux = ListaDAO.getAllListas();
        if(aux == null) return;
        listaList.setAll(aux);
        listTable.setItems(listaList);
        listTable.refresh();
    }

    @FXML
    public void setInfoUser() {
        userName.setText(ControlDTO.getUser().getName());
    }

    @FXML
    public void goHome() {
        try {
            HelloApplication.setRoot("Home-view");
        } catch (IOException err) {
            logger.warning("Error navigate to Home-View");
            logger.warning(err.getMessage());
        }
    }

    @FXML
    private Lista selectList(){
        Lista result = null;
        Lista aux = this.listTable.getSelectionModel().getSelectedItem();
        if(aux!=null)result = aux;
        return result;
    }

    @FXML
    public void subscribe() {
        Lista list = selectList();
        if(list == null || ControlDTO.getUser() == null) return;

        UsuarioDAO aux = new UsuarioDAO(ControlDTO.getUser());

        aux.addSubscription(list);
        aux.saveUsuario();
    }

    @FXML
    public void goList() {
        Lista list = selectList();
        if(list == null) return;
        ControlDTO.setLista(list);
        try {
            ControlDTO.setLista(selectList());
            HelloApplication.setRoot("CancionListView");
        } catch (IOException err) {
            logger.warning("Error navigate to CancionListView");
            logger.warning(err.getMessage());
        }
    }

}
