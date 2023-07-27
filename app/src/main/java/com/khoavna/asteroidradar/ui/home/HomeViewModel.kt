package com.khoavna.asteroidradar.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.khoavna.asteroidradar.MyApplication
import com.khoavna.asteroidradar.data.database.AppDatabase
import com.khoavna.asteroidradar.data.database.entity.Asteroid
import com.khoavna.asteroidradar.data.datasource.apod.ApodDataSourceImpl
import com.khoavna.asteroidradar.data.datasource.asteroid.AsteroidDataSourceImpl
import com.khoavna.asteroidradar.data.network.APIConfig
import com.khoavna.asteroidradar.data.network.result.ResultAPI
import com.khoavna.asteroidradar.data.network.wapi.apod.Apod
import com.khoavna.asteroidradar.data.repositores.apod.ApodRepositoryImpl
import com.khoavna.asteroidradar.data.repositores.asteroid.AsteroidRepositoryImpl
import com.khoavna.asteroidradar.domain.home.GetHomeUseCase
import com.khoavna.asteroidradar.domain.home.GetHomeUseCaseImpl
import com.khoavna.asteroidradar.worker.AsteroidWorkManager
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeViewModel(
    private val getHomeUseCase: GetHomeUseCase
) : ViewModel() {

    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    private val _typeShow = MutableStateFlow(TypeShow.ALL)
    val typeShow: LiveData<TypeShow> = _typeShow.asLiveData()

    private val _asteroids = getHomeUseCase.executeGetAsteroid()
    val asteroids = _typeShow.combine(_asteroids) { type, asteroids ->
        when (type) {
            TypeShow.ALL -> asteroids
            TypeShow.TODAY -> asteroids.filter {
                it.closeApproachDate == getToday()
            }

            TypeShow.WEEK -> asteroids.filter {
                checkWeek(it.closeApproachDate)
            }
        }
    }.asLiveData()

    private val _imageResult = MutableLiveData<Apod>()
    val imageResult: LiveData<Apod> = _imageResult

    private fun getToday(): String = dateFormat.format(Calendar.getInstance().time)

    private fun checkWeek(dateString: String): Boolean {
        val date = dateFormat.parse(dateString) ?: return false
        val now = Calendar.getInstance()
        if (date.before(now.time)) return false
        now.add(Calendar.DAY_OF_YEAR, NUMBER_DATE)
        if (date.after(now.time)) return false
        return true
    }

    fun setTypeShow(typeShow: TypeShow) {
        _typeShow.value = typeShow
    }

    fun fetchImageOfDay() {
        viewModelScope.launch {
            val result = async { getHomeUseCase.executeGetApod() }
            result.await().also {
                when (it) {
                    is ResultAPI.SUCCESS -> {
                        _imageResult.value = it.data
                    }

                    is ResultAPI.ERROR -> {
                        Log.e("TAG", "fetchImageOfDay: ${it.message}")
                    }
                }
            }
        }
    }

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val NUMBER_DATE = 7
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

    enum class TypeShow {
        ALL, TODAY, WEEK;
    }
}