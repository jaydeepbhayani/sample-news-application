package anetos.software.byjuszyoin.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import anetos.software.byjuszyoin.data.api.RemoteDataNotFoundException
import anetos.software.byjuszyoin.data.api.remote.RemoteDataSource
import anetos.software.byjuszyoin.data.model.AppDatabase
import anetos.software.byjuszyoin.data.model.Articles
import anetos.software.byjuszyoin.data.model.TopHeadLinesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class DataMainRepository

/**
 * *[DataRepository]
 *
 * A data repo containing a top headlines, selected news articles from list details.
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */
class DataRepository(
    private val dataSource: RemoteDataSource,
    private val appDatabase: AppDatabase
) : DataMainRepository() {

    //RemoteDataSourceRepo

    val topHeadlinesData: MutableLiveData<TopHeadLinesResponse> = MutableLiveData()

    /**
     * [Live Data] to load topHeadlinesData.  topHeadlinesData's will be loaded from the repository cache.
     * Observing this will not cause the repos to be refreshed, use [refreshTopHeadlinesData].
     */
    suspend fun refreshTopHeadlinesData(country: String, pageSize: Int, page: Int) {
        withContext(Dispatchers.IO) {
            try {
                val result = dataSource.getTopHeadlinesData(country, pageSize, page)
                if (result is Result.Success) {
                    topHeadlinesData.postValue(result.data)
                }

            } catch (error: RemoteDataNotFoundException) {
                throw DataRefreshError(error)
            }
        }
    }

    fun getAllArticles(): List<Articles> {
        return try {
            val articlesFromDb = appDatabase.articlesDao().getAll()
            Log.d("ArticlesFromDb:", "$articlesFromDb")
            articlesFromDb
        } catch (error: Exception) {
            emptyList()
        }
    }

    class DataRefreshError(cause: Throwable) : Throwable(cause.message, cause)
}