package co.edu.uniquindio.proyecto.Tests;

import co.edu.uniquindio.proyecto.Entidades.Ciudad;
import co.edu.uniquindio.proyecto.Entidades.Cliente;
import co.edu.uniquindio.proyecto.Interfaces.ClienteServicio;
import co.edu.uniquindio.proyecto.NegocioApplication;
import co.edu.uniquindio.proyecto.Repositorios.CiudadRepo;
import co.edu.uniquindio.proyecto.Repositorios.ClienteRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = NegocioApplication.class)
@Transactional
public class ClienteServicioTest {

    @Autowired
    ClienteServicio clienteServicio;

    @Autowired
    ClienteRepo clienteRepo;

    @Autowired
    CiudadRepo ciudadRepo;

    Cliente cliente = new Cliente();

    List<Cliente> clientes = new ArrayList<>();

    void crearCliente() {
        cliente.setCedula("1004961210");
        cliente.setNombre("Pablo andres");
        cliente.setEmail("Pablitoal123@gmail.com");
        cliente.setPassword("12345");
        clienteRepo.save(cliente);
    }

    void listarCliente() {
        clientes.add(new Cliente("1004961211", "Natalia", "Natalia@gmail.com", "12345",
                null, crearCiudad()));
        clientes.add(new Cliente("1004961212", "Jose", "Jose@gmail.com", "54321",
                null, crearCiudad()));
        clientes.add(new Cliente("1004961213", "Maria", "Maria@gmail.com", "67890",
                null, crearCiudad()));
        clienteRepo.saveAll(clientes);
    }

    Ciudad crearCiudad() {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre("Madrid");
        ciudadRepo.save(ciudad);
        return ciudad;
    }

    @Test
    public void agregarClienteTest() {
        try {
            ArrayList<String> telefonos = new ArrayList<String>();
            telefonos.add("123456789");
            clienteServicio.registrarCliente("0012340921", "Jose Alfredo", "josealfredore200326@gmail.com", "miPassword", crearCiudad(), telefonos);
            clienteServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals("Jose Alfredo", clienteRepo.findById("0012340921").get().getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ActualizarClienteTest() {
        crearCliente();
        try {
            ArrayList<String> telefonos = new ArrayList<String>();
            telefonos.add("123456789");
            clienteServicio.actualizarCliente(cliente.getCedula(), "Pablo", "pablitoal175@gmail.com", "pab123", crearCiudad(), telefonos);
            clienteServicio.listar().forEach(u -> System.out.println(u));
            Cliente aux = clienteRepo.getById(cliente.getCedula());
            Assertions.assertEquals("Pablo", aux.getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eliminarClienteTest() {
        crearCliente();
        try {
            clienteServicio.eliminarClientePorCedula(cliente.getCedula());
            Assertions.assertNull(clienteRepo.findById(cliente.getCedula()).orElse(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscarClientePorCedula() {
        crearCliente();
        try {
            System.out.println(clienteServicio.buscarClientePorCedula(cliente.getCedula()));
            Assertions.assertEquals("Pablo andres", clienteServicio.buscarClientePorCedula(cliente.getCedula()).getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listar() {
        crearCliente();
        listarCliente();
        try {
            clienteServicio.listar().forEach(u -> System.out.println(u));
            Assertions.assertEquals(clienteRepo.findAll().size(), clienteServicio.listar().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void recuperarPasswordPorEmailClienteTest() {
        crearCliente();
        try {
            System.out.println(clienteServicio.recuperarPasswordUsandoCorreoElectronico(cliente.getEmail()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

