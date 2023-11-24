package com.group.reproductorjava.model.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "DISCO")
public class Disco implements Serializable {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA")
    private LocalDate fecha;
    @Column(name = "PHOTO")
    private String photo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ARTISTA")
    private Artista artista;
    @OneToMany(mappedBy = "disco", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cancion> canciones;

    public Disco() {
        this(-1, "", null, "", null);
    }

    public Disco(int id, String nombre, LocalDate fecha, String photo, Artista artista) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.photo = photo;
        this.artista = artista;
    }

    public Disco(int id) {
        this(id, "", null, "", null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return nombre;
    }

    public void setName(String name) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    @Override
    public String toString() {
        return "Disco{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", date=" + fecha +
                ", photo='" + photo + '\'' +
                ", artista=" + artista +
                ", canciones=" + canciones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disco disco = (Disco) o;
        return id == disco.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

