package com.psi.smarttoothcare.model.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.psi.smarttoothcare.model.History

@Dao
interface HistoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(history: History)

    @Query("SELECT * FROM History WHERE id = :historyId")
    fun observeHistory(historyId: Int): LiveData<History>

    @Query("SELECT * FROM History ORDER BY createdAt DESC")
    fun observeHistories(): LiveData<List<History>>

    @Update
    fun update(history: History)

    @Delete
    fun delete(history: History)
}