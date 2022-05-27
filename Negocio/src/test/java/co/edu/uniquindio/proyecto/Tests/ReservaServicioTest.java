
package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.*;
import co.edu.uniquindio.proyecto.Interfaces.ReservaServicio;
import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.Repositorios.*;
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
public class ReservaServicioTest {

    @Autowired
    ReservaRepo reservaRepo;

    @Autowired
    ReservaServicio reservaServicio;

    @Autowired
    SillaRepo sillaRepo;

    @Autowired
    ClienteRepo clienteRepo;

    @Autowired
    CiudadRepo ciudadRepo;

    @Autowired
    VueloRepo vueloRepo;

    @Autowired
    HabitacionRepo habitacionRepo;

    @Autowired
    HotelRepo hotelRepo;

    @Autowired
    AdministradorHotelRepo administradorHotelRepo;

    Reserva reserva = new Reserva();

    Cliente cliente;

    Ciudad ciudadOrigen = new Ciudad();

    Ciudad ciudadDestino = new Ciudad();

    Vuelo vuelo;

    Hotel hotel;

    AdministradorHotel administradorHotel;

    List<Silla> sillas = new ArrayList<>();

    List<Habitacion> habitacions = new ArrayList<>();

    List<Reserva> reservas = new ArrayList<>();

    void crearReserva() {
        LocalDate fechaInicio = LocalDate.of(2022, Month.APRIL, 26);
        LocalDate fechaFinal = LocalDate.of(2022, Month.APRIL, 30);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFinal(fechaFinal);
        reserva.setCantidadDeClientes(4);
        reserva.setCostoTotal(150000.00);
        reserva.setVuelo(crearVuelo());
        reserva.setSillas(listarSillas());
        reserva.setHabitaciones(listarHabitacion());
        reserva.setCliente(crearCliente());
        reservaRepo.save(reserva);
    }

    List<Silla> listarSillas() {
        sillas.add(new Silla(1, true, crearVuelo(), 120000.0));
        sillas.add(new Silla(2, true, crearVuelo(), 120000.0));
        sillas.add(new Silla(3, true, crearVuelo(), 120000.0));
        sillas.add(new Silla(4, true, crearVuelo(), 120000.0));
        sillaRepo.saveAll(sillas);
        return sillas;
    }

    List<Habitacion> listarHabitacion() {
        habitacions.add(new Habitacion(2, 12000.00, crearHotel()));
        habitacions.add(new Habitacion(3, 12000.00, crearHotel()));
        habitacions.add(new Habitacion(4, 12000.00, crearHotel()));
        habitacions.add(new Habitacion(5, 12000.00, crearHotel()));
        habitacionRepo.saveAll(habitacions);
        return habitacions;
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

    Vuelo crearVuelo() {
        ciudadOrigen.setNombre("Madrid");
        ciudadRepo.save(ciudadOrigen);
        ciudadDestino.setNombre("Barcelona");
        ciudadRepo.save(ciudadDestino);
        LocalDate fechaSalida = LocalDate.of(2022, Month.APRIL, 20);
        vuelo = new Vuelo(ciudadOrigen, ciudadDestino, 60, fechaSalida);
        vueloRepo.save(vuelo);
        return vuelo;
    }

    Cliente crearCliente() {
        ciudadOrigen.setNombre("Madrid");
        ciudadRepo.save(ciudadOrigen);
        ArrayList<String> telefonos = new ArrayList<String>();
        telefonos.add("123456789");
        cliente = new Cliente("1004961210", "Pablo andres", "Pablitoal157@gmail.com", "12345", telefonos, ciudadOrigen);
        clienteRepo.save(cliente);
        return cliente;
    }


    @Test
    public void agregarReservaTest() {
        crearCliente();
        try {
            List<Habitacion> habitaciones = listarHabitacion();
            List<Silla> sillas = listarSillas();
            LocalDate fechaInicio = LocalDate.of(2022, Month.APRIL, 26);
            LocalDate fechaFinal = LocalDate.of(2022, Month.APRIL, 30);
            Reserva reserva = reservaServicio.agregarReserva(fechaInicio, fechaFinal, 4, sillas, habitaciones, crearCliente());
            reservaServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(4, reserva.getCantidadDeClientes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void actualizarReservaTest() {
        crearReserva();
        try {
            List<Habitacion> habitaciones = listarHabitacion();
            List<Silla> sillas = listarSillas();
            LocalDate fechaInicio = LocalDate.of(2022, Month.DECEMBER, 26);
            LocalDate fechaFinal = LocalDate.of(2023, Month.JANUARY, 10);
            Reserva reserva1 = reservaServicio.actualizarReserva(reserva.getCodigo(), fechaInicio, fechaFinal, 4, sillas, habitaciones, crearCliente());
            reservaServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(4, reserva1.getCantidadDeClientes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eliminarReservaTest() {
        crearReserva();
        try {
            reservaServicio.eliminarReserva(reserva.getCodigo());
            reservaServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertNull(reservaRepo.findById(reserva.getCodigo()).orElse(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarReservaPorCodigoTest() {
        crearReserva();
        try {
            System.out.println(reservaServicio.buscarReservaPorCodigo(reserva.getCodigo()));
            Assertions.assertEquals(4, reservaRepo.findById(reserva.getCodigo()).get().getCantidadDeClientes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarTest() {
        crearReserva();
        try {
            reservaServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(reservaRepo.findAll().size(), reservaServicio.listar().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarReservaPorFechaDeInicioTest() {
        crearReserva();
        try {
            LocalDate fechaInicio = LocalDate.of(2022, Month.APRIL, 26);
            reservaServicio.listarReservaPorFechaDeInicio(fechaInicio).forEach(u -> System.out.println(u));
            Assertions.assertEquals(reservaRepo.findAllByFechaInicio(fechaInicio).size(), reservaServicio.listarReservaPorFechaDeInicio(fechaInicio).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarReservaPorFechaDeFinalizacionTest() {
        crearReserva();
        try {
            LocalDate fechaFinal = LocalDate.of(2022, Month.APRIL, 30);
            reservaServicio.listarReservaPorFechaDeFinalizacion(fechaFinal).forEach(u -> System.out.println(u));
            Assertions.assertEquals(reservaRepo.findAllByFechaFinal(fechaFinal).size(), reservaServicio.listarReservaPorFechaDeFinalizacion(fechaFinal).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarReservaPorIdDeClienteTest() {
        crearReserva();
        try {
            reservaServicio.listarReservaPorIdDeCliente(crearCliente().getCedula()).forEach(u -> System.out.println(u));
            Assertions.assertEquals(reservaRepo.findAll().size(), reservaServicio.listarReservaPorIdDeCliente(crearCliente().getCedula()).size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void enviarCorreoConInformacionDeLaReservaTest() {
        crearReserva();
        try {
            reservaServicio.enviarCorreoConInformacionDeLaReserva(crearCliente().getEmail(), reserva);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listarReservaPorHabitacionTest() {
        crearReserva();
        try {
            reservaServicio.listarReservaPorHabitacion(listarHabitacion().get(1)).forEach(u -> System.out.println(u));
            Assertions.assertEquals(reservaRepo.findAll().size(), reservaServicio.listarReservaPorHabitacion(listarHabitacion().get(1)).size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}