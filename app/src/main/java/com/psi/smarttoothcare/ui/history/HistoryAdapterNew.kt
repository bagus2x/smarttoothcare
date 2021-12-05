package com.psi.smarttoothcare.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.psi.smarttoothcare.databinding.RvItemHistoryBinding
import com.psi.smarttoothcare.model.History
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<History>() {

        override fun areItemsTheSame(old: History, new: History) = old.id == new.id

        override fun areContentsTheSame(old: History, new: History) = old == new
    })
    private var onItemToggleListener: ((History, Boolean) -> Unit)? = null
    private var simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    private var simpleTimeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    private var onClickDeleteButtonListener: ((History) -> Unit)? = null

    inner class ViewHolder(val binding: RvItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History) {
            binding.tvTitle.text = history.reminder.title
            binding.tvDescription.text = history.reminder.description
            binding.cbCompleted.isChecked = history.completed
            binding.tvTime.text = simpleTimeFormat.format(history.reminder.time)
            binding.tvDate.text = simpleDateFormat.format(history.createdAt)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = differ.currentList[position]
        holder.bind(history)

        holder.binding.cbCompleted.setOnClickListener {
            onItemToggleListener?.let { it(history, holder.binding.cbCompleted.isChecked) }
        }

        holder.binding.ibDelete.setOnClickListener {
            onClickDeleteButtonListener?.let { it(history) }
        }
    }

    fun setOnItemToggleListener(listener: (History, Boolean) -> Unit) {
        onItemToggleListener = listener
    }
    fun setOnClickDeleteButtonListener(listener: (History) -> Unit) {
        onClickDeleteButtonListener = listener
    }

    override fun getItemCount() = differ.currentList.size
}
