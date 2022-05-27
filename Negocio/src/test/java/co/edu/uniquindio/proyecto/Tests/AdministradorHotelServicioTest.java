package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.AdministradorHotel;
import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Hotel;
import co.edu.uniquindio.proyecto.Interfaces.AdministradorHotelServicio;
import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.Repositorios.AdministradorHotelRepo;
import co.edu.uniquindio.proyecto.Repositorios.CiudadRepo;
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
public class AdministradorHotelServicioTest {

    @Autowired
    AdministradorHotelRepo administradorHotelRepo;

    @Autowired
    CiudadRepo ciudadRepo;

    @Autowired
    HotelRepo hotelRepo;

    @Autowired
    AdministradorHotelServicio administradorHotelServicio;

    AdministradorHotel administradorHotel = new AdministradorHotel();

    List<Hotel> hoteles = new ArrayList<>();

    List<AdministradorHotel> administradorHotels = new ArrayList<>();

    void crearAdministrador() {
        administradorHotel.setCedula("1004961210");
        administradorHotel.setNombre("Pablo andres");
        administradorHotel.setEmail("Pablitoal123@gmail.com");
        administradorHotel.setPassword("12345");
        administradorHotel.setCodigo(1);
        administradorHotel.setHoteles(listaHoteles());
        administradorHotelRepo.save(administradorHotel);
    }

    List<Hotel> listaHoteles() {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre("Madrid");
        ciudadRepo.save(ciudad);
        hoteles.add(new Hotel("Maria fernanda", "Cra 12-5S1", 4, ciudad, administradorHotel));
        hoteles.add(new Hotel("Valentina valencia", "Cra 21-4N1", 3, ciudad, administradorHotel));
        hoteles.add(new Hotel("La loca de jose", "Cra 39-9E1", 5, ciudad, administradorHotel));
        hotelRepo.saveAll(hoteles);
        return hoteles;
    }

    List<AdministradorHotel> listarAdminsiradores() {
        administradorHotels.add(new AdministradorHotel("1004961230", "Julian",
                "julian12@gmail.com", "54321", 2));
        administradorHotels.add(new AdministradorHotel("1004961240", "Jose",
                "jose@gmail.com", "67890", 3));
        administradorHotels.add(new AdministradorHotel("1004961250", "Natalia",
                "Noatila12@gmail.com", "09876", 4));
        administradorHotelRepo.saveAll(administradorHotels);
        return administradorHotels;
    }

    @Test
    public void loginTest() {
        crearAdministrador();
        try {
            administradorHotelServicio.login(administradorHotel.getEmail(), administradorHotel.getPassword());
            System.out.println("Ingreso exitoso");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void agregarAdministradorDeHotelTest() {
        try {
            administradorHotelServicio.agregarAdministradorDeHotel("0012340921", "Jose Alfredo",
                    "josealfredore200326@gmail.com", "miPassword");
            administradorHotelServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals("Jose Alfredo",
                    administradorHotelRepo.findById("0012340921").get().getNombre());
            System.out.println("Agregación exitosa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void actualizarAdministradorDeHotelTest() {
        crearAdministrador();
        try {
            administradorHotelServicio.actualizarAdministradorDeHotel(administradorHotel.getCedula(),
                    "Pablo lozano", "pablitoal15@gmail.com", "pabl124", 9);
            administradorHotelServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals("Pablo lozano", administradorHotelRepo.findById("1004961210").get().getNombre());
            System.out.println("Actualizacion exitosa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eliminarAdministradorDeHotelPorCedulaTest() {
        crearAdministrador();
        try {
            administradorHotelServicio.eliminarAdministradorDeHotelPorCedula("1004961210");
            administradorHotelServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertNull(administradorHotelRepo.findById("1004961210").orElse(null));
            System.out.println("Eliminacion exitosa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarAdministradorDeHotelPorCedulaTest() {
        crearAdministrador();
        try {
            System.out.println(administradorHotelServicio.buscarAdministradorDeHotelPorCedula(
                    administradorHotel.getCedula()));
            Assertions.assertEquals("Pablo andres",
                    administradorHotelServicio.buscarAdministradorDeHotelPorCedula(
                            administradorHotel.getCedula()).getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarAdministradorDeHotelPorCodigoTest() {
        crearAdministrador();
        try {
            System.out.println(administradorHotelServicio.buscarAdministradorDeHotelPorCodigo(administradorHotel.getCodigo()));
            Assertions.assertEquals("Pablo andres",
                    administradorHotelServicio.buscarAdministradorDeHotelPorCodigo(administradorHotel.getCodigo())
                            .getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarAdministradorDeHotelPorHotel() {
        crearAdministrador();
        try {
            System.out.println(administradorHotelServicio.buscarAdministradorDeHotelPorHotel(hoteles.get(1).getCodigo()));
            Assertions.assertEquals("Pablo andres",
                    administradorHotelRepo.buscarPorCodigoDeHotel(hoteles.get(1).getCodigo()).get().getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listar() {
        crearAdministrador();
        listarAdminsiradores();
        try {
            administradorHotelServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(administradorHotelRepo.findAll().size(), administradorHotelServicio.listar().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
