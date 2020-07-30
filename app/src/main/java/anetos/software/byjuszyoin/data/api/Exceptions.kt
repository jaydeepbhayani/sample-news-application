package anetos.software.byjuszyoin.data.api


/**
 * Api service interface to handle all the data from retrofit
 *
 * created by Jaydeep Bhayani on 30/07/2020
 */

open class DataSourceException(message: String? = null) : Exception(message)

class RemoteDataNotFoundException : DataSourceException("Data not Found")