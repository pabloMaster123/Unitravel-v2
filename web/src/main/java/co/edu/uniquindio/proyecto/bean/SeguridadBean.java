package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Entidades.Administrador;
import co.edu.uniquindio.proyecto.Entidades.AdministradorHotel;
import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Cliente;
import co.edu.uniquindio.proyecto.Interfaces.AdministradorHotelServicio;
import co.edu.uniquindio.proyecto.Interfaces.AdministradorServicio;
import co.edu.uniquindio.proyecto.Interfaces.ClienteServicio;
import co.edu.uniquindio.proyecto.Interfaces.PersonaServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;

@Component
@Scope("session")
public class SeguridadBean implements Serializable {

    @Autowired
    private AdministradorServicio administradorServicio;

    @Autowired
    private AdministradorHotelServicio administradorHotelServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private PersonaServicio personaServicio;

    @Getter @Setter
    private String email, password;

    @Getter @Setter
    private boolean autenticado, autenticadoAdmin, autenticadoAdminHotel, autenticadoCliente;

    @Getter @Setter
    private Administrador administrador;

    @Getter @Setter
    private AdministradorHotel administradorHotel;

    @Getter @Setter
    private Cliente cliente;

    @PostConstruct
    public void inicializar() {
        autenticado = false;
        autenticadoAdmin = false;
        autenticadoAdminHotel = false;
        autenticadoCliente = false;

        administrador = null;
        administradorHotel = null;
        cliente = null;
    }

    public String iniciarSesion(){
        try{
            if(!email.isEmpty() && !password.isEmpty()){
                int tipo = personaServicio.verificarTipoDeUsuario(email);
                iniciarUsuario(tipo);
                return "index?faces-redirect=true";
            }
        }catch(Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }

    public void iniciarUsuario(int tipo){
        try{
            switch(tipo){
                case 1:
                    administrador = administradorServicio.login(email, password);
                    autenticadoAdmin = true;
                    break;
                case 2:
                    administradorHotel = administradorHotelServicio.login(email, password);
                    autenticadoAdminHotel = true;
                    break;
                case 3:
                    cliente = clienteServicio.login(email, password);
                    autenticadoCliente = true;
                    break;
            }
            autenticado = true;
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public String cerrarSesion() {
        autenticado = false;
        autenticadoAdmin = false;
        autenticadoAdminHotel = false;
        autenticadoAdminHotel = false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }

    public String redireccionarParaGestionarCuenta(String cedula) {
        return "/cliente/GestionarCuenta.xhtml?faces-redirect=true&amp;cedula="+cedula;
    }

    public String redireccionarParaGestionarHoteles(String cedula) {
        return "/administradorHotel/GestionarHotel.xhtml?faces-redirect=true&amp;cedula="+cedula;
    }

}
