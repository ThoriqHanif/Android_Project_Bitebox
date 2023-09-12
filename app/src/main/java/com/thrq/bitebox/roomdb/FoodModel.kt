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
    @ColumnInfo("foodName")
    val foodName: String? = "",
    @ColumnInfo("foodImages")
    val foodImages: String? = "",
    @ColumnInfo("foodPrice")
    val foodPrice: String? = "",
)
