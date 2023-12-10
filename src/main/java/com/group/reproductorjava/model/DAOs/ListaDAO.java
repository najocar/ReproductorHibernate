package com.group.reproductorjava.model.DAOs;

import com.group.reproductorjava.model.Entity.*;
import com.group.reproductorjava.model.interfaces.IListaDAO;
import com.group.reproductorjava.model.Connection.MariaDBConnection;
import com.group.reproductorjava.utils.LoggerClass;
import com.group.reproductorjava.utils.Manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListaDAO extends Lista implements IListaDAO {

    static LoggerClass logger = new LoggerClass(ListaDAO.class.getName());

    private static EntityManager manager = Manager.getEntityManager();

    public ListaDAO(int id, String nombre, Usuario userCreator, String descripcion) {
        super(id, nombre, userCreator, descripcion);
    }

    public ListaDAO(int id) {
        getLista(id);
    }

    public ListaDAO(Lista lista) {
        super(lista.getId(), lista.getName(), lista.getUserCreator(), lista.getDescription());
        this.canciones = lista.getCanciones();
        this.comentarios = lista.getComentarios();
        this.userCreator = lista.getUserCreator();
    }

    /**
     * Retrieves a list by its identifier
     * @param id The identifier of the list
     * @return true if found and loaded successfully, false otherwise
     */
    @Override
    public boolean getLista(int id) {
        Boolean result = false;
        Lista a = manager.find(Lista.class, id);
        if (a != null) {
            setId(a.getId());
            setName(a.getName());
            setDescription(a.getDescription());
            setCanciones(a.getCanciones());
            setComentarios(a.getComentarios());
            setUserCreator(a.getUserCreator());
            result = true;
        }
        return result;
    }

    /**
     * Retrieves a list by its name
     * @param name The name of the list
     * @return false, as this method is not implemented
     */
    @Override
    public boolean getLista(String name) {
        return false;
    }

    /**
     * Retrieves all lists stored in the database
     * @return A list of all stored lists
     */
    public static List<Lista> getAllListas() {
        List<Lista> listas = manager.createQuery("FROM Lista").getResultList();
        return listas;
    }

    /**
     * Saves the list in the database
     * @return true if the operation is successful, false in case of an error
     */
    public boolean save() {
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            Lista lista = new Lista();
            lista.setName(this.getName());
            lista.setDescription(this.getDescription());
            lista.setUserCreator(this.getUserCreator());
            lista.setCanciones(this.getCanciones());
            lista.setComentarios(this.getComentarios());

            if (this.getId() > 0) {
                lista.setId(this.getId());
                lista = manager.find(Lista.class, lista.getId());
            }

            manager.persist(lista);

            transaction.commit();
            logger.info("Saved correctly");
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            logger.warning("Failed to save the list \n" + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Retrieves all lists created by a user
     * @param user The user whose lists are desired
     * @return A list of lists created by the user
     */
    public static List<Lista> getListByUser(Usuario user) {
        String jpql = "FROM Lista WHERE userCreator = :usuario";
        TypedQuery<Lista> query = manager.createQuery(jpql, Lista.class);
        query.setParameter("usuario", user);
        List<Lista> listas = query.getResultList();
        return listas;
    }

    /**
     * Deletes a list from the database
     * @param lista The list to be deleted
     * @return true if the operation is successful, false in case of an error
     */
    @Override
    public boolean deleteLista(Lista lista) {
        try {
            manager.getTransaction().begin();
            if (manager.contains(lista)) {
                lista = manager.find(Lista.class, lista.getId());
            }
            manager.remove(lista);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
//            manager.close();
        }
        return true;
    }

    /**
     * Adds a comment to the list
     * @param coment The comment to be added
     * @return true if the comment is added successfully, false if it already exists in the list
     */
    @Override
    public boolean addComment(Comentario coment) {
        if (!comentarios.contains(coment))
            return comentarios.add(coment);
        return false;
    }

    /**
     * Deletes a comment from the list
     * @param coment The comment to be deleted
     * @return true if the comment is deleted successfully, false if it does not exist in the list
     */
    @Override
    public boolean deleteComment(Comentario coment) {
        if (comentarios.contains(coment))
            return comentarios.remove(coment);
        return false;
    }

    /**
     * Adds a song to the list
     * @param cancion The song to be added
     * @return true if the song is added successfully, false if it already exists in the list
     */
    public boolean addCancion(Cancion cancion) {
        if (!this.canciones.contains(cancion)){
            return this.canciones.add(cancion);
        }
        return false;
    }

    /**
     * Retrieves the songs of a list
     * @param list The list from which the songs are obtained
     * @return A list of songs from the given list
     */
    public static List<Cancion> getCancionesOfTheList(Lista list) {
        return list.getCanciones();
    }

    /**
     * Retrieves the comments of the list
     * @return A list of comments associated with the list
     */
    @Override
    public List<Comentario> getComentarios() {
        if (comentarios == null) {
            setComentarios(ComentarioDAO.getComentariosByLista(getId()));
        }
        return super.getComentarios();
    }
}
