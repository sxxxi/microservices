package dev.sxxxi.mediastore.di

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class MediaStoreConfig {
    private val logger = LoggerFactory.getLogger(this::class.java)
    @Bean
    fun s3Client(): S3Client {
        return S3Client.builder()
            .region(Region.US_EAST_1)
            .forcePathStyle(true)
            .build()
    }

    // RabbitMQ Connection
    @Bean
    fun rabbitConnection(): Connection {
        return ConnectionFactory().apply {
            username = "seiji"
            password = "foobar"
            host = "192.168.50.153"
            port = 5672
        }.newConnection()
    }
}