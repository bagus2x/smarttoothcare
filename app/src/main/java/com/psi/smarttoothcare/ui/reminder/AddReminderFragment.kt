package com.psi.smarttoothcare.ui.reminder

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.psi.smarttoothcare.databinding.FragmentAddReminderBinding
import com.psi.smarttoothcare.model.Reminder
import dagger.hilt.android.AndroidEntryPoint
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
            val reminder = Reminder(
                title = binding.etTitle.text.toString(),
                description = binding.etDescription.text.toString(),
                time = System.currentTimeMillis()
            )
            reminderViewModel.create(reminder)
        }
    }

    private fun setupTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val formatHour24 = true
        timePickerDialog = TimePickerDialog(requireContext(), this, hour, minute, formatHour24)

        binding.etTime.setOnClickListener {
            timePickerDialog.show()
        }
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)



        binding.etTime.setText("$hourOfDay : $minute")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}