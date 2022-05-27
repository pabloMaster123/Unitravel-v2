package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.AdministradorHotel;
import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Habitacion;
import co.edu.uniquindio.proyecto.Entidades.Hotel;
import co.edu.uniquindio.proyecto.Interfaces.CamaServicio;
import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.Repositorios.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static co.edu.uniquindio.proyecto.Entidades.TipoCama.SENCILLA;

@SpringBootTest(classes = NegocioApplication.class)
@Transactional
public class CamaServicioTest {

    @Autowired
    CamaRepo camaRepo;

    @Autowired
    CamaServicio camaServicio;

    @Autowired
    CiudadRepo ciudadRepo;

    @Autowired
    HotelRepo hotelRepo;

    @Autowired
    AdministradorHotelRepo administradorHotelRepo;

    @Autowired
    HabitacionRepo habitacionRepo;

    Habitacion habitacion = new Habitacion();

    Hotel hotel;

    AdministradorHotel administradorHotel;


    void crearHabitacion() {
        habitacion.setNumero(12);
        habitacion.setPrecio(12000.00);
        habitacion.setHotel(crearHotel());
        habitacionRepo.save(habitacion);
    }

    Hotel crearHotel() {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre("Madrid");
        ciudadRepo.save(ciudad);
        hotel = new Hotel("Maria fernanda", "Cra 12-5S1", 4, ciudad, crearAdministrador());
        hotelRepo.save(hotel);
        return hotel;
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

    @Test
    public void agregarCamaTest() {
        crearHabitacion();
        try {
            camaServicio.agregarCama(habitacion, SENCILLA);
            Assertions.assertEquals(habitacion, camaServicio.agregarCama(habitacion, SENCILLA).getHabitacion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}