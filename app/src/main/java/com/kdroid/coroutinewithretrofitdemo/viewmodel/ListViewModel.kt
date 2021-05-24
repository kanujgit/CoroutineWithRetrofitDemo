package com.kdroid.coroutinewithretrofitdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kdroid.coroutinewithretrofitdemo.data.CountriesService
import com.kdroid.coroutinewithretrofitdemo.model.Country
import kotlinx.coroutines.*
import retrofit2.Response

class ListViewModel : ViewModel() {

    private val countryService = CountriesService.getCountriesService()
    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable -> onError("Error ${throwable.localizedMessage}") }
    private val job: Job? = null
    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response: Response<List<Country>> = countryService.getCountries()
            // switch the dispatcher
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    countries.value = response.body()
                    countryLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error ${response.message()}")
                }
            }

        }

    }

    private fun generateDummyCountries(): List<Country> {
        val countries = arrayListOf<Country>()
        countries.add(Country("dummyCountry1", "dummyCapital1", ""))
        countries.add(Country("dummyCountry2", "dummyCapital2", ""))
        countries.add(Country("dummyCountry3", "dummyCapital3", ""))
        countries.add(Country("dummyCountry4", "dummyCapital4", ""))
        countries.add(Country("dummyCountry5", "dummyCapital5", ""))
        return countries
    }

    private fun onError(message: String) {
        CoroutineScope(Dispatchers.Main).launch {
            countryLoadError.value = message
            loading.value = false
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }


}