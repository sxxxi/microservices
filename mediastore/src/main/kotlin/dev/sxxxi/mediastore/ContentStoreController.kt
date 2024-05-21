package dev.sxxxi.mediastore

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Connection
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class ContentStoreController(
    private val connection: Connection
) {
    private val logger = LoggerFactory.getLogger(ContentStoreController::class.java)

    @PostMapping(value = ["/", ""])
    fun uploadImage(@RequestParam image: MultipartFile): String {
        val headers = AMQP.BasicProperties.Builder()
            .contentType(image.contentType)
            .build()
        connection.createChannel().basicPublish("", MediaReceiver.QUEUE_NAME, headers, image.bytes)
        return "ok"
    }

}