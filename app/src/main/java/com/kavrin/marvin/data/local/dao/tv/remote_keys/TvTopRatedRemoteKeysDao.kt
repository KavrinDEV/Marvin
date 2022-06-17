package com.kavrin.marvin.data.local.dao.tv.remote_keys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kavrin.marvin.domain.model.tv.entities.remote_keys.TvTopRatedRemoteKeys

@Dao
interface TvTopRatedRemoteKeysDao {

	@Query("SELECT * FROM tv_top_rated_remote_table WHERE tvTopRatedId = :id")
	suspend fun getTopRatedRemoteKeys(id: Int): TvTopRatedRemoteKeys

	@Insert
	suspend fun addTopRatedRemoteKeys(topRatedRemoteKeys: List<TvTopRatedRemoteKeys>)

	@Query("DELETE FROM tv_top_rated_remote_table")
	suspend fun deleteAllTopRatedRemoteKeys()
}