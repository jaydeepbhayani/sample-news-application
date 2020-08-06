package anetos.software.byjuszyoin.data.api.remote

import android.content.Context
import android.util.Log
import anetos.software.byjuszyoin.data.api.ApiService
import anetos.software.byjuszyoin.data.api.RemoteDataNotFoundException
import anetos.software.byjuszyoin.data.model.AppDatabase
import anetos.software.byjuszyoin.data.model.TopHeadLinesResponse
import anetos.software.byjuszyoin.data.repository.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 *  *  [RemoteDataSourceImpl]
 * implementation for RemoteDataSource
 *  @author
 *  created by Jaydeep Bhayani on 30/07/2020
 */

class RemoteDataSourceImpl private constructor(
    private val apiService: ApiService,
    private val appdatabase: AppDatabase
) : RemoteDataSource {

    /**
     *  get top headlines according to given country
     */
    override suspend fun getTopHeadlinesData(
        country: String,
        pageSize: Int,
        page: Int
    ): Result<TopHeadLinesResponse> =
        withContext(Dispatchers.IO) {
            /*val request =
                apiService.getTopHeadlinesDataAsync(
                    TOP_HEADLINES_URL
                        .replace("{country}", country)
                )*/

            val request =
                apiService.getTopHeadlinesDataAsync(
                    country, pageSize, page
                )


            try {
                val response  = request.await()
                //if (response.status.equals("ok", ignoreCase = true))
                    Log.d("test", "result.getTopHeadlinesData : $response")
                    appdatabase.articlesDao().deleteAll()
                    appdatabase.articlesDao().insertAll(response.articles)
                    Result.Success(response)

            } catch (ex: HttpException) {
                Result.Error(RemoteDataNotFoundException())
            } catch (ex: Throwable) {
                Result.Error(RemoteDataNotFoundException())
            }
        }

    companion object {
        fun newInstance(context: Context) =
            RemoteDataSourceImpl(
                ApiService.create(context),
                AppDatabase.getInstance(context)
            )
    }
}