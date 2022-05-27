package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Silla;
import co.edu.uniquindio.proyecto.Entidades.Vuelo;
import co.edu.uniquindio.proyecto.Interfaces.SillaServicio;
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
public class SillaServicioTest {

    @Autowired
    SillaServicio sillaServicio;

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

    Vuelo crearVuelo() {
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
        return vuelo;
    }

    void crearSilla() {
        silla.setNumero(1);
        silla.setDisponibilidad(true);
        silla.setVuelo(crearVuelo());
        silla.setValor(120000.0);
        sillas.add(silla);
        sillaRepo.save(silla);
    }

    void listarSillas() {
        Vuelo aux = vueloRepo.getById(vuelo.getCodigo());
        sillas.add(new Silla(2, true, aux, 120000.0));
        sillas.add(new Silla(3, true, aux, 120000.0));
        sillas.add(new Silla(4, true, aux, 120000.0));
        sillas.add(new Silla(5, true, aux, 120000.0));
        aux.setSillas(sillas);
        sillaRepo.saveAll(sillas);
        vueloRepo.save(aux);
    }

    @Test
    public void agregarSillaTest() {
        try {
            Vuelo vuelo = crearVuelo();
            Silla silla = sillaServicio.agregarSilla(15000.0, true, vuelo);
            Assertions.assertEquals(15000.0, sillaRepo.findById(silla.getCodigo()).get().getValor());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void verificarExistenciaDeNumero() {
        crearSilla();
        listarSillas();
        try {
            Vuelo vuelo = crearVuelo();
            System.out.println(sillaServicio.verificarExistenciaDeNumero(vuelo, 5));
            Assertions.assertTrue(sillaServicio.verificarExistenciaDeNumero(vuelo, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void listarPorVuelo() {
        crearSilla();
        listarSillas();
        try {
            Vuelo aux = vueloRepo.getById(vuelo.getCodigo());
            sillaServicio.listarPorVuelo(aux).forEach(u -> System.out.println(u));
            Assertions.assertEquals(aux.getSillas().size(), sillaServicio.listarPorVuelo(aux).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}