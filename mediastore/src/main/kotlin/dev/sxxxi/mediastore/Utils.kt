package dev.sxxxi.mediastore

import org.slf4j.Logger
import java.util.*

fun <T> elapsed(logger: Logger, funk: () -> T): T {
    val startTime = System.currentTimeMillis()
    val f = funk()
    logger.atInfo().log("Elapsed time: ${System.currentTimeMillis() - startTime}ms")
    return f
}

fun minutesFromNow(minutes: Int): Date {
    val cal = Calendar.getInstance()
    cal.add(Calendar.MINUTE, minutes)
    return cal.time
}

