package com.psi.smarttoothcare.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)