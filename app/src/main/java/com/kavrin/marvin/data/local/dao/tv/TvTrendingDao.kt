package com.kavrin.marvin.data.local.dao.tv

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.tv.entities.TvTrending
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndTrending

@Dao
interface TvTrendingDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTrending(tvTrending: TvTrending)

	@Query("DELETE FROM tv_top_rated_table")
	suspend fun deleteAllTrending()

	@Transaction
	@Query("SELECT * FROM tv_table")
	fun getTvAndTrending(): PagingSource<Int, TvAndTrending>

	@Transaction
	@Query("SELECT * FROM tv_table,tv_trending_remote_table WHERE tvId = tvTrendingId ORDER BY popularity DESC LIMIT 5")
	fun getCarouselTvAndTrending(): PagingSource<Int, TvAndTrending>

	@Transaction
	@Query("SELECT * FROM tv_table, tv_trending_table WHERE tvId = trendingTvId LIMIT 8")
	fun getHomeTvAndTrending(): PagingSource<Int, TvAndTrending>
}