package com.group.reproductorjava.model.DAOs;

import com.group.reproductorjava.model.Entity.Artista;
import com.group.reproductorjava.model.Entity.Disco;
import com.group.reproductorjava.model.interfaces.IDiscoDAO;
import com.group.reproductorjava.utils.LoggerClass;
import com.group.reproductorjava.utils.Manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

    /**
     * Retrieves a disc by its identifier.
     * @param id The identifier of the disc.
     * @return true if found and loaded successfully, false otherwise.
     */
    @Override
    public boolean getDisco(int id) {
        boolean result = false;
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

    /**
     * Retrieves all discs stored in the database.
     * @return A list of all stored discs.
     */
    @Override
    public List<Disco> getAllDiscos() {
        List<Disco> discos = manager.createQuery("SELECT d FROM Disco d").getResultList();
        return discos;
    }

    /**
     * Saves the disc in the database.
     * @return true if the operation is successful, false in case of an error.
     */
    @Override
    public boolean saveDisco() {
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            Disco aux = new Disco();
            aux.setName(this.getName());
            aux.setFecha(this.getFecha());
            aux.setPhoto(this.getPhoto());
            aux.setArtista(this.getArtista());
            aux.setCanciones(this.getCanciones());

            manager.persist(aux);
            transaction.commit();
            logger.info("Saved correctly");

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) transaction.rollback();
            logger.warning("Failed to save \n" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Deletes a disc from the database.
     * @param disco The disc to be deleted.
     * @return true if the operation is successful, false in case of an error or if the disc is null.
     */
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
