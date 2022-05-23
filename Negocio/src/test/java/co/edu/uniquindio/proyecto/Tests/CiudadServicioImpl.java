package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Interfaces.CiudadServicio;
import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.Repositorios.CiudadRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = NegocioApplication.class)
@Transactional
public class CiudadServicioImpl {

    @Autowired
    CiudadRepo ciudadRepo;

    @Autowired
    CiudadServicio ciudadServicio;

    Ciudad ciudad = new Ciudad();

    List<Ciudad> ciudads = new ArrayList<>();

    void crearCiudad() {
        ciudad.setNombre("Madrid");
        ciudadRepo.save(ciudad);
    }

    void listarCiudades() {
        ciudads.add(new Ciudad("New york"));
        ciudads.add(new Ciudad("Los Angeles"));
        ciudads.add(new Ciudad("Las vegas"));
        ciudads.add(new Ciudad("Maruecos"));
        ciudadRepo.saveAll(ciudads);
    }

    @Test
    public void agregarCiudadTest() {
        try {
            ciudadServicio.agregarCiudad("Armenia");
            ciudadServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals("Armenia", ciudadRepo.buscarPorNombre("Armenia").get().getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void actualizarCiudadTest() {
        crearCiudad();
        try {
            ciudadServicio.actualizarCiudad(ciudad.getCodigo(), "Armenia");
            ciudadServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals("Armenia", ciudadRepo.findById(ciudad.getCodigo()).get().getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eliminarCiudadTest() {
        crearCiudad();
        try {
            boolean aux = ciudadServicio.eliminarCiudad(ciudad.getCodigo());
            ciudadServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertNull(ciudadRepo.findById(ciudad.getCodigo()).orElse(null));
            System.out.println(aux);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarCiudadTest() {
        crearCiudad();
        try {
            System.out.println(ciudadServicio.buscarCiudad("Madrid"));
            Assertions.assertEquals("Madrid", ciudadRepo.findById(ciudad.getCodigo()).get().getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarTest() {
        crearCiudad();
        listarCiudades();
        try {
            ciudadServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(ciudadRepo.findAll().size(), ciudadServicio.listar().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}