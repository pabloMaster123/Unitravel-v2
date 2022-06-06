package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Entidades.Cliente;
import co.edu.uniquindio.proyecto.Entidades.Habitacion;
import co.edu.uniquindio.proyecto.Entidades.Silla;
import co.edu.uniquindio.proyecto.Entidades.Vuelo;
import co.edu.uniquindio.proyecto.Interfaces.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
@Getter
@Setter
public class ReservaBean implements Serializable {

    @Autowired
    private ReservaServicio reservaServicio;

    @Autowired
    private HabitacionServicio habitacionServicio;

    @Autowired
    private SillaServicio sillaServicio;

    @Autowired
    private VueloServicio vueloServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Value(value = "#{seguridadBean.fechaSalida}")
    private LocalDate fechaFinal;

    private Integer cantidadClientes;

    @Value(value = "#{seguridadBean.sillasSeleccionadas}")
    private List<Silla> sillas;

    @Value(value = "#{seguridadBean.habitacionesSeleccionadas}")
    private List<Habitacion> habitaciones;

    @Value(value = "#{seguridadBean.cliente}")
    private Cliente cliente;

    private List<Vuelo> vuelos;

    private Vuelo vuelo;

    @Value(value = "#{seguridadBean.fechaEntrada}")
    private LocalDate fechaInicio;

    public String agregarReserva(){
        try {
            if(cliente!=null) {
                reservaServicio.agregarReserva(fechaInicio, fechaFinal, cantidadClientes, sillas, habitaciones, cliente);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "Registro Exitoso!");
                FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
                return "/cliente/RegistrarReserva.xhtml?faces-redirect=true";
            }
        }catch (Exception e){
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }


}
