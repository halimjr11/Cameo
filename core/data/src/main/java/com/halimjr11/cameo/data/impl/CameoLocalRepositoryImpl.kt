package com.halimjr11.cameo.data.impl

import com.halimjr11.cameo.common.coroutines.CoroutineDispatcherProvider
import com.halimjr11.cameo.data.local.database.MovieDao
import com.halimjr11.cameo.data.local.mapper.LocalDataMapper
import com.halimjr11.cameo.domain.model.MovieDomain
import com.halimjr11.cameo.domain.repository.CameoLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext

class CameoLocalRepositoryImpl(
    private val movieDao: MovieDao,
    private val localDataMapper: LocalDataMapper,
    private val dispatcher: CoroutineDispatcherProvider
) : CameoLocalRepository {
    override suspend fun getFavoriteMovies(): Flow<List<MovieDomain>> = withContext(dispatcher.io) {
        val movies = movieDao.getFavoriteMovies()
        movies.transform {
            val mapped = it.map { movieEntity ->
                localDataMapper.mapFavoriteEntityToDomain(movieEntity)
            }
            emit(mapped)
        }
    }

    override suspend fun checkFavoriteMovie(movieId: Int): Boolean = withContext(dispatcher.io) {
        val movie = movieDao.getMovieById(movieId)
        movie != null
    }

    override suspend fun insertFavoriteMovie(movie: MovieDomain) = withContext(dispatcher.io) {
        val entity = localDataMapper.mapFavoriteDomainToEntity(movie)
        movieDao.insertFavoriteMovie(entity)
    }

    override suspend fun deleteFavoriteMovie(movie: MovieDomain) = withContext(dispatcher.io) {
        val entity = localDataMapper.mapFavoriteDomainToEntity(movie)
        movieDao.deleteFavoriteMovie(entity)
    }

    override suspend fun clearFavoriteMovies() = withContext(dispatcher.io) {
        movieDao.clearFavoriteMovies()
    }
}