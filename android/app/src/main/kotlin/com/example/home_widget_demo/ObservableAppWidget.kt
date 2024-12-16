package com.example.home_widget_demo

import HomeWidgetGlanceState
import HomeWidgetGlanceStateDefinition
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Alignment.Companion.Center
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.fillMaxSize
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.Text
import androidx.glance.text.TextAlign.Companion.Center
import es.antonborri.home_widget.HomeWidgetBackgroundIntent

class ObservableAppWidget : GlanceAppWidget() {

    override  val  stateDefinition : GlanceStateDefinition<*>?
        get() = HomeWidgetGlanceStateDefinition()


    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            GLanceContent(context, currentState())
        }
    }

    @Composable
    private  fun  GLanceContent(context: Context,currentState: HomeWidgetGlanceState){
          val prefs = currentState.preferences
          val counter = prefs.getInt("counter",0);
          val dash = prefs.getString("dash",null)

         Box(modifier = GlanceModifier.background(Color.White).fillMaxSize()) {

             Column(verticalAlignment = Alignment.CenterVertically
             , horizontalAlignment = Alignment.CenterHorizontally
             ) {

                 Text(
                     text = counter.toString()
                 )
                 dash?.let{
                     val bitMap = BitmapFactory.decodeFile(dash)
                    Box(modifier = GlanceModifier.clickable(
                        onClick = actionRunCallback<IntractiveActionView>()
                    )) {
                         Image(
                             modifier = GlanceModifier.fillMaxSize(),

                             provider = ImageProvider(bitMap),
                             contentDescription = "",
                             contentScale = ContentScale.Fit
                         )
                     }

                 }
             }

         }
    }

}

class  IntractiveActionView : ActionCallback{
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
         var backGroundIntent = HomeWidgetBackgroundIntent.getBroadcast(context,
             uri = Uri.parse("observable://increment"))
        backGroundIntent.send()
    }

}