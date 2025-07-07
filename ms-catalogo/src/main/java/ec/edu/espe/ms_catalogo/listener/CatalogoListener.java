package ec.edu.espe.ms_catalogo.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.ms_catalogo.config.RabbitMQConfig;
import ec.edu.espe.ms_catalogo.dto.PublicacionRecibidaDto;
import ec.edu.espe.ms_catalogo.service.CatalogoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CatalogoListener {

    private static final Logger logger = LoggerFactory.getLogger(CatalogoListener.class);

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private ObjectMapper objectMapper; // Inyectado por Spring Boot

    @RabbitListener(queues = RabbitMQConfig.QUEUE_CATALOGO)
    public void recibirMensajeCatalogo(String mensajeJson) {
        logger.info("Mensaje recibido en {}: {}", RabbitMQConfig.QUEUE_CATALOGO, mensajeJson);
        try {

            PublicacionRecibidaDto publicacionRecibida = objectMapper.readValue(mensajeJson, PublicacionRecibidaDto.class);

            if (publicacionRecibida.getTipoPublicacion() == null || publicacionRecibida.getDatos() == null) {
                logger.error("Mensaje JSON malformado o campos 'tipoPublicacion'/'datos' faltantes: {}", mensajeJson);
                return;
            }

            catalogoService.procesarYGuardarPublicacion(publicacionRecibida);

        } catch (Exception e) {
            logger.error("Error al procesar mensaje de la cola {}: {}. Mensaje: {}",
                    RabbitMQConfig.QUEUE_CATALOGO, e.getMessage(), mensajeJson, e);

        }
    }
}