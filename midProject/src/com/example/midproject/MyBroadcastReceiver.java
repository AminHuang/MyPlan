package com.example.midproject;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver{
	
	private int TASK_ID = 0;
	private String task_value;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		String action = intent.getAction();
		if (action.equals("AlarmAction")) {
			task_value = intent.getStringExtra("task_value");
			
			Bundle tBumdle = new Bundle();
			tBumdle.putString("task_value", task_value);

			
			Intent i=new Intent(context, AlarmActivity.class);
	        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        i.putExtras(tBumdle);
	        context.startActivity(i);
			
//			MediaPlayer mp = MediaPlayer.create(context, R.raw.test);
//			mp.start();
//			Toast.makeText(context, "It's time", Toast.LENGTH_LONG).show();
			
		}
		if (action.equals("WidgetAction")) {
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			remoteViews.setTextViewText(R.id.widget_text, intent.getStringExtra("msg"));
			
			AppWidgetManager.getInstance(context).updateAppWidget(
					new ComponentName(context.getApplicationContext(),
							MyWidgetProvider.class),remoteViews
					);
		}
		
		
		
	}

}
