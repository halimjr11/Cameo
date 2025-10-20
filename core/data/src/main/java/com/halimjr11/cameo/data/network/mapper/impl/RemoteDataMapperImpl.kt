package com.halimjr11.cameo.data.network.mapper.impl

import com.halimjr11.cameo.common.helper.DateFormatter
import com.halimjr11.cameo.common.coroutines.CoroutineDispatcherProvider
import com.halimjr11.cameo.common.orDoubleZero
import com.halimjr11.cameo.common.orFalse
import com.halimjr11.cameo.common.orZero
import com.halimjr11.cameo.data.network.mapper.RemoteDataMapper
import com.halimjr11.cameo.data.network.model.MovieCreditResponse
import com.halimjr11.cameo.data.network.model.MovieDetailResponse
import com.halimjr11.cameo.data.network.model.MovieListResponse
import com.halimjr11.cameo.domain.model.MovieDetailDomain
import com.halimjr11.cameo.domain.model.MovieDomain
import kotlinx.coroutines.withContext

class RemoteDataMapperImpl(
    private val dispatcher: CoroutineDispatcherProvider,
    private val dateFormatter: DateFormatter
) : RemoteDataMapper {
    override suspend fun mapMovieList(
        movies: List<MovieListResponse.Movie?>?
    ): List<MovieDomain> = withContext(dispatcher.io) {
        movies?.mapNotNull { movie ->
            movie?.let { item ->
                MovieDomain(
                    id = item.id.orZero(),
                    title = item.title.orEmpty(),
                    overview = item.overview.orEmpty(),
                    rating = item.voteAverage.orDoubleZero(),
                    posterUrl = item.posterPath.orEmpty(),
                    backdropUrl = item.backdropPath.orEmpty(),
                    releaseDate = item.releaseDate?.let { dateFormatter.formatDate(it) }.orEmpty(),
                    language = item.originalLanguage.orEmpty(),
                    adult = item.adult.orFalse(),
                    genreIds = item.genreIds?.mapNotNull { it.orZero() }.orEmpty()
                )
            }
        }.orEmpty()
    }

    override suspend fun mapMovieCredits(
        movies: List<MovieCreditResponse.Cast?>?
    ): List<MovieDetailDomain.CastDomain> = withContext(dispatcher.io) {
        movies?.mapNotNull {
            it?.let { cast ->
                MovieDetailDomain.CastDomain(
                    id = cast.id.orZero(),
                    name = cast.name.orEmpty(),
                    originalName = cast.originalName.orEmpty(),
                    profileUrl = cast.profilePath.orEmpty(),
                    character = cast.character.orEmpty()
                )
            }
        }.orEmpty()
    }

    override suspend fun mapMovieDetail(
        detailResponse: MovieDetailResponse?
    ): MovieDetailDomain = withContext(dispatcher.io) {
        detailResponse?.let { detail ->
            MovieDetailDomain(
                id = detail.id.orZero(),
                title = detail.title.orEmpty(),
                genres = detail.genres?.mapNotNull { it?.name }.orEmpty(),
                overview = detail.overview.orEmpty(),
                rating = detail.voteAverage.orDoubleZero(),
                posterUrl = detail.posterPath.orEmpty(),
                backdropUrl = detail.backdropPath.orEmpty(),
                runtime = dateFormatter.formatRuntime(detail.runtime.orZero()),
                releaseDate = detail.releaseDate?.let { dateFormatter.formatDate(it) }.orEmpty(),
                spokenLanguages = detail.spokenLanguages?.mapNotNull { it?.name }.orEmpty()
            )
        } ?: MovieDetailDomain()
    }

}