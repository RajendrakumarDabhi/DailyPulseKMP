package com.rajendra.dailypulsekmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform