package dev.sxxxi.portfolio.core.exception

import org.springframework.http.HttpStatusCode

open class WebException(
    val code: HttpStatusCode,
    reason: String
) : Exception(reason)

