package anetos.software.byjuszyoin.data.api

import android.content.Context
import anetos.software.byjuszyoin.core.Networking
import anetos.software.byjuszyoin.data.model.TopHeadLinesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * * [ApiService]
 * Api service interface to handle all the data from retrofit
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */
interface ApiService {

    @GET
    fun getTopHeadlinesDataAsync(@Url url: String): Deferred<TopHeadLinesResponse>

    @GET("top-headlines?apiKey=1d71eb1867514d0690dfeecd9063ec16")
    fun getTopHeadlinesDataAsync(
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int
    ): Deferred<TopHeadLinesResponse>

    companion object {

        fun create(context: Context): ApiService {
            return Networking.create(context)
        }
    }
}