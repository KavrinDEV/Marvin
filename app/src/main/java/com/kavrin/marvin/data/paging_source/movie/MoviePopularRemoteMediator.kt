package com.kavrin.marvin.data.paging_source.movie

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kavrin.marvin.data.local.MarvinDatabase
import com.kavrin.marvin.data.remote.TMDBMovieService
import com.kavrin.marvin.domain.model.movie.entities.MoviePopular
import com.kavrin.marvin.domain.model.movie.entities.relations.MovieAndPopular
import com.kavrin.marvin.domain.model.movie.entities.remote_keys.MoviePopularRemoteKeys
import com.kavrin.marvin.util.Constants.DEFAULT_HOME_PREFETCH
import com.kavrin.marvin.util.Constants.FIRST_REQUEST_DEFAULT
import com.kavrin.marvin.util.Constants.ONE_MINUTE_IN_SECONDS
import com.kavrin.marvin.util.Constants.ONE_SECOND_IN_MILLIS
import com.kavrin.marvin.util.Constants.TWENTY_FOUR_HOURS_IN_MINUTES

class MoviePopularRemoteMediator(
    private val movieService: TMDBMovieService,
    private val marvinDatabase: MarvinDatabase,
) : RemoteMediator<Int, MovieAndPopular>() {

    private val movieDao = marvinDatabase.movieDao()
    private val moviePopularDao = marvinDatabase.moviePopularDao()
    private val moviePopularRemoteKeysDao = marvinDatabase.moviePopularRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = moviePopularRemoteKeysDao.getPopularLastUpdate()?.lastUpdated
            ?: FIRST_REQUEST_DEFAULT
        val cacheTimeout = TWENTY_FOUR_HOURS_IN_MINUTES

        val diffInMinutes =
            (currentTime - lastUpdated) / ONE_SECOND_IN_MILLIS / ONE_MINUTE_IN_SECONDS

        return if (diffInMinutes.toInt() <= cacheTimeout)
            InitializeAction.SKIP_INITIAL_REFRESH
        else
            InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieAndPopular>,
    ): MediatorResult {
        return try {

            val currentPage: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeysClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    if (state.config.prefetchDistance == DEFAULT_HOME_PREFETCH) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = movieService.getPopularMovies(page = currentPage)

            if (response.movies.isNotEmpty()) {
                marvinDatabase.withTransaction {

                    if (loadType == LoadType.REFRESH) {
                        moviePopularDao.deleteAllPopular()
                        moviePopularRemoteKeysDao.deleteAllPopularRemoteKeys()
                    }

                    val prevPage = when (response.page) {
                        1 -> null
                        else -> response.page - 1
                    }

                    val nextPage = when (response.page) {
                        response.totalPages -> null
                        else -> response.page + 1
                    }

                    val lastUpdate = System.currentTimeMillis()
                    val keys = response.movies.map { movie ->
                        MoviePopularRemoteKeys(
                            moviePopularId = movie.movieId,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = lastUpdate
                        )
                    }
                    moviePopularRemoteKeysDao.addPopularRemoteKeys(popularRemoteKeys = keys)

                    val popular = response.movies.map { movie ->
                        MoviePopular(popularMovieId = movie.movieId)
                    }
                    moviePopularDao.insertPopular(moviePopular = popular)

                    movieDao.insertMovie(movie = response.movies)

                }
            }


            MediatorResult.Success(endOfPaginationReached = response.page == response.totalPages)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysClosestToCurrentPosition(
        state: PagingState<Int, MovieAndPopular>,
    ): MoviePopularRemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(anchorPosition = position)?.moviePopular?.id?.let { id ->
                moviePopularRemoteKeysDao.getPopularRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieAndPopular>,
    ): MoviePopularRemoteKeys? {
        return state.pages.firstOrNull { page ->
            page.data.isNotEmpty()
        }?.data?.firstOrNull()
            ?.let { movieAndPopular ->
                movieAndPopular.moviePopular?.let { moviePopular ->
                    moviePopular.id.let { id ->
                        moviePopularRemoteKeysDao.getPopularRemoteKeys(id = id)
                    }
                }
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieAndPopular>,
    ): MoviePopularRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()
            ?.let { movieAndPopular ->
                movieAndPopular.moviePopular?.let { moviePopular ->
                    moviePopular.id.let { id ->
                        moviePopularRemoteKeysDao.getPopularRemoteKeys(id = id)
                    }
                }
            }
    }


}