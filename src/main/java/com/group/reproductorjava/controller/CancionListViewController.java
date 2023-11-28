package com.group.reproductorjava.controller;

import com.group.reproductorjava.model.DAOs.ComentarioDAO;
import com.group.reproductorjava.model.DAOs.ListaDAO;
import com.group.reproductorjava.model.DTOs.ControlDTO;
import com.group.reproductorjava.model.Entity.Cancion;
import com.group.reproductorjava.HelloApplication;
import com.group.reproductorjava.model.Entity.Comentario;
import com.group.reproductorjava.model.DTOs.ComentarioDTO;
import com.group.reproductorjava.utils.LoggerClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class CancionListViewController implements Initializable {

    @FXML
    private Button btnHome, btnPlay, btnExit;

    @FXML
    private TextField inputComment;

    @FXML
    private TableView<Cancion> tableSong;

    @FXML
    private TableColumn colName;

    @FXML
    private TableView<ComentarioDTO> tableComment;

    @FXML
    private TableColumn colUsuarioComment, colComentarioComment;

    @FXML
    private Label labelListaName, labelUserName;

    private ObservableList<Cancion> songList;
    private ObservableList<ComentarioDTO> commentList;

    static LoggerClass logger = new LoggerClass(CancionListViewController.class.getName());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songList = FXCollections.observableArrayList();
        this.colName.setCellValueFactory(new PropertyValueFactory("name"));

        commentList = FXCollections.observableArrayList();
        this.colUsuarioComment.setCellValueFactory(new PropertyValueFactory("name"));
        this.colComentarioComment.setCellValueFactory(new PropertyValueFactory("message"));

        loadTable();
        loadView();
    }
;
    public void loadTable(){
        loadTableSong();
        loadTableComments();
    }

    public void loadTableSong() {
        songList.clear();
        List<Cancion> auxCancionList = ListaDAO.getCancionesOfTheList(ControlDTO.getLista());
        if(auxCancionList == null) return;
        songList.addAll(auxCancionList);
        tableSong.setItems(songList);
        tableSong.refresh();
    }

    public void loadTableComments() {
        commentList.clear();
        List<Comentario> auxCommentList = ComentarioDAO.getComentariosByLista(ControlDTO.getLista().getId());
        if(auxCommentList == null) return;

        for(Comentario comment: auxCommentList) {
            commentList.add(new ComentarioDTO(comment.getUsuario().getName(), comment.getMessage()));
        }
        tableComment.setItems(commentList);
        tableComment.refresh();
    }

    public void loadView(){
        if(ControlDTO.getLista() != null)labelListaName.setText(ControlDTO.getLista().getName());
        if(ControlDTO.getUser() != null)labelUserName.setText(ControlDTO.getUser().getName());

    }

    @FXML
    private Cancion selectSong(){
        Cancion result = null;
        Cancion aux = this.tableSong.getSelectionModel().getSelectedItem();
        if(aux!=null)result = aux;
        return result;
    }

    @FXML
    public void play(){
        Cancion aux = selectSong();
        if(aux == null) return;
        ControlDTO.setSong(aux);
        System.out.println(ControlDTO.getSong());
        try {
            HelloApplication.setRoot("PlayerView");
        } catch (IOException err) {
            logger.warning("Error navigate to PlayerView");
            logger.warning(err.getMessage());
        }
    }

    @FXML
    public void addComment() {
        String text = inputComment.getText();
        if(text.isEmpty() || text.isBlank()) return;
        ListaDAO ldao = new ListaDAO(ControlDTO.getLista());

//        System.out.println(ControlDTO.getLista());
        Comentario aux = new Comentario(-1, LocalDate.now(), text, ControlDTO.getUser(), ControlDTO.getLista());
        System.out.println(aux);


        ldao.addComment(aux);
        ldao.save();
//        ldao.save();
//        System.out.println(aux.getLista());
//        aux.saveComentario();

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
    public void goBack(){
        try {
            HelloApplication.setRoot("Home-view");
        } catch (IOException err) {
            logger.warning("Error navigate to HomeView");
            logger.warning(err.getMessage());
        }
    }

}
