package com.plbertheau.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class MovieResponse (
    @field:SerializedName("backdrop") val backdrop: String,
    @field:SerializedName("cast") val cast: List<String>,
    @field:SerializedName("classification") val classification: String,
    @field:SerializedName("genres") val genres: List<String>,
    @field:SerializedName("id") @PrimaryKey val id: String,
    @field:SerializedName("imdb_rating") val imdb_rating: Double,
    @field:SerializedName("length") val length: String,
    @field:SerializedName("overview") val overview: String,
    @field:SerializedName("poster") val poster: String,
    @field:SerializedName("released_on") val released_on: String,
    @field:SerializedName("title") val title: String
)

enum class Genres(val genre: String) {
    ANIMATION("Animation"),
    ADVENTURE("Adventure"),
    FAMILY("Family"),
    DRAMA("Drama"),
    ROMANCE("Romance"),
    ACTION("Action"),
    CRIME("Crime"),
    BIOGRAPHY("Biography"),
    HISTORY("History"),
    SCIFI("Sci-Fi"),
    THRILLER("Thriller"),
    WAR("War"),
    MYSTERY("Mystery")
}
