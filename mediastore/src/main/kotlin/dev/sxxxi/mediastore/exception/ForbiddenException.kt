package dev.sxxxi.mediastore.exception

import dev.sxxxi.portfolio.core.exception.WebException
import org.springframework.http.HttpStatus

open class ForbiddenException(message: String) : WebException(code = HttpStatus.FORBIDDEN, reason = message)