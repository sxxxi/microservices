package dev.sxxxi.mediastore.exception

import dev.sxxxi.portfolio.core.exception.WebException
import org.springframework.http.HttpStatus

open class InternalErrorException(message: String) : WebException(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = message)