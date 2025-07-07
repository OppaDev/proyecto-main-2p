package ec.edu.espe.ms_catalogo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_CATALOGO = "catalogo.cola";

    @Bean
    public Queue catalogoQueue() {
        return QueueBuilder.durable(QUEUE_CATALOGO).build();
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue relojQueue() {
        return QueueBuilder.durable("reloj.solicitud").build();
    }


    @Bean
    public Queue relojAjusteQueue() {
        return new Queue("reloj.ajuste.catalogo", true);
    }

    @Bean
    public DirectExchange relojIntercambio() {
        return new DirectExchange("reloj.intercambio");
    }

    @Bean
    public Binding bindingAjuste(Queue relojAjusteQueue, DirectExchange relojIntercambio) {
        return BindingBuilder.bind(relojAjusteQueue).to(relojIntercambio).with("ms-catalogo");
    }

}