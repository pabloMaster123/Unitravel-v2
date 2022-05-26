package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Hotel;
import co.edu.uniquindio.proyecto.Interfaces.CiudadServicio;
import co.edu.uniquindio.proyecto.Interfaces.HotelServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
@ViewScoped
@Getter
@Setter
public class InicioHotelesBean implements Serializable {

    @Autowired
    private HotelServicio hotelServicio;

    @Autowired
    private CiudadServicio ciudadServicio;

    private List<Hotel> hoteles;

    private List<Ciudad> ciudades;

    private Ciudad ciudad;

    private Integer cantidadDeEstrellas;

    private String nombreBuscar;

    @Value("#{param['fechaEntrada']}")
    private String fechaEntrada;

    @Value("#{param['fechaSalida']}")
    private String fechaSalida;

    @Value("#{param['cedula']}")
    private String cedula;

    @PostConstruct
    public void inicializar(){
        try{
            this.ciudades = ciudadServicio.listar();
            this.hoteles = hotelServicio.listar();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void listarPorCiudad() {
        try{
            this.hoteles = hotelServicio.listarHotelPorCiudad(ciudad.getNombre());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarPorCantidadDeEstrellas() {
        try{
            this.hoteles = hotelServicio.listarHotelPorCantidadDeEstrellas(cantidadDeEstrellas);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listarPorCoindicencia() {
        try{
            this.hoteles = hotelServicio.buscarHotelPorNombre(nombreBuscar);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String verDetalle(Integer codigo, boolean autenticado){
        if(autenticado){
            return "/DetalleHotel.xhtml?faces-redirect=true&amp;codigoHotel="+codigo+"&seleccionar=true&fechaEntrada="+fechaEntrada+"&fechaSalida="+fechaSalida;
        } else {
            return "/DetalleHotel.xhtml?faces-redirect=true&amp;codigoHotel="+codigo+"&seleccionar=false&fechaEntrada="+fechaEntrada+"&fechaSalida="+fechaSalida;
        }
    }

}
