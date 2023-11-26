package com.group.reproductorjava.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Manager {

    private static EntityManager manager;
    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("aplicacion");
        manager = emf.createEntityManager();
    }

    public static EntityManager getEntityManager() {
        return manager;
    }

    public static void closeEntityManager() {
        manager.close();
        emf.close();
    }

}
