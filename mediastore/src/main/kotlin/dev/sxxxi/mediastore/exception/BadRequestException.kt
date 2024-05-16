package dev.sxxxi.mediastore.exception

import dev.sxxxi.portfolio.core.exception.WebException
import org.springframework.http.HttpStatus

open class BadRequestException(message: String) : WebException(code = HttpStatus.BAD_REQUEST, reason = message)