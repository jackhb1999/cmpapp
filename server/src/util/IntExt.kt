package com.hb.util

import io.ktor.http.*

fun Int.toStatus(): HttpStatusCode {
    return HttpStatusCode.fromValue(this)
}