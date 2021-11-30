package com.psi.smarttoothcare.ui.reminder

import androidx.lifecycle.ViewModel
import com.psi.smarttoothcare.model.Reminder
import com.psi.smarttoothcare.repository.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val reminderRepository: ReminderRepository) : ViewModel() {
    val reminders = reminderRepository.observeReminders()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun create(reminder: Reminder) {
        compositeDisposable.add(reminderRepository.create(reminder).subscribe({

        }) {
            Timber.e(it)
        })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}