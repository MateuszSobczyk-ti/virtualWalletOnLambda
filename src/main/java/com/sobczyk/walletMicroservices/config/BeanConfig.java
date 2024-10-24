package com.sobczyk.walletMicroservices.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class BeanConfig {
    private final CachingConnectionFactory cachingConnectionFactory;

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange("x.performance-core.dlx");
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("x.performance-core");
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable("q.performance-core.dlq").build();
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable("q.performance-core")
                .withArgument("x-dead-letter-exchange", "x.performance-core.dlx")
                .withArgument("x-dead-letter-routing-key", "deadLetter").build();
    }

    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("performance-core");
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

}
