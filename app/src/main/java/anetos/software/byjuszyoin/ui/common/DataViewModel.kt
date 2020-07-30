package anetos.software.byjuszyoin.ui.common

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import anetos.software.byjuszyoin.core.viewModelFactoryWithSingleArg
import anetos.software.byjuszyoin.data.api.RemoteDataNotFoundException
import anetos.software.byjuszyoin.data.model.Articles
import anetos.software.byjuszyoin.data.model.SafeRoute
import anetos.software.byjuszyoin.data.repository.DataRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/***
 *  created by Jaydeep Bhayani on 30/07/2020
 */

class DataViewModel(private val repository: DataRepository) : ViewModel() {

    companion object {
        val FACTORY =
            viewModelFactoryWithSingleArg(::DataViewModel)
    }


    /**
     *  Enable/Disable legends
     */
    val isLegendsEnabled = MutableLiveData<Boolean>()


    fun enableLegends(isEnabled: Boolean) = isLegendsEnabled.postValue(isEnabled)


    /**
     *  Top-Headlines Data mapping and refresh
     */
    val topHeadlinesData = repository.topHeadlinesData

    fun refreshTopHeadlinesData(country: String) {
        launchDataLoad { repository.refreshTopHeadlinesData(country) }
    }

    fun getAllArticles(): List<Articles>? = repository.getAllArticles()

    /*
    fun getAllSafeRoute(): List<SafeRoute>? = repository.getAllSafeRoute()

    fun getSafeRouteByLocation(source: String, destination: String): SafeRoute? =
        repository.getSafeRouteByLocation(source, destination)

    fun getLocationByPlaceName(placeName: String): Location? =
        repository.getLocationByPlaceName(placeName)

    fun getLocationByLatLon(latitude: String, longitude: String): Location? =
        repository.getLocationByLatLon(latitude, longitude)*/


    var displayList = ArrayList<Location>()

    /*fun refreshDisplayLocationData() {
        launchDataLoad {
            if (repository.locationData.value != null)
                displayList.addAll(repository.locationData.value!!)
        }
    }*/

    /*  var locationDetail = Location()

      fun getDisplayLocation(lat: Double, lon: Double) {
          launchDataLoad {
              if (repository.locationData.value != null)
                  displayList.addAll(repository.locationData.value!!)
          }
      }*/


    fun insertSafeRoute(source: String, destination: String) {
        launchDataLoad { repository.insertSafeRoute(source, destination) }
    }

    fun searchSafeRoute(source: String, destination: String) {
        launchDataLoad { repository.insertSafeRoute(source, destination) }
    }

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