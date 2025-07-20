package com.rajendra.dailypulsekmp

import io.ktor.client.engine.HttpClientEngineFactory

expect fun httpClientEngineFactory(): HttpClientEngineFactory<*>