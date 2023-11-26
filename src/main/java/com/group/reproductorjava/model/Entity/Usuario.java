package com.group.reproductorjava.model.Entity;

import com.group.reproductorjava.model.DAOs.UsuarioDAO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name="USER")
public class Usuario implements Serializable {

    private final long serialVersionUID = 1L;

    @Id
    @Column(name="ID")
    int id;

    @Column(name="NOMBRE")
    String name;

    @Column(name="EMAIL")
    String email;

    @Column(name="FOTO")
    String photo;

    @Column(name="ROL")
    int rol;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Comentario> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "userCreator", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Lista> playlists = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "SUSCRIPCIONES",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_LISTA"))
    List<Lista> subscriptionList = new ArrayList<>();

    public Usuario() {
        this(-1, "", "", "", 1);
    }

    public Usuario(int id, String name, String email, String photo, int rol) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.rol = rol;
    }

    public Usuario(int id){
        this(id, "", "", "", 1);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public List<Comentario> getCommentList() {
        return this.commentList;
    }

    public void setCommentList(List<Comentario> commentList) {
        this.commentList = commentList;
    }

    public List<Lista> getPlaylists() {
        if(playlists == null){
            List<Lista> aux = new UsuarioDAO(this).getLista();
            if(aux != null) playlists = aux;
        }
        return playlists;
    }

    public void setPlaylists(List<Lista> playlists) {
        this.playlists = playlists;
    }

    public List<Lista> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<Lista> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", rol=" + rol +
                '}';
    }
}
