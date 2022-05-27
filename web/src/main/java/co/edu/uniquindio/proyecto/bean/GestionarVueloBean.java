package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Vuelo;
import co.edu.uniquindio.proyecto.Interfaces.CiudadServicio;
import co.edu.uniquindio.proyecto.Interfaces.VueloServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GestionarVueloBean implements Serializable {

    @Autowired
    private VueloServicio vueloServicio;

    private Integer codigoVuelo;

    private List<Vuelo> vuelos;

    @Autowired
    private CiudadServicio ciudadServicio;

    private List<Ciudad> ciudades;

    private Ciudad ciudadOrigen;

    private Ciudad ciudadDestino;

    private Ciudad ciudadOrigenBuscar;

    private Ciudad ciudadDestinoBuscar;

    private Integer cantidadSillas;

    private LocalDate fecha;

    private Ciudad ciudadOrigenNueva;

    private Ciudad ciudadDestinoNueva;

    private Integer cantidadSillasNueva;

    private LocalDate fechaNueva;

    private Vuelo vueloActualizar;

    @PostConstruct
    public void inicializar() {
        this.vuelos = vueloServicio.listar();
        this.ciudades = ciudadServicio.listar();
        this.vueloActualizar = null;
    }

    public String registrarVuelo(){
        try {
            vueloServicio.agregarVuelo(ciudadOrigen,ciudadDestino,cantidadSillas,fecha);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Alerta", "Registro Exitoso!");
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
            return "/administrador/GestionarVuelo.xhtml?faces-redirect=true";
        }catch (Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }

    public String agregarVuelo() {
        return "/administrador/RegistrarVuelos.xhtml?faces-redirect=true";
    }

    public String eliminarVuelo(Integer codigoVuelo) {
        try {
            vueloServicio.eliminarVuelo(codigoVuelo);
            return "/administrador/GestionarVuelo.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void buscarVuelo() {
        try {
            vuelos.clear();
            Vuelo vuelo = vueloServicio.buscarVueloPorCodigo(codigoVuelo);
            vuelos.add(vuelo);
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
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

    public void definirVueloActualizar(Vuelo vuelo){
        this.vueloActualizar = vuelo;
    }

    public String actualizarVuelo(){
        try {
            vueloServicio.actualizarVuelo(vueloActualizar.getCodigo(), ciudadOrigenNueva, ciudadDestinoNueva, cantidadSillasNueva, fechaNueva);
            this.vueloActualizar = null;
            return "/administrador/GestionarVuelo.xhtml?faces-redirect=true";
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
