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
    private var onClickDeleteButtonListener: ((Reminder) -> Unit)? = null
    private var onClickUpdateButtonListener: ((Reminder) -> Unit)? = null
    private var onItemToggleListener: ((Reminder, Boolean) -> Unit)? = null
    private var simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    inner class ViewHolder(val binding: RvItemReminderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reminder: Reminder) {
            binding.swToggle.isChecked = reminder.enabled
            binding.tvTime.text = simpleDateFormat.format(reminder.time)
            binding.tvTitle.text = reminder.title
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

        holder.binding.ibDelete.setOnClickListener {
            onClickDeleteButtonListener?.let { it(reminder) }
        }

        holder.binding.ibUpdate.setOnClickListener {
            onClickUpdateButtonListener?.let { it(reminder) }
        }

        holder.binding.swToggle.setOnClickListener {
            onItemToggleListener?.let { it(reminder, holder.binding.swToggle.isChecked) }
        }
    }

    fun setOnClickDeleteButtonListener(listener: (Reminder) -> Unit) {
        onClickDeleteButtonListener = listener
    }

    fun setOnClickUpdateButtonListener(listener: (Reminder) -> Unit) {
        onClickUpdateButtonListener = listener
    }

    fun setOnItemToggleListener(listener: (Reminder, Boolean) -> Unit) {
        onItemToggleListener = listener
    }

    override fun getItemCount() = differ.currentList.size
}