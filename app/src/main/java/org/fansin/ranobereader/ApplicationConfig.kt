package org.fansin.ranobereader

object ApplicationConfig {
    const val CONNECTION_TIMEOUT: Long = 30000
    const val BASE_URL = "https://xn--80ac9aeh6f.xn--p1ai/api/v2/"
    const val NOVEL_LOAD_MAX_RETRIES = 5
    const val NOVEL_RETRY_DELAY = 1000L
    const val NOVELS_PER_PAGE = 16
    const val PAGED_LIST_EXECUTORS_COUNT = 5
}
