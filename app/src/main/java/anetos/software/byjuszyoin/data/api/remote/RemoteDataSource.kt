package anetos.software.byjuszyoin.data.api.remote

import anetos.software.byjuszyoin.data.model.TopHeadLinesResponse
import anetos.software.byjuszyoin.data.repository.Result

/**
 *  *  [RemoteDataSource]
 * Handle remote data
 * Add the data here and handle in the implementation
 *  @author
 *  created by Jaydeep Bhayani on 30/07/2020
 */

interface RemoteDataSource {
    suspend fun getTopHeadlinesData(country: String, pageSize: Int, page: Int): Result<TopHeadLinesResponse>
}