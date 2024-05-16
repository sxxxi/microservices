package dev.sxxxi.mediastore.di

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class AwsBeans {
    @Bean
    fun s3Client(): S3Client {
        return S3Client.builder()
            .region(Region.US_EAST_1)
            .forcePathStyle(true)
            .build()
    }
}