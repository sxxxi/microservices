package dev.sxxxi.mediastore.exception

import dev.sxxxi.portfolio.core.exception.WebException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(WebException::class)
    fun handleProjectNotFound(e: WebException): ResponseEntity<ErrorPacket> {
        val error = ErrorPacket(
            code = e.code.value(),
            message = e.message ?: DEFAULT_MESSAGE
        )
        return ResponseEntity(error, e.code)
    }

    companion object {
        private const val DEFAULT_MESSAGE = "Something went wrong."
    }
}