package com.thesubgraph.notable.data.common

import com.thesubgraph.notable.data.common.error.WebServiceError
import com.thesubgraph.notable.data.common.error.toDomain
import com.thesubgraph.notable.data.common.error.toErrorDomain
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException

suspend inline fun <reified T, U> HttpClient.apiCall(
    noinline mapper: (T) -> U?,
    crossinline block: HttpRequestBuilder.() -> Unit,
): ValueResult<U> =
    withContext(Dispatchers.IO) {
        try {
            val response: HttpResponse =
                request {
                    block()
                }
            when (response.status.value) {
                in 200 until 300 -> {
                    val body: T = response.body()
                    val mappedBody = mapper(body)
                    mappedBody?.let {
                        ValueResult.Success(it)
                    } ?: ValueResult.Failure(WebServiceError.DecodeFailed.toDomain())
                }
                400 ->{
                   val error = response.bodyAsText()
                    ValueResult.Failure(error.toErrorDomain())
                }
                401 -> ValueResult.Failure(WebServiceError.Authentication.toDomain())
                403 -> ValueResult.Failure(WebServiceError.Authorization.toDomain())
                422 -> ValueResult.Failure(WebServiceError.Custom.toDomain())
                500 -> ValueResult.Failure(WebServiceError.ServerError.toDomain())

                else -> ValueResult.Failure(WebServiceError.Unknown.toDomain())
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

// TODO handle all exceptions
fun handleException(e: Exception): ValueResult.Failure =
    when (e) {
        is ClientRequestException -> ValueResult.Failure(WebServiceError.Unknown.toDomain())
        is ServerResponseException -> ValueResult.Failure(WebServiceError.Unknown.toDomain())
        is SerializationException -> ValueResult.Failure(WebServiceError.Unknown.toDomain())
        is TimeoutCancellationException -> ValueResult.Failure(WebServiceError.Unknown.toDomain())
        else ->
            ValueResult.Failure(
                e.message?.toErrorDomain() ?: WebServiceError.Unknown.toDomain(),
            )
    }

fun mapHttpStatusCodeToError(statusCode: Int): WebServiceError {
    // Implement mapping based on statusCode
    return WebServiceError.Unknown // Placeholder
}