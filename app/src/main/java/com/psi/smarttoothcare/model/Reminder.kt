package com.psi.smarttoothcare.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Reminder")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String?,
    val time: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
