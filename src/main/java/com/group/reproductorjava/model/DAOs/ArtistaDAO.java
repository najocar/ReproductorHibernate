package com.group.reproductorjava.model.DAOs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.group.reproductorjava.model.Connection.MariaDBConnection;
import com.group.reproductorjava.model.Entity.Artista;
import com.group.reproductorjava.model.Entity.Disco;
import com.group.reproductorjava.model.interfaces.IArtistaDAO;
import com.group.reproductorjava.utils.LoggerClass;
import com.group.reproductorjava.utils.Manager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ArtistaDAO extends Artista implements IArtistaDAO {

    private final static String INSERT = "INSERT INTO artista (nombre, nacionalidad, foto) VALUES (?, ?, ?)";
    private final static String UPDATE = "UPDATE artista SET nombre=?, foto=?, nacionalidad=? WHERE id=?";
    private final static String DELETE = "DELETE FROM artista WHERE id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM artista WHERE id=?";
    private final static String SELECT_ALL = "SELECT * FROM artista";
    private final static String SELECT_BY_NAME = "SELECT * FROM artista WHERE nombre=?";

    /**
     *
     */
    static LoggerClass logger = new LoggerClass(ArtistaDAO.class.getName());
    private static EntityManager manager = Manager.getEntityManager();

    public ArtistaDAO(int id) {
        getArtista(id);
    }

    public ArtistaDAO(Artista a) {
        super(a.getId(), a.getName(), a.getPhoto(), a.getNacionality());
        this.setDiscos(a.getDiscos());
    }

    public ArtistaDAO(int id, String name, String photo, String nacionality) {
        super(id, name, photo, nacionality);
    }

    public ArtistaDAO() {

    }

    /**
     * Metodo save Artista que guarda un artista en la base de datos
     *
     * @return true si se guarda
     * @return false si no se guarda
     */
    public boolean saveArtista() {
        try {
            manager.getTransaction().begin();
            if (getId() != -1) {
                manager.persist(this);
            } else {
                manager.persist(this);
                manager.flush();
            }
            manager.getTransaction().commit();
            logger.info("Saved Correctly");
            return true;
        } catch (Exception e) {
            logger.warning("Failed to save \n" + e.getMessage());
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            return false;
        } finally {
            manager.close();
        }
    }

    /**
     * Metodo updateArtista que actualiza los datos de un artista existente
     *
     * @return true si se hace correctamente
     * @return false si hubo algun fallo
     */
    public boolean updateArtista() {
        try {
            manager.getTransaction().begin();
            if (getId() != -1) {
                manager.merge(this);
                manager.flush();
                manager.getTransaction().commit();
                logger.info("Updated Correctly");
                return true;
            } else {
                logger.warning("Trying to update an Artist without ID");
                return false;
            }
        } catch (Exception e) {
            logger.warning("Failed to update \n" + e.getMessage());
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            return false;
        } finally {
            manager.close();
        }
    }

    /**
     * Metodo delete artista que borra el artista de la base de datos
     *
     * @return true si se ha borrado
     * @retrun false si no se ha podido borrar
     */
    public boolean deleteArtista() {
        try {
            manager.getTransaction().begin();
            if (getId() != -1) {
                Artista artist = manager.find(Artista.class, getId());
                manager.remove(artist);
                manager.flush();
                manager.getTransaction().commit();
                logger.info("Deleted Correctly");
                return true;
            } else {
                logger.warning("Trying to delete an Artist without ID");
                return false;
            }
        } catch (Exception e) {
            logger.warning("Failed to delete \n" + e.getMessage());
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            return false;
        } finally {
            manager.close();
        }
    }

    /**
     * Metodo get artista que busca el artista en la base de datos por el id
     *
     * @param id del artista a buscar
     * @return true si se ha encontrado
     * @return false si no se ha encontrado
     */
    public boolean getArtista(int id) {
        try {
            manager.getTransaction().begin();
            Artista artist = manager.find(Artista.class, id);
            if (artist != null) {
                setId(artist.getId());
                setName(artist.getName());
                setNacionality(artist.getNacionality());
                setPhoto(artist.getPhoto());
                manager.getTransaction().commit();
                return true;
            } else {
                logger.warning("Artist not found with ID: " + id);
                manager.getTransaction().commit();
                return false;
            }
        } catch (Exception e) {
            logger.warning("Failed to get Artist by ID \n" + e.getMessage());
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            return false;
        } finally {
            manager.close();
        }
    }

    /**
     * Metodo getArtista que busca al artista en la base de datos por el nombre del
     * artista
     *
     * @param name del artista a buscar
     * @return true si se ha encontrado
     * @return false si no se ha encontrado
     */
    @Override
    public boolean getArtista(String name) {
        try {
            manager.getTransaction().begin();
            String jpql = "FROM Artista WHERE name = :name";
            TypedQuery<Artista> query = manager.createQuery(jpql, Artista.class);
            query.setParameter("name", name);
            Artista artist = query.getSingleResult();
            if (artist != null) {
                setId(artist.getId());
                setName(artist.getName());
                setNacionality(artist.getNacionality());
                setPhoto(artist.getPhoto());
                manager.getTransaction().commit();
                return true;
            } else {
                logger.warning("Artist not found with name: " + name);
                manager.getTransaction().commit();
                return false;
            }
        } catch (Exception e) {
            logger.warning("Failed to get Artist by name \n" + e.getMessage());
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            return false;
        } finally {
            manager.close();
        }
    }

    /**
     * metodo getallartist que obtienen todos los artistas de la base de datos
     *
     * @return List con todos los artistas de las base de datos
     */
    public List<Artista> getAllArtists() {
        try {
            manager.getTransaction().begin();
            String jpql = "FROM Artista";
            TypedQuery<Artista> query = manager.createQuery(jpql, Artista.class);
            List<Artista> artists = query.getResultList();
            manager.getTransaction().commit();
            return artists;
        } catch (Exception e) {
            logger.warning("Failed to get all Artists \n" + e.getMessage());
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            return null;
        } finally {
            manager.close();
        }
    }
//
//    /**
//     * metodo getdiscos que obtiene los discos que tengan el id del artista
//     *
//     * @return list con los discos
//     */
//    public List<Disco> getDiscos() {
//        if (super.getDiscos() == null) {
//            setDiscos(DiscoDAO.getAllDiscosByArtista(this));
//        }
//        return super.getDiscos();
//    }

}
