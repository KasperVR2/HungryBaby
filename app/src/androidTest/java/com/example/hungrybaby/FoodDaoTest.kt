package com.example.hungrybaby

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hungrybaby.data.database.FoodDao
import com.example.hungrybaby.data.database.FoodDb
import com.example.hungrybaby.data.database.asDbFood
import com.example.hungrybaby.data.database.asDomainFood
import com.example.hungrybaby.model.Food
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FoodDaoTest {
    private lateinit var foodDao: FoodDao
    private lateinit var foodDb: FoodDb

    private var food1 = Food(80, "2023-12-25:18:00")
    private var food2 = Food(120, "2023-12-24:18:00")

    // unility functions
    private suspend fun addOneTaskToDb() {
        foodDao.insert(food1.asDbFood())
    }

    private suspend fun addTwoTasksToDb() {
        foodDao.insert(food1.asDbFood())
        foodDao.insert(food2.asDbFood())
    }

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        foodDb =
            Room.inMemoryDatabaseBuilder(context, FoodDb::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        foodDao = foodDb.foodDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        foodDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInert_insertTaskIntoDB() =
        runBlocking {
            addOneTaskToDb()
            val allItems = foodDao.getAllItems().first()
            assertEquals(allItems[0].asDomainFood(), food1)
        }

    @Test
    @Throws(Exception::class)
    fun daoGetAllTasks_returnsAllTasksFromDB() =
        runBlocking {
            addTwoTasksToDb()
            val allItems = foodDao.getAllItems().first()
            assertEquals(allItems[0].asDomainFood(), food1)
            assertEquals(allItems[1].asDomainFood(), food2)
        }
}
