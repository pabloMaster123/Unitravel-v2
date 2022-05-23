package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.AdministradorHotel;
import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Comentario;
import co.edu.uniquindio.proyecto.Entidades.Hotel;
import co.edu.uniquindio.proyecto.Interfaces.ComentarioServicio;
import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.Repositorios.AdministradorHotelRepo;
import co.edu.uniquindio.proyecto.Repositorios.CiudadRepo;
import co.edu.uniquindio.proyecto.Repositorios.ComentarioRepo;
import co.edu.uniquindio.proyecto.Repositorios.HotelRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = NegocioApplication.class)
@Transactional
public class ComentarioServicioTest {

    @Autowired
    ComentarioRepo comentarioRepo;

    @Autowired
    ComentarioServicio comentarioServicio;

    @Autowired
    HotelRepo hotelRepo;

    @Autowired
    CiudadRepo ciudadRepo;

    @Autowired
    AdministradorHotelRepo administradorHotelRepo;

    Comentario comentario = new Comentario();

    Hotel hotel;

    AdministradorHotel administradorHotel = new AdministradorHotel();

    List<Comentario> comentarios = new ArrayList<>();

    void crearComentario() {
        comentario.setComentario("1004961210");
        comentario.setCalificacion(2);
        comentario.setHotel(crearHotel());
        comentarioRepo.save(comentario);
    }

    Hotel crearHotel() {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre("Madrid");
        ciudadRepo.save(ciudad);
        hotel = new Hotel("Maria fernanda", "Cra 12-5S1", 4, ciudad, crearAdministrador());
        hotelRepo.save(hotel);
        return hotel;
    }

    void listarComentarios() {
        comentarios.add(new Comentario(5, "Bueno Hotel", crearHotel()));
        comentarios.add(new Comentario(1, "Pero esto que es pero esto que es", crearHotel()));
        comentarios.add(new Comentario(3, "Creo que deje mis llaves", crearHotel()));
        comentarioRepo.saveAll(comentarios);
    }

    AdministradorHotel crearAdministrador() {
        administradorHotel.setCedula("1004961210");
        administradorHotel.setNombre("Pablo andres");
        administradorHotel.setEmail("Pablitoal123@gmail.com");
        administradorHotel.setPassword("12345");
        administradorHotel.setCodigo(1);
        administradorHotelRepo.save(administradorHotel);
        return administradorHotel;
    }

    @Test
    public void agregarComentarioTest() {
        crearHotel();
        try {
            comentario = comentarioServicio.agregarComentario(hotel.getCodigo(), "Buen hotel 5 estrelas", 5);
            comentarioServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals("Buen hotel 5 estrelas", comentario.getComentario());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void actualizarComentarioTest() {
        crearComentario();
        try {
            comentarioServicio.actualizarComentario(comentario.getCodigo(), "Buen hotel 5 estrelas", 5);
            comentarioServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals("Buen hotel 5 estrelas", comentario.getComentario());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eliminarComentarioTest() {
        crearComentario();
        try {
            comentarioServicio.eliminarComentario(comentario.getCodigo());
            comentarioServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertNull(comentarioRepo.findById(comentario.getCodigo()).orElse(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarComentarioPorCodigoTest() {
        crearComentario();
        listarComentarios();
        try {
            System.out.println(comentarioServicio.buscarComentarioPorCodigo(comentario.getCodigo()));
            Assertions.assertEquals("1004961210", comentarioServicio.buscarComentarioPorCodigo(comentario.getCodigo()).getComentario());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarComentarioPorCalificacionTest() {
        crearComentario();
        listarComentarios();
        try {
            System.out.println(comentarioServicio.buscarComentarioPorCalificacion(5));
            Assertions.assertEquals(1, comentarioServicio.buscarComentarioPorCalificacion(5).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarTest() {
        crearComentario();
        listarComentarios();
        try {
            comentarioServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(comentarioRepo.findAll().size(), comentarioServicio.listar().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}