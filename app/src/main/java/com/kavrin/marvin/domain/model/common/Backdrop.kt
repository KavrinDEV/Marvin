package com.kavrin.marvin.domain.model.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Backdrop(
    @SerialName("aspect_ratio")
    val aspectRatio: Double,
    @SerialName("iso_639_1")
    val iso639: String?,
    @SerialName("file_path")
    val filePath: String,
    @SerialName("height")
    val height: Int,
    @SerialName("width")
    val width: Int
)