package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Silla;
import co.edu.uniquindio.proyecto.Entidades.Vuelo;
import co.edu.uniquindio.proyecto.Interfaces.VueloServicio;
import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.Repositorios.CiudadRepo;
import co.edu.uniquindio.proyecto.Repositorios.SillaRepo;
import co.edu.uniquindio.proyecto.Repositorios.VueloRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = NegocioApplication.class)
@Transactional
public class VueloServicioTest {

    @Autowired
    VueloServicio vueloServicio;

    @Autowired
    SillaRepo sillaRepo;

    @Autowired
    VueloRepo vueloRepo;

    @Autowired
    CiudadRepo ciudadRepo;

    Silla silla = new Silla();

    Ciudad ciudadOrigen = new Ciudad();

    Ciudad ciudadDestino = new Ciudad();

    Vuelo vuelo = new Vuelo();

    List<Silla> sillas = new ArrayList<>();

    void crearVuelo() {
        ciudadOrigen.setNombre("Madrid");
        ciudadRepo.save(ciudadOrigen);
        ciudadDestino.setNombre("Barcelona");
        ciudadRepo.save(ciudadDestino);
        LocalDate fechaSalida = LocalDate.of(2022, Month.APRIL, 20);
        vuelo.setOrigen(ciudadOrigen);
        vuelo.setDestino(ciudadDestino);
        vuelo.setFecha(fechaSalida);
        vuelo.setCantidadDeSillas(10);
        vuelo.setSillas(new ArrayList<Silla>());
        vueloRepo.save(vuelo);
    }

    @Test
    public void agregarVueloTest() {
        crearVuelo();
        try {
            Vuelo aux = vueloServicio.agregarVuelo(ciudadOrigen, ciudadDestino, 30, LocalDate.now());
            vueloServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(30, aux.getCantidadDeSillas());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void actualizarVueloTest() {
        crearVuelo();
        try {
            vueloServicio.actualizarVuelo(vuelo.getCodigo(), ciudadOrigen, ciudadDestino, 21, LocalDate.now());
            vueloServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(21, vueloRepo.findById(vuelo.getCodigo()).get().getCantidadDeSillas());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eliminarVueloTest() {
        crearVuelo();
        try {
            System.out.println(vueloServicio.eliminarVuelo(vuelo.getCodigo()));
            vueloServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertNull(vueloRepo.findById(vuelo.getCodigo()).orElse(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarVueloPorCodigoTest() {
        crearVuelo();
        try {
            System.out.println(vueloServicio.buscarVueloPorCodigo(vuelo.getCodigo()));
            Assertions.assertEquals(vueloRepo.findById(vuelo.getCodigo()).get(), vueloServicio.buscarVueloPorCodigo(vuelo.getCodigo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarVueloPorOrigenTest() {
        crearVuelo();
        try {
            System.out.println(vueloServicio.buscarVueloPorOrigen(ciudadOrigen.getNombre()));
            Assertions.assertEquals(vueloRepo.buscarPorNombreDeCiudadDeOrigen(ciudadOrigen.getNombre()).size(), vueloServicio.buscarVueloPorOrigen(ciudadOrigen.getNombre()).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarVueloPorDestinoTest() {
        crearVuelo();
        try {
            System.out.println(vueloServicio.buscarVueloPorDestino(ciudadDestino.getNombre()));
            Assertions.assertEquals(vueloRepo.buscarPorNombreDeCiudadDeDestino(ciudadDestino.getNombre()).size(), vueloServicio.buscarVueloPorDestino(ciudadDestino.getNombre()).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarVueloPorFechaTest() {
        crearVuelo();
        try {
            LocalDate fecha = LocalDate.of(2022, Month.APRIL, 20);
            System.out.println(vueloServicio.buscarVueloPorFecha(fecha));
            Assertions.assertEquals(vueloRepo.findByFecha(fecha).size(), vueloServicio.buscarVueloPorFecha(fecha).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarTest() {
        crearVuelo();
        try {
            vueloServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(vueloRepo.findAll().size(), vueloServicio.listar().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
