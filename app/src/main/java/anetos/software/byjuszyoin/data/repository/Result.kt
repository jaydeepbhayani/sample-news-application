package anetos.software.byjuszyoin.data.repository

/**
 * *[Result]
 *
 * Helper class for api data used in retrofit
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */
sealed class Result<out T : Any> {

    class Success<out T : Any>(val data: T) : Result<T>()

    class Error(val exception: Throwable) : Result<Nothing>()
}