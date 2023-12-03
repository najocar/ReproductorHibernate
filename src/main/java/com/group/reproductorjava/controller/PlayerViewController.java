package com.group.reproductorjava.controller;

import com.group.reproductorjava.model.Entity.Cancion;
import com.group.reproductorjava.HelloApplication;
import com.group.reproductorjava.model.DAOs.CancionDAO;
import com.group.reproductorjava.model.DAOs.UsuarioDAO;
import com.group.reproductorjava.model.DTOs.ControlDTO;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javazoom.jl.player.Player;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Controlador para la vista del reproductor de música.
 */
public class PlayerViewController {

    @FXML
    private Button back_btn;

    @FXML
    private Button play_btn;

    @FXML
    private ProgressBar pb;

    @FXML
    private Button stop_btn;

    @FXML
    private Button last_btn;

    @FXML
    private Button next_btn;
    @FXML
    private ImageView songImage;

    @FXML
    private Label songNameLabel;

    @FXML
    private Label songDurationLabel;

    @FXML
    private Label songGenderLabel;

    @FXML
    private Label songDiscLabel;

    @FXML
    private Label songRepLabel;

    @FXML
    private Label userlabel;

    @FXML
    private ImageView image;

    private String selectedSongName;

    UsuarioDAO userDao = new UsuarioDAO(2);

    private boolean isPaused = false;
    private String currentSongPath;
    private Duration currentMediaTime;
    private Player player;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private Duration pauseTime;
    private Timeline timeline;


    public void initialize(){
        Image defaultImage = new Image(getClass().getResourceAsStream("/com/group/reproductorjava/images/good_times_with_bad_music_1050x700.jpg"));
        songImage.setImage(defaultImage);
        setInfoUser();
    }

    @FXML
    public void setInfoUser() {
        userlabel.setText(userDao.getName());

//        String imagePath = userDao.getPhoto();
//        if (imagePath != null) {
//            Image imagenJ = new Image(new File("com/group/reproductorjava/images/" +imagePath).toURI().toString());
//            image.setImage(imagenJ);
//        }
//        setSongName(ControlDTO.getUser().getName());
    }
    /**
     * Establece el nombre de la canción seleccionada.
     *
     * @param songName Nombre de la canción seleccionada.
     */
//    public void setSongName(String songName) {
//        this.selectedSongName = songName;
//        loadSelectedSong();
//    }

    /**
     * Maneja el evento de reproducción y pausa del reproductor.
     */
    @FXML
    private void Play() {
        if (isPlaying) {
//            pause();
            play_btn.setText("▶");
        } else {
//            playSelectedSong();
            play_btn.setText("⏸");
        }
        isPlaying = !isPlaying;
    }

//    private void playSelectedSong() {
//        try {
//            stop(); // Detener la reproducción actual antes de comenzar una nueva
//            String songPath = "src/main/resources/com/group/reproductorjava/songs/" + songNameLabel.getText() + ".mp3";
//            currentSongPath = songPath;
//
//            Media media = new Media(new File(songPath).toURI().toString());
//            mediaPlayer = new MediaPlayer(media);
//
//            mediaPlayer.setOnEndOfMedia(() -> stop());
//
//            if (currentMediaTime != null) {
//                mediaPlayer.seek(currentMediaTime);
//            }
//
//            mediaPlayer.play();
//            loadSelectedSong();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void pause() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            currentMediaTime = mediaPlayer.getCurrentTime();
        }
    }

    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            currentMediaTime = null;
        }
        isPlaying = false;
        play_btn.setText("▶");
    }

    /**
     * Navega de vuelta a la vista de inicio.
     *
     * @throws IOException Si hay un error al cambiar la vista.
     */
    @FXML
    private void goHome() throws IOException {
//        stop();
        HelloApplication.setRoot("Home-view");
    }

    /**
     * Carga la información de la canción seleccionada en la interfaz.
     */
    private void loadSelectedSong() {
        Cancion currentSong = ControlDTO.getSong();
        if (currentSong != null) {
            songNameLabel.setText(currentSong.getName());
            songDurationLabel.setText(String.valueOf(currentSong.getDuration()));
            songGenderLabel.setText(currentSong.getGender());
            songRepLabel.setText(String.valueOf(currentSong.getnReproductions()));
            songDiscLabel.setText(String.valueOf(currentSong.getDisco()));
            try {
                String imagePath = currentSong.getDisco().getPhoto();
                InputStream inputStream = getClass().getResourceAsStream(imagePath);
                System.out.println(imagePath);

                if (inputStream != null) {
                    Image image = new Image(inputStream);
                    songImage.setImage(null);
                    songImage.setImage(image);
                } else {
                    System.err.println("Error al cargar la imagen. InputStream es nulo.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        updateReproductions();
    }
    /*
     * Actualiza el número de reproducciones de la canción seleccionada.
     *
     * @param songName Nombre de la canción para la cual se actualizarán las reproducciones.
     */
    private void updateReproductions() {
        Cancion currentSong = ControlDTO.getSong();
        if (currentSong != null) {
            CancionDAO cancionDAO= new CancionDAO(currentSong);
            cancionDAO.oneReproduction();
            ControlDTO.getSong().setnReproductions(ControlDTO.getSong().getnReproductions()+1);
        }
    }
}