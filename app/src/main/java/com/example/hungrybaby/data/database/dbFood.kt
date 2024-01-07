package com.example.hungrybaby.data.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.hungrybaby.model.Food

@Entity(tableName = "foodintake", indices = [Index(value = ["dateAndTime"], unique = true)])
data class dbFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val volume: Int,
    val dateAndTime: String,
)

fun dbFood.asDomainFood(): Food {
    return Food(
        this.volume,
        this.dateAndTime,
    )
}

fun Food.asDbFood(): dbFood {
    return dbFood(
        volume = this.volume,
        dateAndTime = this.dateAndTime,
    )
}
