package com.kavrin.marvin.data.local.dao.movie

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.movie.entities.MovieTrending
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndTrending

@Dao
interface MovieTrendingDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTrending(movieTrending: MovieTrending)

	@Query("DELETE FROM movie_top_rated_table")
	suspend fun deleteAllTrending()

	@Transaction
	@Query("SELECT * FROM movie_table WHERE movieId = :movieId")
	fun getMovieAndTrending(movieId: Int): PagingSource<Int, MovieAndTrending>
}