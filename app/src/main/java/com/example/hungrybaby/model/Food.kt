package com.example.hungrybaby.model

import java.util.Date

data class Food(
    var volume: Int,
    var dateAndTime: Date,
    var note: String = "",
)
