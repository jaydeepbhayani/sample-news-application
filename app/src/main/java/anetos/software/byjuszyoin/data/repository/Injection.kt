package anetos.software.byjuszyoin.data.repository

import android.content.Context
import anetos.software.byjuszyoin.data.api.remote.RemoteDataSourceImpl
import anetos.software.byjuszyoin.data.model.AppDatabase

/**
 * *[Injection]
 *
 * All the viewModel Injections will go here.
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */
object Injection {

    fun provideDataRepository(context: Context) =
        DataRepository(
            RemoteDataSourceImpl.newInstance(context),
            AppDatabase.getInstance(context)
        )
}