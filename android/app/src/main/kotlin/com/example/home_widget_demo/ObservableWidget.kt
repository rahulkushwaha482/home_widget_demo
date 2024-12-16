package com.example.home_widget_demo
import HomeWidgetGlanceWidgetReceiver


class ObservableWidget : HomeWidgetGlanceWidgetReceiver<ObservableAppWidget>() {

    override val glanceAppWidget: ObservableAppWidget
        get() = ObservableAppWidget()
}