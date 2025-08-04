package ec.edu.espe.ms_autorizacion.controller;

import ec.edu.espe.ms_autorizacion.model.Role;
import ec.edu.espe.ms_autorizacion.repository.RoleRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepo;

    public RoleController(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @GetMapping
    public List<Role> list() {
        return roleRepo.findAll();
    }

    @PostMapping
    public Role create(@Valid @RequestBody Role role) {
        return roleRepo.save(role);
    }
}
