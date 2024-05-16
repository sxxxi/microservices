package dev.sxxxi.mediastore.service

import dev.sxxxi.portfolio.media.domain.Services
import dev.sxxxi.mediastore.exception.ContentTypeNotSupported
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer
import java.nio.file.Path
import java.security.MessageDigest
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class MediaStoreServiceImpl(private val s3: S3Client) : MediaStoreService {

    override fun store(serviceName: Services, file: MultipartFile): String {
        val fileSuffix = file.originalFilename?.split(".")?.get(1)
            ?: throw ContentTypeNotSupported("File suffix missing.")
        val contentType = when (file.contentType) {
            "image/jpeg", "image/png" -> "img"
            "video/mp4", "video/mpeg" -> "video"
            else -> throw ContentTypeNotSupported("Content type not supported.")
        }
        val fileName = genFileName(file)
        val tempFile = File.createTempFile(fileName, ".$fileSuffix")
        val path = Path.of(ROOT_DIR, serviceName.path, contentType, tempFile.name).toString()

        file.transferTo(tempFile)

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(BUCKET_NAME)
            .key(path)
            .build()

        // TODO: Implement multipart upload. [url: https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-s3-objects.html#list-objects]
        s3.putObject(putObjectRequest, RequestBody.fromBytes(tempFile.readBytes()))

        return path
    }

    private fun genFileName(file: MultipartFile): String {
        val md = MessageDigest.getInstance("MD5")
        val bos = ByteArrayOutputStream()
        val epochBytes = ByteBuffer.allocate(Long.SIZE_BYTES)

        bos.writeBytes(file.inputStream.use { it.readAllBytes() })
        epochBytes.put(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toByte())
        bos.write(epochBytes.array())

        return md.digest(bos.use { it.toByteArray() })
                .joinToString("") { "%02x".format(it) }
    }

    override fun get(key: String): String {
        S3Presigner.create().use { signer ->
            val getRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(
                    GetObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(key)
                        .build()
                )
                .signatureDuration(Duration.ofMinutes(PRE_SIGNED_URL_VALIDITY_MINUTES))
                .build()
            return signer.presignGetObject(getRequest).url().toString()
        }
    }

    override fun delete(key: String) {
        s3.deleteObject { builder ->
            builder
                .bucket(BUCKET_NAME)
                .key(key)
                .build()
        }
    }

    // TODO: Create a mechanism to switch S3 client region with environment variables for
    //  easy deployments to other regions. The bucket name too.
    companion object {
        private const val ROOT_DIR = "portfolio"
        private const val BUCKET_NAME = "seijiakakabe-991617069"
        private const val PRE_SIGNED_URL_VALIDITY_MINUTES = 1L
        private const val PART_SIZE_MIB = 1
    }
}