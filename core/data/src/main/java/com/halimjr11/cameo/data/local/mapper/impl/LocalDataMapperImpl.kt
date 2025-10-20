package com.halimjr11.cameo.data.local.mapper.impl

import com.halimjr11.cameo.common.coroutines.CoroutineDispatcherProvider
import com.halimjr11.cameo.data.local.mapper.LocalDataMapper
import com.halimjr11.cameo.data.local.model.MovieEntity
import com.halimjr11.cameo.domain.model.MovieDomain
import kotlinx.coroutines.withContext

class LocalDataMapperImpl(
    private val dispatcher: CoroutineDispatcherProvider
) : LocalDataMapper {
    override suspend fun mapFavoriteEntityToDomain(
        entity: MovieEntity
    ): MovieDomain = withContext(dispatcher.io) {
        entity.let {
            MovieDomain(
                id = it.id,
                title = it.title,
                posterUrl = it.posterUrl,
                backdropUrl = it.backdropUrl,
                rating = it.rating,
                releaseDate = it.releaseDate,
                overview = it.overview,
                adult = it.adult
            )
        }
    }

    override suspend fun mapFavoriteDomainToEntity(
        domain: MovieDomain
    ): MovieEntity = withContext(dispatcher.io) {
        domain.let {
            MovieEntity(
                id = it.id,
                title = it.title,
                posterUrl = it.posterUrl,
                backdropUrl = it.backdropUrl,
                rating = it.rating,
                releaseDate = it.releaseDate,
                overview = it.overview,
                adult = it.adult
            )
        }
    }
}