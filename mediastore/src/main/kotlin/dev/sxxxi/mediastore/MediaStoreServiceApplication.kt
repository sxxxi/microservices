package dev.sxxxi.mediastore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
class MediaStoreServiceApplication

fun main(args: Array<String>) {
	runApplication<MediaStoreServiceApplication>(*args)
}
