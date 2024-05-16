package com.example.demo

import com.rabbitmq.client.Connection
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

@RestController
class SendController (
    private val connection: Connection
) {
    @GetMapping("/", "")
    fun readMessage(
        @RequestParam(name = "message", required = true) message: String
    ): String {
        connection.createChannel().use { channel ->
            channel.basicPublish("", QUEUE_NAME, null, message.toByteArray(Charsets.UTF_8))
        }
        return "Sent: $message"
    }

    @PostMapping("")
    fun sendMedia(
        image: MultipartFile
    ): String {
        connection.createChannel().use { channel ->
            val path = Path("src/main/resources/${image.originalFilename}")
            image.transferTo(Files.createFile(path))
            channel.basicPublish("", MEDIA_QUEUE_NAME, null, Files.readAllBytes(path))
            Files.delete(path)

        }
        return "ok"
    }

}