package com.example.demo

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class Receive(private val connection: Connection) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    init {
        val channel = connection.createChannel()
        channel.queueDeclare(QUEUE_NAME, false, false, false, null)
        val pushCallback = DeliverCallback { _, delivery ->
            val message = String(delivery.body, Charsets.UTF_8)
            logger.info("Received: $message")
        }
        channel.basicConsume(QUEUE_NAME, true, pushCallback) { _ -> }
    }
}