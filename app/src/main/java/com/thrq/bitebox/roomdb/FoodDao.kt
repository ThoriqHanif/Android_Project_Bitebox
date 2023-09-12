package com.thrq.bitebox.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {

    @Insert
    suspend fun insertFood(food : FoodModel)

    @Delete
    suspend fun deleteFood(food : FoodModel)

    @Query("SELECT * FROM foods")
    fun getAllFood() : List<FoodModel>

    @Query("SELECT * FROM foods WHERE foodId = :id")
    fun isExit(id : String) : FoodModel
}