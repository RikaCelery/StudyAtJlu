import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.serialization.kotlinx.xml.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import utils.OkHttpUtil
import java.io.File

val client = HttpClient(OkHttp) {
//    install(Logging) {
//        logger = Logger.SIMPLE
//        level = LogLevel.INFO
//    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            prettyPrint = true
        })
        xml()
    }
    install(UserAgent) {
        agent = "Ktor client"
    }
    install(ContentEncoding) {
        deflate(1.0F)
        gzip(0.9F)
    }
    install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 5)
        constantDelay(300, 2000)
    }
    install(HttpTimeout) {
        connectTimeoutMillis = 3_000
    }
    engine {
        config {
            followRedirects(true)
            sslSocketFactory(OkHttpUtil.ignoreInitedSslFactory, OkHttpUtil.IGNORE_SSL_TRUST_MANAGER_X509)
            hostnameVerifier(OkHttpUtil.ignoreSslHostnameVerifier)
        }
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {
    val baseFolder = File("E:\\课程资料\\学期\\2022-2")
//    val query = "teachClassId" to "xxxxxxx"//算法
//    val query = "teachClassId" to "xxxxxxx"//操作系统
//    val query = "teachClassId" to "xxxxxxx"//习概论
    val query = "termId" to "xxxxxxx"
    download(baseFolder,query,)
}