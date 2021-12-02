package com.psi.smarttoothcare.ui.history

import androidx.lifecycle.ViewModel
import com.psi.smarttoothcare.model.History
import com.psi.smarttoothcare.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val historyRepository: HistoryRepository) : ViewModel() {
    val histories = historyRepository.observeHistories()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun create(history: History) {
        Timber.i("Start")
        compositeDisposable.add(historyRepository.create(history).observeOn(AndroidSchedulers.mainThread()).subscribe({
            Timber.i("Sukses")
        }) {
            Timber.e(it)
        })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}