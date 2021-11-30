package com.psi.smarttoothcare.ui.reminder

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.psi.smarttoothcare.databinding.FragmentUpdateReminderBinding
import com.psi.smarttoothcare.model.Reminder
import com.psi.smarttoothcare.utils.makeSnackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UpdateReminderFragment : BottomSheetDialogFragment(), TimePickerDialog.OnTimeSetListener {
    private var _binding: FragmentUpdateReminderBinding? = null
    private val binding get() = _binding!!
    private lateinit var timePickerDialog: TimePickerDialog
    private val reminderViewModel by viewModels<ReminderViewModel>(ownerProducer = { requireParentFragment() })

    companion object {
        const val ARG_REMINDER = "arg_reminder"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUpdateReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTimePicker()

        arguments?.getParcelable<Reminder>(ARG_REMINDER)?.let {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it.time
            binding.etTitle.setText(it.title)
            binding.etTime.setText(simpleAmPm(calendar))
            binding.etDescription.setText(it.description)
            reminderViewModel.time.postValue(it.time)
        }

        binding.btnUpdate.setOnClickListener {
            if (!valid()) return@setOnClickListener

            arguments?.takeIf { it.containsKey(ARG_REMINDER) }?.apply {
                getParcelable<Reminder>(ARG_REMINDER)?.let { reminder ->
                    reminderViewModel.time.value?.let { time ->
                        val newReminder = reminder.copy(
                            title = binding.etTitle.text.toString(),
                            description = binding.etDescription.text.toString(),
                            time = time
                        )
                        reminderViewModel.update(newReminder)
                        dismiss()
                    }
                }
            }
        }
    }

    private fun valid(): Boolean {
        if (binding.etTitle.text == null || binding.etTitle.text.toString() == "" || reminderViewModel.time.value == null) {
            makeSnackbar("Title and time are required", Snackbar.LENGTH_SHORT)?.show()
            return false
        }

        return true
    }

    private fun setupTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        timePickerDialog = TimePickerDialog(requireContext(), this, hour, minute, false)

        binding.etTime.setOnClickListener {
            timePickerDialog.show()
        }
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val time = simpleAmPm(calendar)

        reminderViewModel.time.postValue(calendar.timeInMillis)
        binding.etTime.setText(time)
    }

    private fun simpleAmPm(calendar: Calendar): String {
        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}