package com.group.reproductorjava.model.interfaces;

import com.group.reproductorjava.model.Entity.Artista;

import java.util.List;

public interface IArtistaDAO {
    boolean getArtista(int id);
    boolean getArtista(String name);
    List<Artista> getAllArtists();
    boolean saveArtista();
    boolean deleteArtista();
}
