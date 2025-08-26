package com.example.brewbuddy.core.data.remote

import android.os.Build
import androidx.annotation.RequiresExtension
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException
import kotlin.coroutines.cancellation.CancellationException

open class ApiException(message: String, val statusCode: Int? = null) : Exception(message)

class InternetConnectionException(message: String = "No internet connection") :
    ApiException(message)

class ApiTimeoutException(message: String = "Request timed out") : ApiException(message)

class CertificateException(message: String = "Invalid certificate") : ApiException(message)

class RequestCancelledException(message: String = "Request cancelled") : ApiException(message)

class DataParsingException(message: String = "Data parsing error") : ApiException(message)

class BadRequestException(message: String = "Bad request", statusCode: Int? = 400) :
    ApiException(message, statusCode)

class UnauthorizedException(message: String = "Unauthorized", statusCode: Int? = 401) :
    ApiException(message, statusCode)

class ForbiddenException(message: String = "Forbidden", statusCode: Int? = 403) :
    ApiException(message, statusCode)

class NotFoundException(message: String = "Not found", statusCode: Int? = 404) :
    ApiException(message, statusCode)

class InternalServerErrorException(
    message: String = "Internal server error",
    statusCode: Int? = 500
) : ApiException(message, statusCode)

class UnknownApiException(message: String = "Unexpected error") : ApiException(message)

object ApiManager {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun <T> execute(apiCall: suspend () -> T): Result<T> {
        return try {
            val response = apiCall()
            Result.Success(response)
        } catch (e: SocketException) {
            Result.Failure(InternetConnectionException("No internet connection"))
        } catch (e: SocketTimeoutException) {
            Result.Failure(ApiTimeoutException("Connection timed out"))
        } catch (e: SSLException) {
            Result.Failure(CertificateException("Invalid certificate"))
        } catch (e: CancellationException) {
            Result.Failure(RequestCancelledException("Request cancelled"))
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: IOException) {
            Result.Failure(InternetConnectionException("Connection failed"))
        } catch (e: JSONException) {
            Result.Failure(DataParsingException("Data parsing error"))
        } catch (e: Exception) {
            Result.Failure(UnknownApiException(e.message ?: "Unexpected error"))
        }
    }

    private fun <T> handleHttpException(e: HttpException): Result<T> {
        val code = e.code()
        val errorBodyString = try {
            e.response()?.errorBody()?.string()
        } catch (_: Exception) {
            null
        }

        val errorMessage = extractErrorMessage(errorBodyString)

        return when (code) {
            400 -> Result.Failure(BadRequestException(errorMessage, code))
            401 -> Result.Failure(UnauthorizedException(errorMessage, code))
            403 -> Result.Failure(ForbiddenException(errorMessage, code))
            404 -> Result.Failure(NotFoundException(errorMessage, code))
            500 -> Result.Failure(InternalServerErrorException(errorMessage, code))
            else -> Result.Failure(UnknownApiException("Unexpected error: $code - $errorMessage"))
        }
    }

    private fun extractErrorMessage(body: String?): String {
        if (body.isNullOrBlank()) {
            return "Unexpected server error"
        }

        return try {
            val json = JSONObject(body)
            val message = json.optString("message").takeIf { it.isNotBlank() }
            message ?: "Unexpected server error"
        } catch (ex: Exception) {
            "Unexpected server error"
        }
    }
}