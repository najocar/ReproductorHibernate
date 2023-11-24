package com.group.reproductorjava.model.interfaces;

import com.group.reproductorjava.model.Entity.Cancion;

public interface ICancionDAO {
    boolean getCancion(int id);
    boolean saveCancion();
    boolean deleteCancion(Cancion cancion);
    void oneReproduction();
}
