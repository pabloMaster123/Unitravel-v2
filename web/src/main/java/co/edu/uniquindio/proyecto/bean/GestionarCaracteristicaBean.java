package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Entidades.Caracteristica;
import co.edu.uniquindio.proyecto.Interfaces.CaracteristicaServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GestionarCaracteristicaBean implements Serializable {

    @Autowired
    CaracteristicaServicio caracteristicaServicio;

    private String contenido;

    private String buscar;

    private List<Caracteristica> buscadas;

    private List<Caracteristica> caracteristicas;

    private Caracteristica caracteristica;

    private String caracteristicaActualizada;

    @PostConstruct
    public void inicializar(){
        this.caracteristicas = caracteristicaServicio.listar();
        this.caracteristica = null;
    }

    public String ingresarCaracteristica(){
        try {
            caracteristicaServicio.agregarCaracteristica(contenido);
            return "/administrador/GestionarCaracteristica.xhtml?faces-redirect=true";
        }catch (Exception e){
            e.printStackTrace();
            FacesMessage msg1 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg1);
        }
        return null;
    }

    public String eliminar(Integer id){
        try{
            caracteristicaServicio.eliminarCaracteristica(id);
            return "/administrador/GestionarCaracteristica.xhtml?faces-redirect=true";
        }catch(Exception e){
            e.printStackTrace();
            FacesMessage msg1 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg1);
        }
        return null;
    }

    public void definirCaracteristicaActualizar(Caracteristica caracteristica){
        this.caracteristica = caracteristica;
    }

    public String ActualizarCarateristica() throws Exception {
        try {
            caracteristicaServicio.actualizarCaracteristica(caracteristica.getCodigo(), caracteristicaActualizada);
            return "/administrador/GestionarCaracteristica.xhtml?faces-redirect=true";
        }catch (Exception e){

        }
        return null;
    }

    public void buscarCaracteristica(){
        try{
            caracteristicas = caracteristicaServicio.buscarCaracteristicasPorContenido(buscar);
        }catch (Exception e){
            FacesMessage msg1 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg1);
        }
    }


}
