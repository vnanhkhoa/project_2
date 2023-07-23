package com.khoavna.asteroidradar.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.khoavna.asteroidradar.MyApplication
import com.khoavna.asteroidradar.data.database.AppDatabase
import com.khoavna.asteroidradar.data.datasource.apod.ApodDataSourceImpl
import com.khoavna.asteroidradar.data.datasource.asteroid.AsteroidDataSourceImpl
import com.khoavna.asteroidradar.data.network.APIConfig
import com.khoavna.asteroidradar.data.network.result.ResultAPI
import com.khoavna.asteroidradar.data.network.wapi.apod.Apod
import com.khoavna.asteroidradar.data.repositores.apod.ApodRepositoryImpl
import com.khoavna.asteroidradar.data.repositores.asteroid.AsteroidRepositoryImpl
import com.khoavna.asteroidradar.domain.home.GetHomeUseCase
import com.khoavna.asteroidradar.domain.home.GetHomeUseCaseImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeUseCase: GetHomeUseCase
) : ViewModel() {

    val asteroids = getHomeUseCase.executeGetAsteroid().asLiveData()

    private val _imageResult = MutableLiveData<Apod>()
    val imageResult: LiveData<Apod> = _imageResult


    fun fetchImageOfDay() {
        viewModelScope.launch {
            val result = async { getHomeUseCase.executeGetApod() }
            result.await().also {
                when(it) {
                    is ResultAPI.SUCCESS -> {
                        _imageResult.value = it.data
                    }

                    is ResultAPI.ERROR -> {
                        Log.e("TAG", "fetchImageOfDay: ${it.message}", )
                    }
                }
            }
        }
    }

    companion object {
        val FACTORY = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MyApplication)
                val getAsteroidUseCase = GetHomeUseCaseImpl(
                    AsteroidRepositoryImpl(
                        asteroidDataSource = AsteroidDataSourceImpl(
                            AppDatabase.getInstance(application.applicationContext).getAsteroidDao()
                        )
                    ),
                    ApodRepositoryImpl(
                        apodDataSource = ApodDataSourceImpl(
                            APIConfig.getInstance().appService
                        )
                    )
                )
                HomeViewModel(getAsteroidUseCase)
            }
        }
    }
}