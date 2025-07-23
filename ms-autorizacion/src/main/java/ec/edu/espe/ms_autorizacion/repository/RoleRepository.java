package ec.edu.espe.ms_autorizacion.repository;

import ec.edu.espe.ms_autorizacion.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
