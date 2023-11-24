package com.group.reproductorjava.model.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Artista")
public class Artista implements Serializable {
    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//autoincrement
    @Column(name = "id")
    private int  id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "nacionalidad")
     private String nacionality;
    @Column(name = "foto")
    private String photo;

    @OneToMany(mappedBy = "artista",cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Disco> discos=new ArrayList<>();

    public Artista(int id, String name, String nacionality, String photo) {
        this.id = id;
        this.name = name;
        this.nacionality = nacionality;
        this.photo = photo;
    }

    public Artista(){ }

    public Artista(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNacionality() {
        return nacionality;
    }

    public void setNacionality(String nacionality) {
        this.nacionality = nacionality;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Disco> getDiscos() {
        return discos;
    }

    public void setDiscos(List<Disco> discos) {
        this.discos = discos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artista artista = (Artista) o;
        return id == artista.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Artista{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", nacionality='" + nacionality + '\'' +
                ", discos=" + discos +
                '}';
    }
}
