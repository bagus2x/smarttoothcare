package com.psi.smarttoothcare.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.psi.smarttoothcare.model.History
import com.psi.smarttoothcare.model.Reminder

@Database(entities = [Reminder::class, History::class], version = 1)
abstract class STCDatabase : RoomDatabase() {

    abstract fun getReminderDAO(): ReminderDAO

    abstract fun getHistoryDAO(): HistoryDAO
}