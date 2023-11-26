package com.group.reproductorjava.model.interfaces;

import com.group.reproductorjava.model.Entity.Comentario;
import com.group.reproductorjava.model.Entity.Lista;

public interface IListaDAO {
    boolean getLista(int id);
    boolean getLista(String name);
//    boolean save();
    boolean deleteLista(Lista Lista);
    boolean addComment(Comentario coment);
    boolean deleteComment(Comentario coment);
}
