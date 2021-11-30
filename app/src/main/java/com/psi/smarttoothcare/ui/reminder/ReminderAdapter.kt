package com.psi.smarttoothcare.ui.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.psi.smarttoothcare.databinding.RvItemReminderBinding
import com.psi.smarttoothcare.model.Reminder
import java.text.SimpleDateFormat
import java.util.*

class ReminderAdapter : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {
    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Reminder>() {

        override fun areItemsTheSame(old: Reminder, new: Reminder) = old.id == new.id

        override fun areContentsTheSame(old: Reminder, new: Reminder) = old == new
    })
    private var onItemClickListener: ((Reminder) -> Unit)? = null
    private var onItemLongClickListener: ((Reminder) -> Unit)? = null
    private var onItemToggleListener: ((Reminder, Boolean) -> Unit)? = null
    private var simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    inner class ViewHolder(val binding: RvItemReminderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reminder: Reminder) {
            binding.swToggle.isChecked = reminder.enabled
            binding.tvTime.text = simpleDateFormat.format(reminder.time)
            binding.tvDescription.text = reminder.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemReminderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reminder = differ.currentList[position]
        holder.bind(reminder)

        holder.binding.root.setOnClickListener {
            onItemClickListener?.let { it(reminder) }
        }

        holder.binding.root.setOnLongClickListener {
            onItemLongClickListener?.let { it(reminder) }
            true
        }

        holder.binding.swToggle.setOnCheckedChangeListener { _, isChecked ->
            onItemToggleListener?.let { it(reminder, isChecked) }
        }
    }

    fun setOnItemClickListener(listener: (Reminder) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: (Reminder) -> Unit) {
        onItemLongClickListener = listener
    }

    fun setOnItemToggleListener(listener: (Reminder, Boolean) -> Unit) {
        onItemToggleListener = listener
    }

    override fun getItemCount() = differ.currentList.size
}