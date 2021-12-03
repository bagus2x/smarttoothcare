package com.psi.smarttoothcare.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Embedded(prefix = "reminder_")
    val reminder: Reminder,
    val completed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)