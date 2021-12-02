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
    private var onItemClickListener: ((History) -> Unit)? = null
    private var onItemLongClickListener: ((History) -> Unit)? = null
    private var onItemToggleListener: ((History, Boolean) -> Unit)? = null
    private var simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    inner class ViewHolder(val binding: RvItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History) {
            binding.tvName.text = history.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = differ.currentList[position]
        holder.bind(history)

        holder.binding.root.setOnClickListener {
            onItemClickListener?.let { it(history) }
        }

        holder.binding.root.setOnLongClickListener {
            onItemLongClickListener?.let { it(history) }
            true
        }
//
//        holder.binding.swToggle.setOnCheckedChangeListener { _, isChecked ->
//            onItemToggleListener?.let { it(history, isChecked) }
//        }
    }

    fun setOnItemClickListener(listener: (History) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: (History) -> Unit) {
        onItemLongClickListener = listener
    }

    fun setOnItemToggleListener(listener: (History, Boolean) -> Unit) {
        onItemToggleListener = listener
    }

    override fun getItemCount() = differ.currentList.size
}