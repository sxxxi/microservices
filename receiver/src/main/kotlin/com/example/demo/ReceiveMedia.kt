package com.example.demo

import com.rabbitmq.client.Connection
import com.rabbitmq.client.DeliverCallback
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.nio.file.Files
import javax.imageio.ImageIO
import kotlin.io.path.Path

@Component
class ReceiveMedia(connection: Connection) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    init {
        val channel = connection.createChannel()
        channel.queueDeclare(MEDIA_QUEUE_NAME, false, false, false, null)

        val pushCallback = DeliverCallback { _, delivery ->
            ByteArrayInputStream(delivery.body).use { bos ->
                ImageIO.read(bos)?.let { ib ->
                    val type = ib.type
                    val ext = when(type) {
                        5 -> "jpeg"
                        13 -> "png"
                        else -> "gif"
                    }
                    bos.reset()
                    Files.write(Path("src/main/resources/foo.$ext"), bos.readAllBytes())
                } ?: run {
                    logger.info("File type not supported")
                }



            }
        }
        channel.basicConsume(MEDIA_QUEUE_NAME, true, pushCallback) { _ -> }
    }

}