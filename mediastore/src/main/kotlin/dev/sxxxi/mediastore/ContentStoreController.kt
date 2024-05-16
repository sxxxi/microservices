package dev.sxxxi.mediastore

import dev.sxxxi.mediastore.service.MediaStoreService
import dev.sxxxi.portfolio.media.domain.Services
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/media")
class ContentStoreController(
    private val service: MediaStoreService
) {


    private val logger = LoggerFactory.getLogger(ContentStoreController::class.java)

    @PostMapping(value = ["/", ""])
    fun uploadImage(@RequestParam file: MultipartFile): String {
        return service.get(service.store(Services.PROJECTS, file))
    }

}