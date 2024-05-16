package dev.sxxxi.mediastore

import dev.sxxxi.mediastore.service.MediaStoreService
import dev.sxxxi.portfolio.media.domain.Services
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class MediaStoreController(
    private val mediaAccessService: MediaStoreService
) {
    @PostMapping(value = ["/", ""])
    fun uploadImage(@RequestParam file: MultipartFile): String {
        return mediaAccessService.get(mediaAccessService.store(Services.PROJECTS, file))
    }
}