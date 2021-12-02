package com.psi.smarttoothcare.broadcastreceiver

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.psi.smarttoothcare.MainActivity
import com.psi.smarttoothcare.R
import com.psi.smarttoothcare.model.History
import com.psi.smarttoothcare.model.Reminder
import com.psi.smarttoothcare.repository.HistoryRepository
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {
    @Inject
    lateinit var historyRepository: HistoryRepository

    companion object {
        private const val NOTIFICATION_ID = 100
        private const val REMINDER_TITLE = "key_title"
        private const val REMINDER_DESCRIPTION = "key_description"

        @SuppressLint("UnspecifiedImmutableFlag")
        fun setReminder(context: Context, reminder: Reminder) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, ReminderReceiver::class.java)
            intent.putExtra(REMINDER_TITLE, reminder.title)
            intent.putExtra(REMINDER_DESCRIPTION, reminder.description)

            val pendingIntent = PendingIntent.getBroadcast(context, reminder.id, intent, 0)

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                reminder.time,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

        @SuppressLint("UnspecifiedImmutableFlag")
        fun cancelReminder(context: Context, reminder: Reminder) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, ReminderReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, reminder.id, intent, 0)
            alarmManager.cancel(pendingIntent)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val title = """
         aaaa
            ${intent.getStringExtra(REMINDER_TITLE)}
        """.trimIndent()
        val description = """
        bbb
            ${intent.getStringExtra(REMINDER_DESCRIPTION)}
        """.trimIndent()

        Timber.i("Dipanggil $title")

        historyRepository.create(History(name = title)).subscribe({
            Timber.i("Sukses")
        }) {
            Timber.e("Error nih aduh")
            it.printStackTrace()
        }

        showAlarmNotification(context, title, description)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showAlarmNotification(context: Context, title: String, message: String) {
        val channelId = "Reminder_Channel"
        val channelName = "Reminder channel"
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val pendingIntentDeepLink = NavDeepLinkBuilder(context)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_main)
            .setDestination(R.id.historyFragment)
            .createPendingIntent()

        val notification = builder
            .setContentIntent(pendingIntentDeepLink)
            .build()
        notificationManagerCompat.notify(NOTIFICATION_ID, notification)
    }
}