package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.AdministradorHotel;
import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Habitacion;
import co.edu.uniquindio.proyecto.Entidades.Hotel;
import co.edu.uniquindio.proyecto.Interfaces.HabitacionServicio;
import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.Repositorios.AdministradorHotelRepo;
import co.edu.uniquindio.proyecto.Repositorios.CiudadRepo;
import co.edu.uniquindio.proyecto.Repositorios.HabitacionRepo;
import co.edu.uniquindio.proyecto.Repositorios.HotelRepo;
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
public class HabitacionServicioTest {

    @Autowired
    HabitacionRepo habitacionRepo;

    @Autowired
    HabitacionServicio habitacionServicio;

    @Autowired
    HotelRepo hotelRepo;

    @Autowired
    AdministradorHotelRepo administradorHotelRepo;

    @Autowired
    CiudadRepo ciudadRepo;

    Habitacion habitacion = new Habitacion();

    Hotel hotel;

    AdministradorHotel administradorHotel;

    List<Habitacion> habitacions = new ArrayList<>();

    void crearHabitacion() {
        habitacion.setNumero(1);
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

    void listarHabitaciones() {
        habitacions.add(new Habitacion(2, 12000.00, crearHotel()));
        habitacions.add(new Habitacion(3, 12000.00, crearHotel()));
        habitacions.add(new Habitacion(4, 12000.00, crearHotel()));
        habitacions.add(new Habitacion(5, 12000.00, crearHotel()));
        habitacionRepo.saveAll(habitacions);
    }

    @Test
    public void agregarHabitacionTest() {
        crearHabitacion();
        try {
            Habitacion habitacion = habitacionServicio.agregarHabitacion(hotel, 150000.0);
            Assertions.assertEquals(hotel.getNombre(), habitacion.getHotel().getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarHabitacionesPorFechasTest() {
        crearHabitacion();
        listarHabitaciones();
        try {
            LocalDate fechaInicio = LocalDate.of(2022, Month.DECEMBER, 26);
            LocalDate fechaFinal = LocalDate.of(2023, Month.JANUARY, 10);
            habitacionServicio.listarHabitacionesDisponiblesPorFechas(fechaInicio, fechaFinal).forEach(u -> System.out.println(u));
            Assertions.assertEquals(5, habitacionServicio.listarHabitacionesDisponiblesPorFechas(fechaInicio, fechaFinal).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarHabitacionesTest() {
        crearHabitacion();
        listarHabitaciones();
        try {
            habitacionServicio.listarHabitaciones().forEach(u -> System.out.println(u));
            Assertions.assertEquals(habitacionRepo.findAll().size(), habitacionServicio.listarHabitaciones().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}