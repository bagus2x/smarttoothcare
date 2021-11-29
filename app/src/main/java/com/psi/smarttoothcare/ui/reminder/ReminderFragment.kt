package com.psi.smarttoothcare.ui.reminder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.psi.smarttoothcare.R
import com.psi.smarttoothcare.databinding.FragmentReminderBinding

class ReminderFragment : Fragment() {
    private var _binding: FragmentReminderBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetDialog: AddReminderFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReminderBinding.inflate(inflater, container, false)
        bottomSheetDialog = AddReminderFragment()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddReminder.setOnClickListener {
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "bsd")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}