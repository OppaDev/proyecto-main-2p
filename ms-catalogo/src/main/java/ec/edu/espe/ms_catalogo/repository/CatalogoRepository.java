package ec.edu.espe.ms_catalogo.repository;

import ec.edu.espe.ms_catalogo.entity.Catalogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
}