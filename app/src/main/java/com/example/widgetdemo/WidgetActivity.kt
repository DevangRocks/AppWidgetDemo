package com.example.widgetdemo

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity

class WidgetActivity : AppCompatActivity() {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the result to CANCELED initially
        setResult(RESULT_CANCELED)

        // Retrieve the AppWidgetId from the Intent
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        // If the AppWidgetId is invalid, close the activity
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        // Set your activity layout with two buttons
        setContentView(R.layout.activity_widget)

        // Handle Button 1 click (for Widget Layout 1)
        findViewById<Button>(R.id.btn1).setOnClickListener {
            configureWidget(1) // Widget type 1
        }

        // Handle Button 2 click (for Widget Layout 2)
        findViewById<Button>(R.id.btn2).setOnClickListener {
            configureWidget(2) // Widget type 2
        }
    }

    private fun configureWidget(widgetType: Int) {
        val appWidgetManager = AppWidgetManager.getInstance(this)

        // Create different layouts depending on the widget type
        val views = when (widgetType) {
            1 -> RemoteViews(packageName, R.layout.widget_layout_1) // Widget Layout 1
            2 -> RemoteViews(packageName, R.layout.widget_layout_2) // Widget Layout 2
            else -> RemoteViews(packageName, R.layout.widget_layout_1) // Default to layout 1
        }

        // Update the widget with the selected layout
        appWidgetManager.updateAppWidget(appWidgetId, views)

        // Return RESULT_OK and pass back the AppWidgetId
        val result = Intent()
        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_OK, result)

        // Close the activity
        finish()
    }
}


