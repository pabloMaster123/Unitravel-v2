package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.Caracteristica;
import co.edu.uniquindio.proyecto.Interfaces.CaracteristicaServicio;
import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.Repositorios.CaracteristicaRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = NegocioApplication.class)
@Transactional
public class CaracteristicasServicioTest {

    @Autowired
    CaracteristicaRepo caracteristicaRepo;

    @Autowired
    CaracteristicaServicio caracteristicaServicio;

    Caracteristica caracteristica = new Caracteristica();

    List<Caracteristica> caracteristicas = new ArrayList<>();

    void cearCaracteristica() {
        caracteristica.setContenido("Sala video juegos");
        caracteristicaRepo.save(caracteristica);
    }

    void listarCaracteristicas() {
        caracteristicas.add(new Caracteristica("Buffet"));
        caracteristicas.add(new Caracteristica("Pesca deportiva"));
        caracteristicas.add(new Caracteristica("Mini golf"));
        caracteristicas.add(new Caracteristica("Balcon"));
        caracteristicaRepo.saveAll(caracteristicas);
    }


    @Test
    public void agregarCaracteristicaTest() {
        try {
            caracteristicaServicio.agregarCaracteristica("Video juegos");
            caracteristicaServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals("Video juegos", caracteristicaRepo.findByContenido("Video juegos").get().getContenido());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void actualizarCaracteristicaTest() {
        cearCaracteristica();
        try {
            caracteristicaServicio.actualizarCaracteristica(caracteristica.getCodigo(), "Salon recreativo");
            caracteristicaServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals("Salon recreativo", caracteristicaRepo.findById(caracteristica.getCodigo()).get().getContenido());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eliminarCaracteristicaTest() {
        cearCaracteristica();
        try {
            caracteristicaServicio.eliminarCaracteristica(caracteristica.getCodigo());
            caracteristicaServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertNull(caracteristicaRepo.findById(caracteristica.getCodigo()).orElse(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarCaracteristicaTest() {
        cearCaracteristica();
        try {
            System.out.println(caracteristicaServicio.buscarCaracteristica(caracteristica.getCodigo()));
            Assertions.assertEquals("Sala video juegos", caracteristicaServicio.buscarCaracteristica
                    (caracteristica.getCodigo()).getContenido());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarCaracteristicasPorContenidoTest() {
        cearCaracteristica();
        try {
            System.out.println(caracteristicaServicio.buscarCaracteristicasPorContenido("Sala video juegos"));
            Assertions.assertEquals(1, caracteristicaServicio.buscarCaracteristicasPorContenido("Sala video juegos").size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarTest() {
        cearCaracteristica();
        listarCaracteristicas();
        try {
            caracteristicaServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(caracteristicaRepo.findAll().size(), caracteristicaServicio.listar().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}