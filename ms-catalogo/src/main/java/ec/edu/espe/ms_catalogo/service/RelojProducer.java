package ec.edu.espe.ms_catalogo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.ms_catalogo.dto.HoraClienteDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;

@Service
public class RelojProducer {

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private ObjectMapper mapper;

    private static final String nombreNodo = "ms-catalogo";

    public void enviarHora() {
        try {
            HoraClienteDto dto = new HoraClienteDto(nombreNodo, Instant.now().toEpochMilli());
            template.convertAndSend("reloj.solicitud", dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}