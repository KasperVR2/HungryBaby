package com.example.hungrybaby.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hungrybaby.model.Food
import java.util.Date

@Entity(tableName = "foodintake")
data class dbFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val volume: Int,
    val dateAndTime: Date,
    val note: String = "",
)

fun dbFood.asDomainFood(): Food {
    return Food(
        this.volume,
        this.dateAndTime,
        this.note,
    )
}

fun Food.asDbFood(): dbFood {
    return dbFood(
        volume = this.volume,
        dateAndTime = this.dateAndTime,
        note = this.note,
    )
}
