package com.thesubgraph.notable.data.common.error

fun ApplicationError.getMessage(): String {
    return when (this) {
        NetworkError.ServerNotResponding -> "Unable to reach server. Please try again later."
        NetworkError.ServerNotFound -> {
            "Unable to find server. Please check your connection or try again later."
        }
        NetworkError.RequestTimedOut -> "Request timed out. Please try again."
        NetworkError.NoInternet -> "Please check your internet connection and try again."
        WebServiceError.Authorization -> "You are not authorized to access this resource."
        WebServiceError.ServerError -> {
            "Server encountered an error. Please try again or contact us to raise an issue."
        }
        WebServiceError.EncodeFailed -> {
            "Request could not be created. Please try again or contact us to raise an issue."
        }
        WebServiceError.DecodeFailed -> {
            "Response could not be read. Please try again or contact us to raise an issue."
        }
        WebServiceError.Unknown -> "Something went wrong."
        else -> "Something went wrong."
    }
}

fun ApplicationError.toDomain(): ErrorModel {
    return ErrorModel(this, this.getMessage())
}

fun String.toErrorDomain(): ErrorModel {
    return ErrorModel(WebServiceError.Custom, this)
}

fun getError(status: Int): ApplicationError {
    return when (status) {
        400 -> WebServiceError.EncodeFailed
        401 -> WebServiceError.Authentication
        403 -> WebServiceError.Authorization
        404 -> NetworkError.ServerNotFound
        503, 429 -> NetworkError.ServerNotResponding
        500 -> WebServiceError.ServerError
        408 -> NetworkError.RequestTimedOut
        422 -> WebServiceError.Custom
        else -> WebServiceError.Unknown
    }
//    }
}
