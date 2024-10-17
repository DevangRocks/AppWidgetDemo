package com.example.widgetdemo

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews

class BluetoothUpdatingWidget : AppWidgetProvider() {

    // This method is called when the widget is created for the first time
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        // Optional: Perform any setup when the widget is first added
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            // Update the widget layout
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        // Prepare the intent to launch the configuration activity
        val configIntent = Intent(context, WidgetActivity::class.java).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Create a pending intent that will be fired when the widget is clicked
        val pendingIntent = PendingIntent.getActivity(
            context,
            appWidgetId,
            configIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set up the layout for the widget
        val views = RemoteViews(context.packageName, R.layout.widget_broadcast)
        views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent)

        // Update the widget with the new layout
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        // Handle different actions
        when (intent.action) {
            AppWidgetManager.ACTION_APPWIDGET_UPDATE -> {
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)

                if (appWidgetIds != null) {
                    onUpdate(context, appWidgetManager, appWidgetIds)
                }
            }
        }
    }
}










