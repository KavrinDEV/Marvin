package com.kavrin.marvin.data.remote

import com.kavrin.marvin.BuildConfig
import com.kavrin.marvin.domain.model.tv.api.TvApiResponse
import com.kavrin.marvin.domain.model.tv.api.TvGenreApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBTvService {

	@GET("/tv/popular")
	suspend fun getPopularMovies(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): TvApiResponse


	@GET("/tv/top_rated")
	suspend fun getTopRatedMovies(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): TvApiResponse


	@GET("trending/tv/day")
	suspend fun getTrendingMovies(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): TvApiResponse


	@GET("/search/tv")
	suspend fun searchMovies(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("query") query: String,
		@Query("page") page: Int = 1,
		@Query("include_adult") includeAdult: Boolean = true
	): TvApiResponse


	@GET("/genre/tv/list")
	suspend fun getMovieGenres(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@Query("page") page: Int = 1
	): TvGenreApiResponse
}