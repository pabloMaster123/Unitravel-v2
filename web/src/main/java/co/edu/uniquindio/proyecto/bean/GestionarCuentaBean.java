package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Cliente;
import co.edu.uniquindio.proyecto.Interfaces.CiudadServicio;
import co.edu.uniquindio.proyecto.Interfaces.ClienteServicio;
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
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
public class GestionarCuentaBean implements Serializable {

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private CiudadServicio ciudadServicio;

    @Getter @Setter
    private Cliente cliente;

    @Getter @Setter
    private List<Cliente> clientes;

    @Value("#{param['cedula']}")
    private String cedula;

    @Getter @Setter
    private String nombreNuevo, emailNuevo, passwordNueva;

    @Getter @Setter
    private Ciudad ciudadNueva;

    @Getter @Setter
    private List<Ciudad> ciudades;

    @Getter @Setter
    private List<String> telefonos;

    @PostConstruct
    public void inicializar(){
        try{
            ciudades = ciudadServicio.listar();
            cliente = clienteServicio.buscarClientePorCedula(cedula);
            telefonos = cliente.getTelefono();
        }catch(Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
    }

    public String actualizar(){
        try{
            if(!nombreNuevo.isEmpty() && !emailNuevo.isEmpty() && !passwordNueva.isEmpty() && ciudadNueva != null){
                clienteServicio.actualizarCliente(cedula, nombreNuevo, emailNuevo, passwordNueva, ciudadNueva, cliente.getTelefono());
                return "/cliente/GestionarCuenta.xhtml?faces-redirect=true&amp;cedula="+cedula;
            }
        }catch(Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }

    public String eliminarTelefono(String numero){
        try{
            System.out.println(numero);
            this.telefonos.remove(numero);
            clienteServicio.actualizarTelefonos(cedula, telefonos);
            return "/cliente/GestionarCuenta.xhtml?faces-redirect=true&amp;cedula="+cedula;
        }catch(Exception e){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }

}
