package com.group.reproductorjava.model.DAOs;

import com.group.reproductorjava.model.Entity.*;
import com.group.reproductorjava.model.interfaces.IListaDAO;
import com.group.reproductorjava.model.Connection.MariaDBConnection;
import com.group.reproductorjava.utils.LoggerClass;
import com.group.reproductorjava.utils.Manager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListaDAO extends Lista implements IListaDAO {

    private final static String INSERT = "INSERT INTO lista (nombre,id_user,descripcion) VALUES(?,?,?)";
    private final static String UPDATE = "UPDATE lista SET nombre=?,descripcion=? WHERE id=?";
    private final static String DELETE = "DELETE FROM lista WHERE id=?";
    private final static String SELECTBYID = "SELECT id,nombre,id_user,descripcion FROM lista WHERE id=?";
    private final static String SELECTALL = "SELECT id,nombre,id_user,descripcion FROM lista";
    private final static String SELECTBYCREADOR = "SELECT id,nombre,id_user,descripcion FROM user WHERE id_user=?";
    private final static String SAVESONGS = "INSERT INTO cancion_lista (id_lista, id_cancion) VALUES(?,?)";
    private final static String DELETESONGS = "DELETE FROM cancion_lista WHERE id=?";

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

    @Override
    public boolean getLista(int id) {
        Boolean result = false;
//        manager.getTransaction().begin();
        Lista a = manager.find(Lista.class, id);
        if (a!=null){
            setId(a.getId());
            setName(a.getName());
            setDescription(a.getDescription());
            setCanciones(a.getCanciones());
            setComentarios(a.getComentarios());
            setUserCreator(a.getUserCreator());
            result = true;
        }
//        manager.getTransaction().commit();
//        manager.close();
        return result;
    }

    @Override
    public boolean getLista(String name) {
        return false;
    }

    public static List<Lista> getAllListas() {
        List<Lista> listas = manager.createQuery("FROM Lista").getResultList();
        return listas;
    }

    @Override
    public boolean save() {
        try {
            manager.getTransaction().begin();
            manager.persist(this);
            manager.getTransaction().commit();
            logger.info("Saved Correctly");
        } catch (Exception e) {
            logger.warning("Failed to save \n" + e.getMessage());
        } finally {
            manager.close();
        }
        return true;
    }

//    public boolean saveSongRelation(Cancion song) throws SQLException {
//        if (canciones==null) getCanciones();
//        canciones.add(song);
//        Connection conn = MariaDBConnection.getConnection();
//        if (conn == null) return false;
//
//        try (PreparedStatement ps = conn.prepareStatement(SAVESONGS)) {
//            ps.setInt(1, this.getId());
//            ps.setInt(2, song.getId());
//
//            if (ps.executeUpdate() == 1) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }

    public static List<Lista> getListByUser(Usuario user) {
        String jpql = "FROM Lista WHERE userCreator = :usuario";
        TypedQuery<Lista> query = manager.createQuery(jpql, Lista.class);
        query.setParameter("usuario", user);
        List<Lista> listas = query.getResultList();
        return listas;
    }

    @Override
    public boolean deleteLista(Lista lista) {
        try {
            manager.getTransaction().begin();
            if (!manager.contains(lista)) {
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
            manager.close();
        }
        return true;
    }

    @Override
    public boolean addComment(Comentario coment) {
        if (!comentarios.contains(coment))
            return comentarios.add(coment);
        return false;
    }

    @Override
    public boolean deleteComment(Comentario coment) {
        if (comentarios.contains(coment))
            return comentarios.remove(coment);
        return false;
    }


    public boolean addCancion(Cancion cancion) {
        if (!canciones.contains(cancion))
            return canciones.add(cancion);
        return false;
    }

//    @Override
//    public boolean deleteComment(Comentario coment) {
//        if (comentarios.contains(coment))
//            return comentarios.remove(coment);
//        return false;
//    }

    public static List<Cancion> getCancionesOfTheList(Lista list) {
        return list.getCanciones();
    }

    @Override
    public List<Comentario> getComentarios() {
        if (comentarios == null) {
            setComentarios(ComentarioDAO.getComentariosByLista(getId()));
        }
        return super.getComentarios();
    }

//    @Override
//    public Usuario getUserCreator() {
//        if (userCreator == null) {
//            setUserCreator(UsuarioDAO.getUsuario(getId()));
//        }
//        return super.getUserCreator();
//    }
}
