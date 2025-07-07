package notificaciones.repository;

import notificaciones.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

}
