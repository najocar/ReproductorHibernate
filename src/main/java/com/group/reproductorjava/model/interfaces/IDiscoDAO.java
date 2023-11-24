package com.group.reproductorjava.model.interfaces;

import com.group.reproductorjava.model.Entity.Disco;

import java.util.List;

public interface IDiscoDAO {
    boolean getDisco(int id);
    boolean getDisco(String name);
    List<Disco> getAllDiscos();
    boolean saveDisco();
    boolean deleteDisco();
}
