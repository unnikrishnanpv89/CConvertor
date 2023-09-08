package com.open.exchange.data.remote

import com.open.exchange.domain.models.common.Error
import com.open.exchange.domain.models.common.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

suspend fun <T : Any> networkCall(
    apiCall: suspend () -> T
): Flow<Response<T>> =
    flow{
        try {
            val response = apiCall.invoke()
            emit(Response.Success(data = response))
        } catch (exception: HttpException) {
            emit(when (exception.code()) {
                401 -> {
                    Response.Fail(Error.UnAuthorizedError)
                }
                403 -> {
                    Response.Fail(Error.UnAuthorizedError)
                }
                404 -> {
                    Response.Fail(Error.PageNotFoundError)
                }
                500 -> {
                    Response.Fail(Error.ServerError)
                }
                else -> {
                    Response.Fail(Error.UnknownError)
                }
            })
        } catch (exception: IOException) {
            emit(Response.Fail(Error.NoInternetError))
        } catch (exception: Exception) {
            emit(Response.Fail(Error.UnknownError))
        }
    }

