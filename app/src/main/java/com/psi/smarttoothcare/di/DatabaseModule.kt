package com.psi.smarttoothcare.di

import android.content.Context
import androidx.room.Room
import com.psi.smarttoothcare.model.local.HistoryDAO
import com.psi.smarttoothcare.model.local.ReminderDAO
import com.psi.smarttoothcare.model.local.STCDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideSTCDatabase(@ApplicationContext context: Context): STCDatabase {
        return Room
            .databaseBuilder(context, STCDatabase::class.java, "stc.db")
            .createFromAsset("smtc.db")
            .build()
    }

    @Provides
    fun provideReminderDAO(stcDatabase: STCDatabase): ReminderDAO {
        return stcDatabase.getReminderDAO()
    }

    @Provides
    fun provideHistoryDAO(stcDatabase: STCDatabase): HistoryDAO {
        return stcDatabase.getHistoryDAO()
    }
}