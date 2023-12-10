package com.group.reproductorjava.model.DAOs;

import com.group.reproductorjava.model.Entity.Cancion;
import com.group.reproductorjava.model.Entity.Disco;
import com.group.reproductorjava.model.Entity.Lista;
import com.group.reproductorjava.model.interfaces.ICancionDAO;
import com.group.reproductorjava.model.Connection.MariaDBConnection;
import com.group.reproductorjava.utils.LoggerClass;
import com.group.reproductorjava.utils.Manager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CancionDAO extends Cancion implements ICancionDAO {

    static LoggerClass logger = new LoggerClass(CancionDAO.class.getName());
    private static EntityManager manager = Manager.getEntityManager();

    public CancionDAO(int id){
        getCancion(id);
    }

    public CancionDAO(Cancion song){
        super(song.getId(), song.getName(), song.getDuration(), song.getGender(), song.getnReproductions(), song.getDisco());
    }

    public CancionDAO(int id, String nombre, int duration, String gender, int nReproductions, Disco disco){
        super(id, nombre, duration, gender, nReproductions, disco);
    }

    /**
     * Method to get Cancion by id to cancion
     * @param id: int
     * @return boolean
     * If return true, success
     */
    @Override
    public boolean getCancion(int id) {
            Boolean result = false;
//        manager.getTransaction().begin();
            Cancion a = manager.find(Cancion.class, id);
            if (a!=null){
                setId(a.getId());
                setName(a.getName());
                setDuration(a.getDuration());
                setGender(a.getGender());
                setnReproductions(a.getnReproductions());
                setDisco(a.getDisco());
                result = true;
            }
            return result;
    }

    /**
     * Static Method to get all Cancion
     * @return List<Cancion> | null
     * if dont return null, success
     */
    public static List<Cancion> getAllCanciones() {
        List<Cancion> canciones = manager.createQuery("FROM Cancion").getResultList();
        return canciones;
    }

    /**
     * Method to save Cancion
     * @return boolean
     * If return true, success
     * If Cancion exist, update Cancion
     */
    @Override
    public boolean saveCancion() {
        try {
            manager.getTransaction().begin();
            manager.persist(this);
            manager.getTransaction().commit();
            logger.info("Saved Correctly");
        } catch (Exception e) {
            logger.warning("Failed to save \n" + e.getMessage());
        }
        return true;
    }

    /**
     * Method to remove Cancion
     * @return boolean
     * If return true, success
     */
    @Override
    public boolean deleteCancion(Cancion cancion) {
        try {
            manager.getTransaction().begin();
            if (!manager.contains(cancion)) {
                cancion = manager.find(Cancion.class, cancion.getId());
            }
            manager.remove(cancion);
            manager.getTransaction().commit();
            logger.info("Removed correctly");
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            logger.warning("Failed to remove the song \n" + e.getMessage());
        }
        return true;
    }

    /**
     * Method to increase plus one the nReproducciones of a Cancion
     */
    @Override
    public void oneReproduction() {
        setnReproductions(getnReproductions()+1);
        saveCancion();
    }

    /**
     * Static Method to get all Cancion by gender
     * @param gender: string
     * @return List<Lista> | null
     * If dont return null, success
     */
    public static List<Cancion> selectByGender(String gender) {
        String jpql = "FROM Cancion WHERE gender = :gender";
        TypedQuery<Cancion> query = manager.createQuery(jpql, Cancion.class);
        query.setParameter("gender", gender);
        List<Cancion> canciones = query.getResultList();
        return canciones;
    }

}
