package com.psi.smarttoothcare.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.psi.smarttoothcare.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyViewModel by viewModels<HistoryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historyAdapter = HistoryAdapter()

        historyViewModel.histories.observe(viewLifecycleOwner) {
            historyAdapter.differ.submitList(it)
        }

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }

        historyAdapter.setOnItemToggleListener { history, isChecked ->
            historyViewModel.update(history.copy(completed = isChecked))
        }

        historyAdapter.setOnClickDeleteButtonListener {
            val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            alert.setTitle("Delete history")
            alert.setMessage("Are you sure you want to delete?")
            alert.setPositiveButton("Yes") { _, _ ->
                historyViewModel.delete(it)
            }
            alert.setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            alert.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}