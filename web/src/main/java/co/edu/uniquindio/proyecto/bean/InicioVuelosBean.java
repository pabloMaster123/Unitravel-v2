package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Vuelo;
import co.edu.uniquindio.proyecto.Interfaces.CiudadServicio;
import co.edu.uniquindio.proyecto.Interfaces.VueloServicio;
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
import java.util.List;

@Component
@ViewScoped
@Getter
@Setter
public class InicioVuelosBean implements Serializable {

    @Autowired
    private VueloServicio vueloServicio;

    private Integer codigoVuelo;

    private List<Vuelo> vuelos;

    @Autowired
    private CiudadServicio ciudadServicio;

    private List<Ciudad> ciudades;

    private Ciudad ciudadOrigenBuscar;

    private Ciudad ciudadDestinoBuscar;

    private LocalDate fechaBuscar;

    @Value("#{param['seleccionar']}")
    private String seleccionar;

    @PostConstruct
    void inicializar(){
        this.vuelos = vueloServicio.listar();
        this.ciudades = ciudadServicio.listar();
    }

    public void buscarVueloCiuddaOrigen() {
        try {
            vuelos.clear();
            vuelos = vueloServicio.buscarVueloPorOrigen(ciudadOrigenBuscar.getNombre());
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
    }

    public void buscarVueloCiudadDestino() {
        try {
            vuelos.clear();
            vuelos = vueloServicio.buscarVueloPorDestino(ciudadDestinoBuscar.getNombre());
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
    }

    public void buscarVueloFecha() {
        try {
            vuelos.clear();
            vuelos = vueloServicio.buscarVueloPorFecha(fechaBuscar);
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
    }

}
