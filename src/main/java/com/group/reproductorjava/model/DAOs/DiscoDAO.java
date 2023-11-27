package com.group.reproductorjava.model.DAOs;

import com.group.reproductorjava.model.Entity.Artista;
import com.group.reproductorjava.model.Entity.Disco;
import com.group.reproductorjava.model.interfaces.IDiscoDAO;
import com.group.reproductorjava.utils.LoggerClass;
import com.group.reproductorjava.utils.Manager;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class DiscoDAO extends Disco implements IDiscoDAO {
    static LoggerClass logger = new LoggerClass(DiscoDAO.class.getName());
    private static EntityManager manager = Manager.getEntityManager();

    public DiscoDAO(int id) {
        getDisco(id);
    }

    public DiscoDAO(Disco disco) {
        super(disco.getId(), disco.getName(), disco.getFecha(), disco.getPhoto(), disco.getArtista());
    }

    public DiscoDAO(int id, String nombre, LocalDate fecha, String photo, Artista artista) {
        super(id, nombre, fecha, photo, artista);
    }

    @Override
    public boolean getDisco(int id) {
        Boolean result = false;
        try {
            Disco disco = manager.find(Disco.class, id);
            if (disco != null) {
                setId(disco.getId());
                setName(disco.getName());
                setFecha(disco.getFecha());
                setPhoto(disco.getPhoto());
                setArtista(disco.getArtista());
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public List<Disco> getAllDiscos() {
        List<Disco> discos = manager.createQuery("SELECT d FROM Disco d").getResultList();
        return discos;
    }

    @Override
    public boolean saveDisco() {
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

    @Override
    public boolean deleteDisco(Disco disco) {
        try {
            manager.getTransaction().begin();
            if (disco != null) {
                manager.remove(disco);
                manager.getTransaction().commit();
            } else {
                return false;
            }
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        return true;
    }
}
