package com.kavrin.marvin.data.local.dao.movie.remote_keys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kavrin.marvin.domain.model.movie.entities.remote_keys.MovieTrendingRemoteKeys

@Dao
interface MovieTrendingRemoteKeysDao {

	@Query("SELECT * FROM movie_trending_remote_table WHERE id = :id")
	suspend fun getTrendingRemoteKeys(id: Int): MovieTrendingRemoteKeys?

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun addTrendingRemoteKeys(trendingRemoteKeys: List<MovieTrendingRemoteKeys>)

	@Query("DELETE FROM movie_trending_remote_table")
	suspend fun deleteAllTrendingRemoteKeys()
}