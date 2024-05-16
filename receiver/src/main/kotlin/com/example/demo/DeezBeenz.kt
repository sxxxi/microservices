package com.example.demo

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DeezBeenz {

    @Bean
    fun amqpFactory(): ConnectionFactory {
        val factory = ConnectionFactory()
        factory.host = "localhost"
        factory.username = "seiji"
        factory.password = "foobar"
        return factory
    }

    @Bean
    fun amqpConnection(): Connection {
        val factory = amqpFactory()
        return factory.newConnection()
    }
}