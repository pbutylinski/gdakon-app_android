package org.eurofurence.connavigator.gcm

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.eurofurence.connavigator.util.extensions.logd
import org.eurofurence.connavigator.util.extensions.objects
import java.util.*

/**
 * Created by David on 5/15/2016.
 */
class NotificationPublisher : BroadcastReceiver() {
    companion object {
        val NOTIFICATION_ID = "notification-id"
        val NOTIFICATION = "notification"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)

        logd{ "NOT: notification ID is $id"}
        notificationManager.notify(id, notification)
    }
}