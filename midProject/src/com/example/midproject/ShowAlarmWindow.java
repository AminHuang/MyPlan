package com.example.midproject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ShowAlarmWindow extends Activity {
	
	private int TASK_ID = 0;
	private String task_value;
	
	Button btn_ok;
	TextView plan_content;
	
	Context context;
	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.warn);
		
		findView();
		bindButton();
		
		context = this;
		
		play();
		
//		Bundle bundle = this.getIntent().getExtras();
//		TASK_ID = Integer.parseInt(bundle.getString("task_id"));
//		task_value = bundle.getString("task_value");
//		plan_content.setText(task_value);
		
	}
	
	private void findView() {
		btn_ok=(Button)findViewById(R.id.alarm_ok);
        plan_content=(TextView)findViewById(R.id.alarm_plan_content);
    }
	
	private void bindButton() {
		btn_ok.setOnClickListener(new myOnClickListener());
		plan_content.setOnClickListener(new myOnClickListener());
    }
	
	//实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		//finish();
		return true;
	}
	
	
	public class myOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.alarm_ok:
				stop();
				finish();
				break;
			}
		}
    }
	
	private void play() {
		mp = MediaPlayer.create(context, R.raw.test);
		mp.start();
		Toast.makeText(context, "It's time", Toast.LENGTH_LONG).show();
	}
	
	private void stop() {
		mp.stop();
		
	}
	

}
