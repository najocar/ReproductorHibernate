package com.group.reproductorjava.model.DAOs;

import com.group.reproductorjava.model.Entity.Comentario;
import com.group.reproductorjava.model.Entity.Lista;
import com.group.reproductorjava.model.Entity.Usuario;
import com.group.reproductorjava.model.interfaces.IComentarioDAO;
import com.group.reproductorjava.utils.LoggerClass;
import com.group.reproductorjava.utils.Manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;

public class ComentarioDAO extends Comentario implements IComentarioDAO {

    static LoggerClass logger = new LoggerClass(ComentarioDAO.class.getName());
    private static EntityManager manager = Manager.getEntityManager();

    public ComentarioDAO(int id){
        getComentario(id);
    }

    public ComentarioDAO(int id, LocalDate date, String message, Usuario user, Lista playlist){
        super(id, date, message, user, playlist);
    }

    public ComentarioDAO(Comentario c){
        super(c.getId(), c.getDate(), c.getMessage(), c.getUsuario(), c.getLista());
    }

    /**
     * Method to get Comentario by Id
     * @param id: int
     * @return boolean
     * true if success
     */
    @Override
    public boolean getComentario(int id) {
        boolean result = false;
        Comentario aux = manager.find(Comentario.class, id);
        if(aux != null) {
            setId(aux.getId());
            setDate(aux.getDate());
            setLista(aux.getLista());
            setUsuario(aux.getUsuario());
            setMessage(aux.getMessage());
            result = true;
        }
        return result;
    }

    /**
     * Static Method to get all Comentario
     * @return List<Comentario> | null
     * if dont return null, success
     */
    public static List<Comentario> getAllComentarios(){
        return manager.createQuery("FROM Comentario").getResultList();
    }

    /**
     * Method to remove Comentario
     * @return boolean
     * true if success
     */
    @Override
    public boolean deleteComentario() {
        Comentario aux = this;
        try {
            manager.getTransaction().begin();
            if(!manager.contains(this)) {
                aux = manager.find(Comentario.class, this);
            }
            manager.remove(aux);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if(manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            logger.warning("Failed to remove \n" + e.getMessage());
        }
        return true;
    }

    /**
     * Static Method to get all Comentario on User
     * @param idUser: int
     * @return List<Comentario> | null
     * if dont return null, success
     */
    public static List<Comentario> getComentariosByUsuario(int idUser){
        Usuario aux = manager.find(Usuario.class, idUser);
        if(aux == null) return null;
        return aux.getCommentList();
    }

    /**
     * Static Method to get all Comentario on Playlist (List)
     * @param idLista: int
     * @return List<Comentario> | null
     * if dont return null, success
     */
    public static List<Comentario> getComentariosByLista(int idLista){
        Lista aux = manager.find(Lista.class, idLista);
        if(aux == null) return null;
        return aux.getComentarios();
    }
}
