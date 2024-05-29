package com.example.foodish

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FoodItem::class], version = 1, exportSchema = false)
abstract class FavoriteFoodDatabase : RoomDatabase() {
    abstract fun foodDatabaseDao(): FoodDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteFoodDatabase? = null
        fun getDatabase(context: Context): FavoriteFoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteFoodDatabase::class.java,
                    "food_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Dao
interface FoodDatabaseDao {
    @Query("SELECT * FROM food_items where isFavorite = 1")
    suspend fun getFavoriteFoodItems(): List<FoodItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFood(foodItem: FoodItem): Long

    @Delete
    suspend fun deleteFood(foodItem: FoodItem): Int
}