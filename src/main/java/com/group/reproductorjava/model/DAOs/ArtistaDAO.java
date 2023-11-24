package com.group.reproductorjava.model.DAOs;

import com.group.reproductorjava.model.Entity.Artista;
import com.group.reproductorjava.model.Entity.Disco;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ArtistaDAO {

    private static final EntityManager EM = Manager.getEntityManager();

    public boolean saveArtista(Artista artista) {
        Transaction transaction = null;
        try {
            transaction = EM.getTransaction();
            transaction.begin();
            EM.persist(artista);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateArtista(Artista artista) {
        Transaction transaction = null;
        try {
            transaction = EM.getTransaction();
            transaction.begin();
            EM.merge(artista);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteArtista(int id) {
        Transaction transaction = null;
        try {
            transaction = EM.getTransaction();
            transaction.begin();
            Artista artista = EM.find(Artista.class, id);
            EM.remove(artista);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public Artista getArtista(int id) {
        return EM.find(Artista.class, id);
    }

    public Artista getArtistaByName(String name) {
        Query query = EM.createQuery("SELECT a FROM Artista a WHERE a.name = :name");
        query.setParameter("name", name);
        List<Artista> artistas = query.getResultList();
        if (!artistas.isEmpty()) {
            return artistas.get(0);
        } else {
            return null;
        }
    }

    public List<Artista> getAllArtistas() {
        Query query = EM.createQuery("SELECT a FROM Artista a");
        return query.getResultList();
    }

    public List<Disco> getDiscos(int id) {
        Artista artista = getArtista(id);
        if (artista != null) {
            return artista.getDiscos();
        } else {
            return null;
        }
    }
}
