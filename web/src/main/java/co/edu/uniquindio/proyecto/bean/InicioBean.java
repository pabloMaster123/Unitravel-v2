package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.Interfaces.AdministradorServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

@Component
@ViewScoped
@Getter
@Setter
public class InicioBean implements Serializable {

    private String mensaje = "Sustentación Software 3" ;

/*    @Autowired
    private AdministradorServicio administradorServicio;

    @PostConstruct
    public void inicializar(){
         try{
             administradorServicio.crear("1006580999", "Jose Ramirez", "joseAdmin@gmail.com", "0123456");
         }catch(Exception e){
             e.printStackTrace();
         }

    }*/

}
