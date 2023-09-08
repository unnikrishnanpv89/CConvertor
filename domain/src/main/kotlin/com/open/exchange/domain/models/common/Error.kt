package com.open.exchange.domain.models.common

sealed class Error(code: Int = 0, message: String = String()){
    object NoInternetError : Error()
    object ServerError : Error()
    object UnAuthorizedError : Error()
    object PageNotFoundError : Error()
    object UnknownError : Error()
    object CustomError: Error(){
    }

    object InvalidOrExpiredTokenError : Error()
    object IDSdkError : Error()
}