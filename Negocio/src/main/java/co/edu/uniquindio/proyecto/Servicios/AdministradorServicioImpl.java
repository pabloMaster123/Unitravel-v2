package co.edu.uniquindio.proyecto.Servicios;

import co.edu.uniquindio.proyecto.Entidades.Administrador;
import co.edu.uniquindio.proyecto.Entidades.AdministradorHotel;
import co.edu.uniquindio.proyecto.Entidades.Cliente;
import co.edu.uniquindio.proyecto.Interfaces.AdministradorServicio;
import co.edu.uniquindio.proyecto.Repositorios.AdministradorHotelRepo;
import co.edu.uniquindio.proyecto.Repositorios.AdministradorRepo;
import co.edu.uniquindio.proyecto.Repositorios.ClienteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministradorServicioImpl implements AdministradorServicio {

    @Autowired
    private AdministradorRepo administradorRepo;

    @Autowired
    private ClienteRepo clienteRepo;

    @Autowired
    private AdministradorHotelRepo administradorHotelRepo;

    @Override
    public Administrador login(String email, String password) throws Exception {
        return administradorRepo.findByEmailAndPassword(email, password).orElseThrow(() -> new Exception("Datos incorrectos."));
    }

    @Override
    public Administrador crear(String cedula, String nombre, String email, String password) throws Exception {

        Optional<Cliente> buscar = clienteRepo.findById(cedula);
        Optional<Administrador> buscar2 = administradorRepo.findById(cedula);
        Optional<AdministradorHotel> buscar3 = administradorHotelRepo.findById(cedula);

        if (buscar.isPresent() || buscar2.isPresent() || buscar3.isPresent()){
            throw new Exception("La cedula ya esta registrada en el sistema");
        }

        buscar = clienteRepo.findByEmail(email);
        buscar2 = administradorRepo.findByEmail(email);
        buscar3 = administradorHotelRepo.findByEmail(email);

        if (buscar.isPresent() || buscar2.isPresent() || buscar3.isPresent()){
            throw new Exception("El email ya esta registrado");
        }


        Integer codigo;
        if(!administradorRepo.findAll().isEmpty()){
            codigo = administradorRepo.findAll().size()+1;

        } else {
            codigo = 1;
        }
        return administradorRepo.save(new Administrador(cedula, nombre, email, password, codigo));
    }
}
