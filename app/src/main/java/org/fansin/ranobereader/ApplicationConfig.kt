package org.fansin.ranobereader

object ApplicationConfig {
    const val CONNECTION_TIMEOUT: Long = 30000
    const val BASE_URL = "https://xn--80ac9aeh6f.xn--p1ai/api/v2/"
    const val NOVEL_LOAD_RETRIES = 5
    const val NOVEL_RETRY_DELAY = 1000L
}
