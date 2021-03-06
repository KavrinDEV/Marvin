package com.kavrin.marvin.data.local.dao.tv

import androidx.paging.PagingSource
import androidx.room.*
import com.kavrin.marvin.domain.model.tv.entities.TvPopular
import com.kavrin.marvin.domain.model.tv.entities.relations.TvAndPopular

@Dao
interface TvPopularDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertPopular(tvPopular: List<TvPopular>)

	@Query("DELETE FROM tv_popular_table")
	suspend fun deleteAllPopular()

	@Transaction
	@RewriteQueriesToDropUnusedColumns
	@Query("SELECT * FROM tv_table, tv_popular_table WHERE tvId = popularTvId ORDER BY tv_popular_table.id ASC")
	fun getTvAndPopular(): PagingSource<Int, TvAndPopular>

	@Transaction
	@RewriteQueriesToDropUnusedColumns
	@Query("SELECT * FROM tv_table, tv_popular_table WHERE tvId = popularTvId ORDER BY tv_popular_table.id ASC LIMIT 8")
	fun getHomeTvAndPopular(): PagingSource<Int, TvAndPopular>
}