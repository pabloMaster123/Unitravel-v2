package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Entidades.AdministradorHotel;
import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Hotel;
import co.edu.uniquindio.proyecto.Interfaces.AdministradorHotelServicio;
import co.edu.uniquindio.proyecto.Interfaces.CiudadServicio;
import co.edu.uniquindio.proyecto.Interfaces.HabitacionServicio;
import co.edu.uniquindio.proyecto.Interfaces.HotelServicio;
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
import java.util.List;

@Component
@ViewScoped
@Getter
@Setter
public class GestionarHotelBean implements Serializable {

    @Autowired
    private HotelServicio hotelServicio;

    @Autowired
    private AdministradorHotelServicio administradorHotelServicio;

    @Autowired
    private CiudadServicio ciudadServicio;

    @Autowired
    private HabitacionServicio habitacionServicio;

    private String nombre;

    private String direccion;

    private Integer numEstrellas;

    private List<Ciudad> ciudades;

    private Ciudad ciudadHotel;

    private Integer codigoActualizar;

    private String nombreActualizar;

    private String direccionActualizar;

    private Integer numEstrellasActualizar;

    private List<Ciudad> ciudadesActualizar;

    private Ciudad ciudadHotelActualizar;

    private AdministradorHotel administradorHotel;

    private List<Hotel> hoteles;

    private String nombreBuscar;

    @Value("#{param['cedula']}")
    private String cedula;

    @PostConstruct
    public void inicializar(){
        try{
            this.ciudades = ciudadServicio.listar();
            this.administradorHotel = administradorHotelServicio.buscarAdministradorDeHotelPorCedula(cedula);
            this.hoteles = administradorHotel.getHoteles();
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public String registrarHotel(){
        try {
            Hotel aux = hotelServicio.agregarHotel(nombre, direccion,numEstrellas,ciudadHotel,administradorHotel);
            agregarHabitaciones(aux);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"alerta", "registro exitoso");
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
            return "/administradorHotel/GestionarHotel.xhtml?faces-redirect=true&amp;cedula="+cedula;
        }catch (Exception e){
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }

    public void agregarHabitaciones(Hotel aux){
        for(int i = 0; i < 100; i++){
            try{
                habitacionServicio.agregarHabitacion(aux, 250000.00);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void setearCodigoActualizar(Integer codigo){
        this.codigoActualizar = codigo;
    }

    public String actualizarHotel(){
        try {
            hotelServicio.actualizarHotel(codigoActualizar, nombreActualizar, direccionActualizar,numEstrellasActualizar,ciudadHotelActualizar,administradorHotel);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"alerta", "Actualizacion exitosa");
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
            return "/administradorHotel/GestionarHotel.xhtml?faces-redirect=true&amp;cedula="+cedula;
        }catch (Exception e){
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }

    public String eliminarHotel(Integer codigo){
        try {
            hotelServicio.eliminarHotel(codigo);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"alerta", "Actualizacion exitosa");
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
            return "/administradorHotel/GestionarHotel.xhtml?faces-redirect=true&amp;cedula="+cedula;
        }catch (Exception e){
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }

    public void buscarHotel(){
        try{
            hoteles = hotelServicio.buscarHotelPorNombre(nombreBuscar);
            nombreBuscar = null;
        }catch (Exception e){
            FacesMessage msg1 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg1);
        }
    }


}
