package notificaciones.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import notificaciones.dto.NotificacionDto;
import notificaciones.entity.Notificacion;
import notificaciones.services.NotificacionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component

public class NotificacionListener {
    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener (queues = "notificaciones.cola")
    public void recibirNotificacion(String mensajeJson) {
        try {
            NotificacionDto dto = objectMapper.readValue(mensajeJson, NotificacionDto.class);
            // Convertir NotificacionDto a Notificacion y guardar
            Notificacion notificacion = new Notificacion();
            notificacion.setMensaje(dto.getMensaje());
            notificacion.setTipo(dto.getTipo());
            notificacion.setFecha(java.time.LocalDateTime.now());
            notificacionService.guardarNotificacion(notificacion);
        } catch (Exception e) {
            // Puedes agregar logging aquí si lo deseas
            e.printStackTrace();
        }
    }
}
