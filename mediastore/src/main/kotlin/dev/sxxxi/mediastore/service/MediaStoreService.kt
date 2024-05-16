package dev.sxxxi.mediastore.service

import dev.sxxxi.portfolio.media.domain.Services
import org.springframework.web.multipart.MultipartFile

interface MediaStoreService {
    fun store(serviceName: Services, file: MultipartFile): String
    fun get(key: String): String
    fun delete(key: String)
}