package anetos.software.byjuszyoin.data.api.remote

import anetos.software.byjuszyoin.data.model.TopHeadLinesResponse
import anetos.software.byjuszyoin.data.repository.Result


/**
 * Handle remote data
 * Add the data here and handle in the implementation
 *
 * created by Jaydeep Bhayani on 30/07/2020
 */
interface RemoteDataSource {
    suspend fun getTopHeadlinesData(country: String): Result<TopHeadLinesResponse>

    suspend fun insertSafeRoute(source: String, destination: String)
}