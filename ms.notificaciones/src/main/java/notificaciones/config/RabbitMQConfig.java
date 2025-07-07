package notificaciones.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue notificacionQueue(){
        return QueueBuilder.durable("notificaciones.cola").build();
    }

    @Bean
    public Queue relojQueue() {
        return QueueBuilder.durable("reloj.solicitud").build();
    }

    @Bean
    public Queue relojAjusteQueue() {
        return new Queue("reloj.ajuste.notificaciones", true);
    }

    @Bean
    public DirectExchange relojIntercambio() {
        return new DirectExchange("reloj.intercambio");
    }

    @Bean
    public Binding bindingAjuste(Queue relojAjusteQueue, DirectExchange relojIntercambio) {
        return BindingBuilder.bind(relojAjusteQueue).to(relojIntercambio).with("ms.notificaciones");
    }
}
