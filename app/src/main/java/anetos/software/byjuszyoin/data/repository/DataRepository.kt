package anetos.software.byjuszyoin.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import anetos.software.byjuszyoin.data.api.RemoteDataNotFoundException
import anetos.software.byjuszyoin.data.api.remote.RemoteDataSource
import anetos.software.byjuszyoin.data.model.AppDatabase
import anetos.software.byjuszyoin.data.model.Articles
import anetos.software.byjuszyoin.data.model.SafeRoute
import anetos.software.byjuszyoin.data.model.TopHeadLinesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class DataMainRepository

/**
 * A data repo containing a top headlines, selected news articles from list details.
 *
 * created by Jaydeep Bhayani on 30/07/2020
 */
class DataRepository(
    private val dataSource: RemoteDataSource,
    private val appDatabase: AppDatabase
) : DataMainRepository() {

    //RemoteDataSourceRepo

    val safeRouteData: MutableLiveData<List<SafeRoute>> = MutableLiveData()
    val topHeadlinesData: MutableLiveData<TopHeadLinesResponse> = MutableLiveData()

    /**
     * [Live Data] to load topHeadlinesData.  topHeadlinesData's will be loaded from the repository cache.
     * Observing this will not cause the repos to be refreshed, use [refreshTopHeadlinesData].
     */
    suspend fun refreshTopHeadlinesData(country: String) {
        withContext(Dispatchers.IO) {
            try {
                val result = dataSource.getTopHeadlinesData(country)
                if (result is Result.Success) {
                    topHeadlinesData.postValue(result.data)
                }

            } catch (error: RemoteDataNotFoundException) {
                throw DataRefreshError(error)
            }
        }
    }

    /*fun getLocationByPlaceName(placeName: String): Location? {
        return try {
            val locationFromDb = appDatabase.locationDao().getByPlaceName(placeName)
            Log.d("getLocationByPlaceName", "LocationFromDb $locationFromDb")
            locationFromDb
        } catch (error: Exception) {
            null
        }
    }

    fun getLocationByLatLon(latitude: String, longitude: String): Location? {
        return try {
            val locationFromDb = appDatabase.locationDao().getByLatLon(latitude, longitude)
            Log.d("test", "LocationFromDb $locationFromDb")
            locationFromDb
        } catch (error: Exception) {
            null
        }
    }

    */

    fun getAllArticles(): List<Articles>? {
        return try {
            val articlesFromDb = appDatabase.articlesDao().getAll()
            Log.d("test", "ArticlesFromDb $articlesFromDb")
            articlesFromDb
        } catch (error: Exception) {
            null
        }
    }

    fun getSafeRouteByLocation(start: String, destination: String): SafeRoute? {
        return try {
            val routeFromDb = appDatabase.routeDao().getByLocations(start, destination)
            Log.d("test", "getSafeRouteByLocation $routeFromDb")
            routeFromDb
        } catch (error: Exception) {
            null
        }
    }


    suspend fun insertSafeRoute(source: String, destination: String) {
        withContext(Dispatchers.IO) {
            try {
                dataSource.insertSafeRoute(source, destination)
            } catch (error: RemoteDataNotFoundException) {
                throw DataRefreshError(error)
            }
        }
    }

    class DataRefreshError(cause: Throwable) : Throwable(cause.message, cause)
}