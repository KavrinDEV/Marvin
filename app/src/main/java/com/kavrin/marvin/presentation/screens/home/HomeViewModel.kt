package com.kavrin.marvin.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kavrin.marvin.domain.use_cases.home.HomeUseCases
import com.kavrin.marvin.util.NetworkListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: HomeUseCases,
	networkListener: NetworkListener
) : ViewModel() {


	val getCarouselMovies = useCases.getCarouselMovies().cachedIn(viewModelScope)
	val getPopularMovies = useCases.getHomePopularMovies().cachedIn(viewModelScope)
	val getTopRatedMovies = useCases.getHomeTopRatedMovies().cachedIn(viewModelScope)
	val getTrendingMovies = useCases.getHomeTrendingMovies().cachedIn(viewModelScope)

	val getCarouselTvs = useCases.getCarouselTvs().cachedIn(viewModelScope)
	val getPopularTvs = useCases.getHomePopularTvs().cachedIn(viewModelScope)
	val getTopRatedTvs = useCases.getHomeTopRatedTvs().cachedIn(viewModelScope)
	val getTrendingTvs = useCases.getHomeTrendingTvs().cachedIn(viewModelScope)

	val isConnected = networkListener.checkNetworkAvailability()

	fun deleteAll() {
		viewModelScope.launch(Dispatchers.IO) {
			useCases.deleteAll()
		}
	}
}