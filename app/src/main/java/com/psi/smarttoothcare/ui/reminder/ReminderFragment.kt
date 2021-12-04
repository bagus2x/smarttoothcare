package com.psi.smarttoothcare.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.psi.smarttoothcare.broadcastreceiver.ReminderReceiver
import com.psi.smarttoothcare.databinding.FragmentReminderBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ReminderFragment : Fragment() {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private lateinit var addReminderFragment: AddReminderFragment
    private lateinit var updateReminderFragment: UpdateReminderFragment
    private val reminderViewModel by viewModels<ReminderViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupReminderRecyclerView()
        setupReminderFragment()
    }

    private fun setupReminderFragment() {
        updateReminderFragment = UpdateReminderFragment()
        addReminderFragment = AddReminderFragment()
        binding.fabAddReminder.setOnClickListener {
            addReminderFragment.show(childFragmentManager, addReminderFragment.tag)
        }
    }

    private fun setupReminderRecyclerView() {
        val reminderAdapter = ReminderAdapter()
        val reminderLayoutManager = LinearLayoutManager(requireContext())

        reminderViewModel.reminders.observe(viewLifecycleOwner) {
            reminderAdapter.differ.submitList(it)
        }

        binding.rvTeethBrushingReminder.apply {
            adapter = reminderAdapter
            layoutManager = reminderLayoutManager
        }

        reminderAdapter.setOnClickUpdateButtonListener {
            val bundle = Bundle()
            bundle.putParcelable(UpdateReminderFragment.ARG_REMINDER, it)
            updateReminderFragment.arguments = bundle
            updateReminderFragment.show(childFragmentManager, updateReminderFragment.tag)
        }

        reminderAdapter.setOnClickDeleteButtonListener {
            reminderViewModel.delete(it)
            if (it.enabled) {
                ReminderReceiver.cancelReminder(requireContext(), it)
            }
        }

        reminderAdapter.setOnItemToggleListener { reminder, isChecked ->
            Timber.i(reminder.toString())
            if (isChecked) {
                ReminderReceiver.setReminder(requireActivity().applicationContext, reminder)
            } else {
                ReminderReceiver.cancelReminder(requireActivity().applicationContext, reminder)
            }
            reminderViewModel.update(reminder.copy(enabled = isChecked))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}