package com.thesubgraph.notable.data.common.serialization

import com.thesubgraph.notable.data.common.ResponseDomainMapper
import com.thesubgraph.notable.domain.model.Photo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
@Serializable
data class PhotoDto(
    @SerialName("id") val id: String?,
    @SerialName("width") val width: Long?,
    @SerialName("height") val height: Long?,
    @SerialName("color") val color: String?,
    @SerialName("description") val description: String?,
    @SerialName("urls") val urls: UrlsDto
) : ResponseDomainMapper<Photo> {
    override fun mapToDomain(): Photo {
        return Photo(
            id = id ?: "",
            width = width ?: 0,
            height = height ?: 0,
            color = color ?: "#ffffff",
            description = description ?: "",
            imageUrls = urls.mapToDomain()
        )
    }
    @OptIn(ExperimentalObjCRefinement::class)
    @HiddenFromObjC
    @Serializable
    data class UrlsDto(
        @SerialName("raw") val raw: String?,
        @SerialName("full") val full: String?,
        @SerialName("regular") val regular: String?,
        @SerialName("small") val small: String?,
        @SerialName("thumb") val thumb: String?,
    ) : ResponseDomainMapper<Photo.ImageUrls> {
        override fun mapToDomain(): Photo.ImageUrls {
            return Photo.ImageUrls(
                raw = raw ?: "",
                full = full ?: "",
                regular = regular ?: "",
                small = small ?: "",
                thumb = thumb ?: "",
            )
        }
    }
}
