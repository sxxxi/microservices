package dev.sxxxi.mediastore.exception

import dev.sxxxi.portfolio.core.exception.WebException
import org.springframework.http.HttpStatus

open class ConflictException(message: String) : WebException(code = HttpStatus.CONFLICT, reason = message)