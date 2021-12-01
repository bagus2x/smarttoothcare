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
import com.psi.smarttoothcare.databinding.FragmentAddReminderBinding
import com.psi.smarttoothcare.model.Reminder
import com.psi.smarttoothcare.utils.makeSnackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddReminderFragment : BottomSheetDialogFragment(), TimePickerDialog.OnTimeSetListener {
    private var _binding: FragmentAddReminderBinding? = null
    private val binding get() = _binding!!
    private lateinit var timePickerDialog: TimePickerDialog
    private val reminderViewModel by viewModels<ReminderViewModel>(ownerProducer = { requireParentFragment() })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTimePicker()

        binding.btnSave.setOnClickListener {
            if (!valid()) return@setOnClickListener

            reminderViewModel.time.value?.let {
                val reminder = Reminder(
                    title = binding.etTitle.text.toString(),
                    description = binding.etDescription.text.toString(),
                    time = it
                )
                reminderViewModel.create(reminder)
                close()
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

    private fun close() {
        reminderViewModel.time.postValue(null)
        binding.etTime.setText("")
        binding.etDescription.setText("")
        binding.etTitle.setText("")
        dismiss()
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
        calendar.set(Calendar.SECOND, 0)

        val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.time)

        reminderViewModel.time.postValue(calendar.timeInMillis)
        binding.etTime.setText(time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}