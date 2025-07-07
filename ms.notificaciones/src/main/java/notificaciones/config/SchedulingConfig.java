package notificaciones.config;


import notificaciones.services.RelojProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class SchedulingConfig {
    @Autowired
    private RelojProducer relojProducer;

    @Scheduled(fixedRate = 10000) // 10 segundos
    public void reportarHora() {
        try {
            relojProducer.enviarHora();
            System.out.println("Nodo: ms.notificaciones -> Enviando hora");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
