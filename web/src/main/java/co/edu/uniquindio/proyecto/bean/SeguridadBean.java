package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Entidades.*;
import co.edu.uniquindio.proyecto.Interfaces.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
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

    @Autowired
    private SillaServicio sillaServicio;

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

    //DETALLE BEAN

    @Autowired
    private HabitacionServicio habitacionServicio;

    @Getter @Setter
    private LocalDate fechaEntrada;

    @Getter @Setter
    private LocalDate fechaSalida;

    @Getter @Setter
    private List<Habitacion> habitacionesSeleccionadas;

    @Getter @Setter
    private List<Silla> sillasSeleccionadas;

    //VUELO BEAN
    @Getter @Setter
    private Vuelo vuelo;

    @Getter @Setter
    private String cantidadSillas;

    @PostConstruct
    public void inicializar() {
        autenticado = false;
        autenticadoAdmin = false;
        autenticadoAdminHotel = false;
        autenticadoCliente = false;

        administrador = null;
        administradorHotel = null;
        cliente = null;

        vuelo = null;

        habitacionesSeleccionadas = new ArrayList<Habitacion>();
        sillasSeleccionadas = new ArrayList<Silla>();

        fechaEntrada = null;
        fechaSalida = null;
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
        autenticadoCliente = false;
        autenticadoAdminHotel = false;
        administrador = null;
        administradorHotel = null;
        cliente = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }

    public String redireccionarParaGestionarCuenta(String cedula) {
        return "/cliente/GestionarCuenta.xhtml?faces-redirect=true&amp;cedula="+cedula;
    }

    public String redireccionarParaGestionarHoteles(String cedula) {
        return "/administradorHotel/GestionarHotel.xhtml?faces-redirect=true&amp;cedula="+cedula;
    }

    public String redireccionarParaVerHoteles() {
        if(autenticadoCliente){
            if(fechaEntrada!= null && fechaSalida != null) {
                return "/InicioHoteles.xhtml?faces-redirect=true&amp;cedula="+cliente.getCedula()+"&fechaEntrada="+fechaEntrada.toString()+"&fechaSalida="+fechaSalida.toString();
            } else {
                return "/InicioHoteles.xhtml?faces-redirect=true&amp;cedula="+cliente.getCedula()+"&fechaEntrada="+""+"&fechaSalida="+"";
            }
        } else {
            return "/InicioHoteles.xhtml?faces-redirect=true&amp;cedula="+""+"&fechaEntrada="+""+"&fechaSalida="+"";
        }
    }

    public void seleccionarHabitacion(Integer codigo){
        boolean centinela = true;
        try{
            if(!habitacionesSeleccionadas.isEmpty()){
                for(int i = 0; i < habitacionesSeleccionadas.size(); i++){
                    if(habitacionesSeleccionadas.get(i).getCodigo().equals(codigo)){
                        centinela = false;
                        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", "Ya esta añadida esta habitacion en el listado.");
                        FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
                    }
                }
                if(centinela) {
                    habitacionesSeleccionadas.add(habitacionServicio.buscarHabitacion(codigo));
                    System.out.println(habitacionServicio.buscarHabitacion(codigo).toString());
                }
            } else {
                habitacionesSeleccionadas.add(habitacionServicio.buscarHabitacion(codigo));
                System.out.println(habitacionServicio.buscarHabitacion(codigo).toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String seleccionarSillas() {
        Integer cantidad = Integer.parseInt(cantidadSillas);
        System.out.println("Entro en 1");
        try {
            if(sillaServicio.verificarDisponibilidad(vuelo, cantidad)) {
                System.out.println("Entro en 2");
                for(int i = 0; i < cantidad; i++) {
                    sillasSeleccionadas.add(sillaServicio.agregarSilla(150000.00,false, vuelo));
                }
                return "/cliente/RegistrarReserva.xhtml?faces-redirect=true";
            }
        }catch (Exception e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("msj-bean", msg);
        }
        return null;
    }

    public void seleccionarVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

}
