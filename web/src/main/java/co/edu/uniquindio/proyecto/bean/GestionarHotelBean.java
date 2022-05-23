package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Entidades.AdministradorHotel;
import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Interfaces.AdministradorHotelServicio;
import co.edu.uniquindio.proyecto.Interfaces.CiudadServicio;
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

    private String nombre;

    private String direcion;

    private Integer numEstrellas;

    private List<Ciudad> ciudades;

    private Ciudad ciudadHotel;

    private AdministradorHotel administradorHotel;

    @Value("#{param['cedula']}")
    private String cedula;

    @PostConstruct
    public void inicializar(){
        try{
            this.ciudades = ciudadServicio.listar();
            this.administradorHotel = administradorHotelServicio.buscarAdministradorDeHotelPorCedula(cedula);
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public String registrarHotel(){
        try {
            hotelServicio.agregarHotel(nombre, direcion,numEstrellas,ciudadHotel,administradorHotel);
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


}
