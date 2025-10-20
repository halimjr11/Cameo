package com.halimjr11.cameo.data.local.mapper

import com.halimjr11.cameo.data.local.model.MovieEntity
import com.halimjr11.cameo.domain.model.MovieDetailDomain
import com.halimjr11.cameo.domain.model.MovieDomain

interface LocalDataMapper {
    suspend fun mapFavoriteEntityToDomain(entity: MovieEntity): MovieDomain
    suspend fun mapFavoriteDomainToEntity(domain: MovieDetailDomain): MovieEntity
}