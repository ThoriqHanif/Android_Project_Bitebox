package com.thrq.bitebox.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull


@Entity(tableName = "foods")
data class FoodModel(
    @PrimaryKey
    @Nonnull
    val foodId: String,
    @ColumnInfo(name = "foodName")
    val foodName: String? = "",
    @ColumnInfo(name = "foodImages")
    val foodImages: String? = "",
    @ColumnInfo(name ="foodPrice")
    val foodPrice: String? = "",
)