package co.edu.uniquindio.proyecto.Interfaces;

import co.edu.uniquindio.proyecto.Entidades.Habitacion;
import co.edu.uniquindio.proyecto.Entidades.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface HabitacionServicio {

    Habitacion agregarHabitacion(Hotel hotel, Double precio) throws Exception;

    List<Habitacion> listarHabitacionesDisponiblesPorFechas(Hotel hotel, LocalDate entrada, LocalDate salida) throws Exception;

    List<Habitacion> listarHabitaciones();

    List<Habitacion> listarHabitacionesPorHotel(Hotel hotel) throws Exception;

    Habitacion buscarHabitacion(Integer codigo) throws Exception;
}
