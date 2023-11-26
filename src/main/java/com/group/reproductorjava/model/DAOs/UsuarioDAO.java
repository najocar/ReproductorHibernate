package com.group.reproductorjava.model.DAOs;

import com.group.reproductorjava.model.Entity.Lista;
import com.group.reproductorjava.model.Entity.Usuario;
import com.group.reproductorjava.model.interfaces.IUsuarioDAO;
import com.group.reproductorjava.utils.LoggerClass;
import com.group.reproductorjava.utils.Manager;

import javax.persistence.EntityManager;
import java.util.List;

public class UsuarioDAO extends Usuario implements IUsuarioDAO {

    static LoggerClass logger = new LoggerClass(UsuarioDAO.class.getName());
    private static EntityManager manager = Manager.getEntityManager();


    public UsuarioDAO(int id){
        getUsuario(id);
    }

    public UsuarioDAO(int id, String nombre, String email, String photo, int rol){
        super(id, nombre, email, photo, rol);
    }

    public UsuarioDAO(Usuario user){
        super(user.getId(), user.getName(), user.getEmail(), user.getPhoto(), user.getRol());
    }

    /**
     * Method to get User by id to user
     * @param id: int
     * @return boolean
     * true if success
     */
    @Override
    public boolean getUsuario(int id) {
        boolean result = false;
        Usuario user = manager.find(Usuario.class, id);
        if(user != null) {
            setId(user.getId());
            setName(user.getName());
            setEmail(user.getEmail());
            setPhoto(user.getPhoto());
            setRol(user.getRol());
            result = true;
        }
        return result;
    }

    /**
     * Static method to get all Users
     * @return List<Usuario> | null
     * if dont return null, success
     */
    public static List<Usuario> getAllUsuarios() {
        return manager.createQuery("FROM Usuario").getResultList();
    }

    /**
     * Method to save a user
     * @return boolean
     * If return true success
     * If User exist, update User
     */
    @Override
    public boolean saveUsuario() {
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

    /**
     * Method to remove User
     * @return boolean
     * If return true, success
     */
    @Override
    public boolean deleteUsuario() {
        Usuario aux = this;
        try {
            manager.getTransaction().begin();
            if(!manager.contains(this)) {
                aux = manager.find(Usuario.class, this);
            }
            manager.remove(aux);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if(manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            logger.warning("Failed to remove \n" + e.getMessage());
        } finally {
            manager.close();
        }
        return true;
    }

    /**
     * Method to get all Lista of a owner user
     * @return List<Lista> | null
     * If dont return null, success
     */
    public List<Lista> getLista(){
        return this.getPlaylists();
    }
}
