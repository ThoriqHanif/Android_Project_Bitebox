package com.thrq.bitebox.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {
        @Insert
         fun insertFood(food : FoodModel)

        @Delete
         fun deleteFood(food : FoodModel)

        @Query("SELECT * FROM foods")
        fun getAllFood() : LiveData<List<FoodModel>>

        @Query("SELECT * FROM foods WHERE foodId = :id")
        fun isExit(id : String) : FoodModel
    }
