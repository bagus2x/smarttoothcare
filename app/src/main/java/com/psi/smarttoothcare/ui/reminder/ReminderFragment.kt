package com.psi.smarttoothcare.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.psi.smarttoothcare.databinding.FragmentReminderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderFragment : Fragment() {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetDialog: AddReminderFragment
    private val reminderViewModel by viewModels<ReminderViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        bottomSheetDialog = AddReminderFragment()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBottomSheetDialog()
        setupReminderRecyclerView()
    }

    private fun setupBottomSheetDialog() {
        binding.fabAddReminder.setOnClickListener {
            bottomSheetDialog.show(childFragmentManager, "bsd")
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}