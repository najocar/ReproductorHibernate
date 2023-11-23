package com.group.reproductorjava.model.interfaces;

public interface IUsuarioDAO {
    boolean getUsuario(int id);
    boolean getUsuario(String name);
//    static List<Usuario> getAllUsuarios()
    boolean saveUsuario();
    boolean deleteUsuario();
}
