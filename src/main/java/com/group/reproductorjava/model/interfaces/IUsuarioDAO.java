package com.group.reproductorjava.model.interfaces;

public interface IUsuarioDAO {
    boolean getUsuario(int id);
    boolean saveUsuario();
    boolean deleteUsuario();
}
