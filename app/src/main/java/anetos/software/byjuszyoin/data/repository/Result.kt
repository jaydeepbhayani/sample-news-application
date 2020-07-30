package anetos.software.byjuszyoin.data.repository

/**
 * Helper class for api data used in retrofit
 *
 * created by Jaydeep Bhayani on 30/07/2020
 */
sealed class Result<out T : Any> {

    class Success<out T : Any>(val data: T) : Result<T>()

    class Error(val exception: Throwable) : Result<Nothing>()
}