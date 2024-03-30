package ru.akella.cryptocoin.domain

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpSendPipeline
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
}

fun createHttpClient(
    header: AuthHeaders,
    httpClientEngine: HttpClientEngine,
    json: Json,
    log: Logger
) = HttpClient(httpClientEngine) {
    expectSuccess = true

    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            when (exception) {
                is ServerResponseException -> throw ServerResponseException(exception.response, "Server Exception")
                is ClientRequestException -> {
                    val response = exception.response
                    when (response.status) {
                        HttpStatusCode.NotFound -> throw NotFoundException(exception.cause)
                        HttpStatusCode.Forbidden -> throw ForbiddenException(exception.cause)
                        HttpStatusCode.Unauthorized -> throw UnauthorizedException(exception.cause)
                        HttpStatusCode.TooManyRequests -> throw TooManyRequestsException(exception.cause)
                        HttpStatusCode.InternalServerError -> throw ServerException(exception.cause)
                        else -> throw BadRequestException(exception.cause)
                    }
                }
            }
        }
    }

    install(ContentNegotiation) {
        json(json)
    }

    install(Logging) {
        logger = object : io.ktor.client.plugins.logging.Logger {
            override fun log(message: String) {
                log.v { message }
            }
        }

        level = LogLevel.ALL
        this.filter { true }
    }

    install(HttpTimeout) {
        val timeout = 30000L
        connectTimeoutMillis = timeout
        requestTimeoutMillis = timeout
        socketTimeoutMillis = timeout
    }
}.apply {
    sendPipeline.intercept(HttpSendPipeline.State) {
        context.headers.append(header.name, header.value)
    }
}
