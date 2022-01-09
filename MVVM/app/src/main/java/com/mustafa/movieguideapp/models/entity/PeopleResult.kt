package com.mustafa.movieguideapp.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PeopleResult(
    val ids: List<Int>,
    @PrimaryKey
    val page: Int
)