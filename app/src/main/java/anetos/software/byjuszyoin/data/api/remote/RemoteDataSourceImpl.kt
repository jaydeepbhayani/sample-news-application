package anetos.software.byjuszyoin.data.api.remote

import android.content.Context
import android.util.Log
import anetos.software.byjuszyoin.data.UrlConstants.TOP_HEADLINES_URL
import anetos.software.byjuszyoin.data.api.ApiService
import anetos.software.byjuszyoin.data.api.RemoteDataNotFoundException
import anetos.software.byjuszyoin.data.model.AppDatabase
import anetos.software.byjuszyoin.data.model.SafeRoute
import anetos.software.byjuszyoin.data.model.TopHeadLinesResponse
import anetos.software.byjuszyoin.data.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * RemoteDataSourceImpl impl for RemoteDataSource
 *
 * created by Jaydeep Bhayani on 30/07/2020
 */
class RemoteDataSourceImpl private constructor(
    private val apiService: ApiService,
    private val appdatabase: AppDatabase
) : RemoteDataSource {

    /**
     *  get top headlines according to given country
     */
    override suspend fun getTopHeadlinesData(country: String): Result<TopHeadLinesResponse> =
        withContext(Dispatchers.IO) {
            val request =
                apiService.getTopHeadlinesDataAsync(
                    TOP_HEADLINES_URL
                        .replace("{country}", country)
                )

            try {
                val response = request.await()
                if (response.status.equals("ok", ignoreCase = true)) {
                    Log.d("test", "result.getTopHeadlinesData : $response")
                    appdatabase.articlesDao().insertAll(response.articles)
                    Result.Success(response)
                } else {
                    Result.Error(RemoteDataNotFoundException())
                }
            } catch (ex: HttpException) {
                Result.Error(RemoteDataNotFoundException())
            } catch (ex: Throwable) {
                Result.Error(RemoteDataNotFoundException())
            }
        }

    override suspend fun insertSafeRoute(source: String, destination: String) {
        appdatabase.routeDao().insert(SafeRoute(0, source, destination))
    }

    companion object {
        fun newInstance(context: Context) =
            RemoteDataSourceImpl(
                ApiService.create(context),
                AppDatabase.getInstance(context)
            )
    }
}