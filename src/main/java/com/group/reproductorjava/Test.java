package com.group.reproductorjava;

import com.group.reproductorjava.model.Entity.Author;
import com.group.reproductorjava.model.DAOs.*;
import com.group.reproductorjava.model.Entity.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Test {
    private static EntityManager manager;
    private static EntityManagerFactory emf;
    public static void main(String[] args) throws SQLException {
//        Artista artista = new Artista(-1,"jose","foto","España");
//        Artista artista = new ArtistaDAO(5);
//        artista.setName("josua II");
//        System.out.println(new ArtistaDAO().getAllArtists());

//        System.out.println(new ArtistaDAO(4).deleteArtista());

//        Disco disco = new Disco(-1,"los ecos el rocio", LocalDate.now(),"foto", new ArtistaDAO(5),null);

//        Cancion cancion = new Cancion(-1,"gasolina",120,"reggaetton",0, new Disco(6,"los ecos el rocio", LocalDate.now(),"foto", new ArtistaDAO(5),null));
//        new CancionDAO(cancion).saveCancion();

//        Usuario usuario = new Usuario(-1,"elPepe","a@gmail.com","foto",1);
//        new UsuarioDAO(usuario).saveUsuario();









        emf= Persistence.createEntityManagerFactory("aplicacion");
        manager = emf.createEntityManager();

        Author a1 = new Author(1L, "Carlos Serrano", LocalDate.parse("1980-06-01"));
        Author a2 = new Author(2L, "Miguel de Cervantes", LocalDate.parse("1547-09-22"));

        manager.getTransaction().begin();
        manager.persist(a1);
        manager.persist(a2);
        manager.getTransaction().commit();
        list();
    }

    public static  void list(){
        List<Author> authors = manager.createQuery("FROM Author").getResultList();
        System.out.println("Autores: " + authors.size());
        for(Author a:authors){
            System.out.println(a);
        }
        System.out.println("*************");
    }


//        new ListaDAO(lista).save();


//        Cancion cancion = new CancionDAO(5);
//        cancion.setName("y se prendió");
//        new CancionDAO(cancion).saveCancion();
//        System.out.println(cancion);
//        System.out.println(CancionDAO.getCancionesByList(3));

//        Lista lista = new ListaDAO(3);
//        lista.setName("subnormal");
//        new ListaDAO(lista).save();

        //System.out.println(CancionDAO.getAllCanciones());

}
