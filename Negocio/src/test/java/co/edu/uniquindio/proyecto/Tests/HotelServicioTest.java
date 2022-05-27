package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.AdministradorHotel;
import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Hotel;
import co.edu.uniquindio.proyecto.Interfaces.HotelServicio;
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
public class HotelServicioTest {

    @Autowired
    HotelRepo hotelRepo;

    @Autowired
    HotelServicio hotelServicio;

    @Autowired
    CiudadRepo ciudadRepo;

    @Autowired
    AdministradorHotelRepo administradorHotelRepo;

    Hotel hotel;

    AdministradorHotel administradorHotel;

    Ciudad ciudad = new Ciudad();

    List<Hotel> hoteles = new ArrayList<>();

    void crearHotel() {
        ciudad.setNombre("Madrid");
        ciudadRepo.save(ciudad);
        hotel = new Hotel("Maria fernanda", "Cra 12-5S1", 4, ciudad, crearAdministrador());
        hotelRepo.save(hotel);
    }

    AdministradorHotel crearAdministrador() {
        administradorHotel = new AdministradorHotel();
        administradorHotel.setCedula("1004961210");
        administradorHotel.setNombre("Pablo andres");
        administradorHotel.setEmail("Pablitoal123@gmail.com");
        administradorHotel.setPassword("12345");
        administradorHotel.setCodigo(1);
        administradorHotelRepo.save(administradorHotel);
        return administradorHotel;
    }

    void listaHoteles() {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre("Madrid");
        ciudadRepo.save(ciudad);
        hoteles.add(new Hotel("Valentina valencia", "Cra 21-4N1", 3, ciudad, administradorHotel));
        hoteles.add(new Hotel("La loca de jose", "Cra 39-9E1", 5, ciudad, administradorHotel));
        hotelRepo.saveAll(hoteles);
    }

    @Test
    public void agregarHotelTest() {
        crearHotel();
        try {
            hotelServicio.agregarHotel("La santidad de dios", "cra 19-68N55", 5, ciudad, crearAdministrador());
            hotelServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals("La santidad de dios", hotelRepo.findByNombre("La santidad de dios").get().getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void actualizarHotelTest() {
        crearHotel();
        try {
            hotelServicio.actualizarHotel(hotel.getCodigo(), "Maria fernanda", "cra 19-68N55", 4, ciudad, crearAdministrador());
            hotelServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals("cra 19-68N55", hotelRepo.findByNombre("Maria fernanda").get().getDireccion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eliminarHotelTest() {
        crearHotel();
        try {
            hotelServicio.eliminarHotel(hotel.getCodigo());
            hotelServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertNull(hotelRepo.findById(hotel.getCodigo()).orElse(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarHotelPorNombreTest() {
        crearHotel();
        listaHoteles();
        try {
            System.out.println(hotelServicio.buscarHotelPorNombre(hotel.getNombre()));
            Assertions.assertEquals(1, hotelServicio.buscarHotelPorNombre(hotel.getNombre()).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarHotelPorCodigoTest() {
        crearHotel();
        listaHoteles();
        try {
            System.out.println(hotelServicio.buscarHotelPorCodigo(hotel.getCodigo()));
            Assertions.assertEquals("Maria fernanda", hotelServicio.buscarHotelPorCodigo(hotel.getCodigo()).getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarTest() {
        crearHotel();
        listaHoteles();
        try {
            hotelServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(hotelRepo.findAll().size(), hotelServicio.listar().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarHotelPorCiudadTest() {
        crearHotel();
        listaHoteles();
        try {
            hotelServicio.listarHotelPorCiudad("Madrid").forEach(u -> System.out.println(u));
            Assertions.assertEquals(3, hotelServicio.listarHotelPorCiudad("Madrid").size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
