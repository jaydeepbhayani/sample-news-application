package anetos.software.byjuszyoin.data.api

import android.content.Context
import anetos.software.byjuszyoin.core.Networking
import anetos.software.byjuszyoin.data.model.SafeRoute
import anetos.software.byjuszyoin.data.model.TopHeadLinesResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Url


/**
 * Api service interface to handle all the data from retrofit
 *
 * created by Jaydeep Bhayani on 30/07/2020
 */
interface ApiService {


    @GET
    fun getFloodDataAsync(@Url url: String): Deferred<ArrayList<SafeRoute>>


    @GET
    fun getTopHeadlinesDataAsync(@Url url: String): Deferred<TopHeadLinesResponse>


    companion object {

        fun create(context: Context): ApiService {
            return Networking.create(context)
        }
    }
}