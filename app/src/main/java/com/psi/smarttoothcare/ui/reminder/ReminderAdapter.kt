package com.psi.smarttoothcare.ui.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.psi.smarttoothcare.databinding.RvItemReminderBinding
import com.psi.smarttoothcare.model.Reminder
import java.util.*

class ReminderAdapter : RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {
    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Reminder>() {

        override fun areItemsTheSame(old: Reminder, new: Reminder) = old.id == new.id

        override fun areContentsTheSame(old: Reminder, new: Reminder) = old == new
    })
    private val calendar = Calendar.getInstance()
    private var onItemClickListener: ((Reminder) -> Unit)? = null

    inner class ViewHolder(val binding: RvItemReminderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reminder: Reminder) {
            binding.swToggle.isChecked = reminder.enabled
            binding.tvTime.text = time(reminder.time)
            binding.tvDescription.text = reminder.description
        }

        private fun time(date: Long): String {
            calendar.timeInMillis = date
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)

            return "$hour : $minute"
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
    }

    override fun getItemCount() = differ.currentList.size
}