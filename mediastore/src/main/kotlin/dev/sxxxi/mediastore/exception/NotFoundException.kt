package dev.sxxxi.mediastore.exception

import dev.sxxxi.portfolio.core.exception.WebException
import org.springframework.http.HttpStatus

open class NotFoundException(message: String) : WebException(code = HttpStatus.NOT_FOUND, reason = message)