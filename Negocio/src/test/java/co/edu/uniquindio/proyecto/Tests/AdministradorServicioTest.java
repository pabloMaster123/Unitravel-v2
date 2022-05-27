package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.Administrador;
import co.edu.uniquindio.proyecto.Interfaces.AdministradorServicio;
import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.Repositorios.AdministradorRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = NegocioApplication.class)
@Transactional
public class AdministradorServicioTest {

    @Autowired
    AdministradorRepo administradorRepo;

    @Autowired
    AdministradorServicio administradorServicio;

    Administrador administrador = new Administrador();

    void crearAdministrador() {
        administrador.setCedula("1004961210");
        administrador.setNombre("Pablo andres");
        administrador.setEmail("Pablitoal123@gmail.com");
        administrador.setPassword("12345");
        administrador.setCodigo(1);
        administradorRepo.save(administrador);
    }

    @Test
    public void loginTest() {
        crearAdministrador();
        try {
            administradorServicio.login(administrador.getEmail(), administrador.getPassword());
            System.out.println("Ingreso exitoso");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
