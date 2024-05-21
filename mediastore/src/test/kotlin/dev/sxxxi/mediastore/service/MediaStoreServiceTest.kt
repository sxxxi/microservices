package dev.sxxxi.mediastore.service

import dev.sxxxi.mediastore.data.Media
import dev.sxxxi.mediastore.domain.Services
import org.junit.jupiter.api.Test
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import java.io.File

@SpringBootTest
class MediaStoreServiceTest {
//    @Autowired
//    private lateinit var mediaStoreService: MediaStoreServiceImpl
//    private val logger = LoggerFactory.getLogger(this::class.java)
//
//    @Test
//    fun `files are uploaded to S3`() {
//        val file = File("src/main/resources/peace.jpg")
//        val mpFile = MockMultipartFile("peace.jpg", "peace.jpg", "image/jpeg", file.readBytes())
//        val res = mediaStoreService.store(Services.BLOG, Media(
//            contentType = mpFile.contentType ?: "image/jpg",
//            body = mpFile.bytes
//        ))
//        val signedUrl = mediaStoreService.get(res)
//
//        logger.info { signedUrl }
//        assert(true)
//    }


}