package com.psi.smarttoothcare.repository

import com.psi.smarttoothcare.model.Reminder
import com.psi.smarttoothcare.model.local.ReminderDAO
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderRepository @Inject constructor(private val reminderDAO: ReminderDAO) {

    fun observeReminders() = reminderDAO.observeReminders()

    fun observeReminder(reminderId: Int) = reminderDAO.observeReminder(reminderId)

    fun create(reminder: Reminder): Completable = Completable.fromAction {
        reminderDAO.create(reminder)
    }.subscribeOn(Schedulers.io())

    fun delete(reminder: Reminder): Completable = Completable.fromAction {
        reminderDAO.delete(reminder)
    }.subscribeOn(Schedulers.io())
}