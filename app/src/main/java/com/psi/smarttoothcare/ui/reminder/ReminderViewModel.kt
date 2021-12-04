package com.psi.smarttoothcare.ui.reminder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.psi.smarttoothcare.model.Reminder
import com.psi.smarttoothcare.repository.ReminderRepository
import com.psi.smarttoothcare.utils.plusAssign
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val reminderRepository: ReminderRepository) : ViewModel() {
    val reminders = reminderRepository.observeReminders()
    val time = MutableLiveData<Long>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun create(reminder: Reminder) {
        compositeDisposable += reminderRepository.create(reminder).observeOn(AndroidSchedulers.mainThread()).subscribe({
        }) {
            Timber.e(it)
        }
    }

    fun delete(reminder: Reminder) {
        compositeDisposable += reminderRepository.delete(reminder).observeOn(AndroidSchedulers.mainThread()).subscribe({
        }) {
            Timber.e(it)
        }
    }

    fun update(reminder: Reminder) {
        compositeDisposable += reminderRepository.update(reminder).observeOn(AndroidSchedulers.mainThread()).subscribe({
        }) {
            Timber.e(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}