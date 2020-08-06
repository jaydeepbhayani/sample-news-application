package anetos.software.byjuszyoin.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import anetos.software.byjuszyoin.core.viewModelFactoryWithSingleArg
import anetos.software.byjuszyoin.data.api.RemoteDataNotFoundException
import anetos.software.byjuszyoin.data.model.Articles
import anetos.software.byjuszyoin.data.repository.DataRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * * [DataViewModel]
 *
 * Common [ViewModel] class.
 *  @author
 *  created by Jaydeep Bhayani on 30/07/2020
 */

class DataViewModel(private val repository: DataRepository) : ViewModel() {

    companion object {
        val FACTORY =
            viewModelFactoryWithSingleArg(::DataViewModel)

        const val VISIBLE_THRESHOLD = 5
    }


    /**
     *  Top-Headlines Data mapping and refresh
     */
    val topHeadlinesData = repository.topHeadlinesData

    fun refreshTopHeadlinesData(country: String, pageSize: Int, page: Int) {
        launchDataLoad { repository.refreshTopHeadlinesData(country, pageSize, page) }
    }


    fun getAllArticles(): List<Articles> = repository.getAllArticles()

    //--------------------------------------------------------------------------------------------//
    /***
     * SnackBar and Spinner common for all datas
     */
    private var _snackBar: MutableLiveData<String> = MutableLiveData()
    val snackbar: LiveData<String> get() = _snackBar
    var spinner: MutableLiveData<Boolean> = MutableLiveData()
    val spinner1: LiveData<Boolean> get() = spinner

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                spinner.value = true
                block()
            } catch (error: RemoteDataNotFoundException) {
                _snackBar.value = error.message
            } finally {
                spinner.value = false
            }
        }
    }
    //--------------------------------------------------------------------------------------------//
}