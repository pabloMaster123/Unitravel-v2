package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Entidades.Habitacion;
import co.edu.uniquindio.proyecto.Entidades.Hotel;
import co.edu.uniquindio.proyecto.Entidades.Vuelo;
import co.edu.uniquindio.proyecto.Interfaces.HabitacionServicio;
import co.edu.uniquindio.proyecto.Interfaces.HotelServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
@Getter
@Setter
public class DetalleHotelBean implements Serializable {

    @Autowired
    private HotelServicio hotelServicio;

    @Autowired
    private HabitacionServicio habitacionServicio;

    private List<Habitacion> habitaciones;

    @Value("#{param['codigoHotel']}")
    private String codigoHotel;

    @Value("#{param['seleccionar']}")
    private String seleccionar;

    @Value("#{param['fechaEntrada']}")
    private String fechaEntrada;

    @Value("#{param['fechaSalida']}")
    private String fechaSalida;

    private Hotel hotel;

    private boolean seleccionarBoolean;


    @PostConstruct
    public void inicializar(){
        seleccionarBoolean = false;
        try{
            this.hotel = hotelServicio.buscarHotelPorCodigo(Integer.parseInt(codigoHotel));
            if(seleccionar.equals("true")){
                habitaciones = habitacionServicio.listarHabitacionesDisponiblesPorFechas(hotel, LocalDate.parse(fechaEntrada), LocalDate.parse(fechaSalida));
                seleccionarBoolean = true;
            } else {
                habitaciones = habitacionServicio.listarHabitacionesPorHotel(hotel);
            }
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public String redireccionarParaReservar(boolean vuelo){
        if(vuelo){
            return "/cliente/InicioVuelos.xhtml?faces-redirect=true&amp;seleccionar=true";
        } else {
            return "/cliente/RegistrarReserva.xhtml?faces-redirect=true";
        }
    }

}
