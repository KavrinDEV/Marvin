package com.kavrin.marvin.data.local.dao.tv.remote_keys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kavrin.marvin.domain.model.tv.entities.remote_keys.TvPopularRemoteKeys

@Dao
interface TvPopularRemoteKeysDao {

	@Query("SELECT * FROM tv_popular_remote_table WHERE id = :id")
	suspend fun getPopularRemoteKeys(id: Int): TvPopularRemoteKeys?

	@Query("SELECT * FROM tv_popular_remote_table ORDER BY lastUpdated ASC LIMIT 1")
	suspend fun getPopularLastUpdate(): TvPopularRemoteKeys?

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun addPopularRemoteKeys(popularRemoteKeys: List<TvPopularRemoteKeys>)

	@Query("DELETE FROM tv_popular_remote_table")
	suspend fun deleteAllPopularRemoteKeys()
}