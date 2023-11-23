package com.group.reproductorjava.model.interfaces;

public interface ICancionDAO {
    boolean getCancion(int id);
    boolean getCancion(String name);
    boolean saveCancion();
    boolean deleteCancion();
    void oneReproduction();
}
