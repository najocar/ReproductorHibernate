package com.group.reproductorjava.model.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "Lista")
public class Lista implements Serializable {
    private static final long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private int id;
    @Column (name = "name")
    private String name;
    @Column (name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AUTHOR")
    protected Usuario userCreator;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "lista_cancion",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "cancion_id")
    )
    protected List<Cancion> canciones = new ArrayList<>();

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected List<Comentario> comentarios = new ArrayList<>();

    @ManyToMany(mappedBy = "subscriptionList", cascade = {CascadeType.ALL})
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected List<Usuario> usersSucribtions = new ArrayList<>();

    public Lista(int id, String name, Usuario userCreator, String description) {
        this.id = id;
        this.name = name;
        this.userCreator = userCreator;
        this.description = description;
    }

    public Lista(int id) {
        this(id, "", null, "");
    }

    public Lista() {
        this(-1, "", null,"");
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Usuario getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(Usuario userCreator) {
        this.userCreator = userCreator;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Usuario> getUsersSucribtions() {
        return usersSucribtions;
    }

    public void setUsersSucribtions(List<Usuario> usersSucribtions) {
        this.usersSucribtions = usersSucribtions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lista lista = (Lista) o;
        return id == lista.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Lista{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userCreator=" + userCreator.getName() +
                ", canciones=" + canciones +
                ", comentarios=" + comentarios +
                '}';
    }
}
