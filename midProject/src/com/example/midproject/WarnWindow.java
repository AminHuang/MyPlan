package com.example.midproject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
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

public class WarnWindow extends Activity {
	
	
	Button setDateB, setTimeB, btn_ok;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	TextView finishDate, finishTime, plan_content;
	Calendar calendar, alarmCalendar;
	
	int TASK_ID;
	String task_value;
	
	Bundle mBundle = new Bundle();
	Intent mIntent = new Intent();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.warn);
		
		findView();
		bindButton();
		
		calendar = Calendar.getInstance();
        alarmCalendar = Calendar.getInstance();
		alarmCalendar.setTimeInMillis(System.currentTimeMillis());
		
		Bundle bundle = this.getIntent().getExtras();
		TASK_ID = Integer.parseInt(bundle.getString("task_id"));
		task_value = bundle.getString("task_value");
		plan_content.setText(task_value);
		
		
	}
	
	private void findView() {
		btn_ok=(Button)findViewById(R.id.warn_ok);
        setDateB=(Button)findViewById(R.id.setupdate);
        setTimeB=(Button)findViewById(R.id.setuptime);
        finishDate=(TextView)findViewById(R.id.finishdate);
        finishTime=(TextView)findViewById(R.id.finishtime);
        plan_content=(TextView)findViewById(R.id.plan_content);
    }
	
	private void bindButton() {
		btn_ok.setOnClickListener(new myOnClickListener());
    	setDateB.setOnClickListener(new myOnClickListener());
    	setTimeB.setOnClickListener(new myOnClickListener());
    }
	
	//实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		//finish();
		return true;
	}
	
	public void returnMain(){
		MainActivity.instance.finish();
		mIntent.setClass(WarnWindow.this, MainActivity.class);
		mIntent.putExtras(mBundle);
		startActivity(mIntent);
		finish();
	}
	
	public class myOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.setupdate:
				setupDate();
				break;
			case R.id.setuptime:
				setupTime();
				break;
			case R.id.warn_ok:
				if (!finishDate.getText().equals("") && !finishTime.getText().equals("")){
					setAlarm();
					returnMain();
				}
				break;
			}
		}
    }
	
	private void setupDate() {
    	new DatePickerDialog(WarnWindow.this, 
    			new DatePickerDialog.OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						finishDate.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
						alarmCalendar.set(year, monthOfYear, dayOfMonth);
					}
				}, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
	
	private void setupTime() {
    	new TimePickerDialog(WarnWindow.this,
    			new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						alarmCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						alarmCalendar.set(Calendar.MINUTE, minute);
						alarmCalendar.set(Calendar.SECOND, 0);
						alarmCalendar.set(Calendar.MILLISECOND, 0);

						finishTime.setText(hourOfDay+":"+minute);
					}
    		
    	}, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    	
    }
	
	private void setAlarm () {
		Intent intent = new Intent("AlarmAction");
		intent.putExtra("task_value", task_value);
		
		// 最后一个参数解决数据传送过程中信息丢失问题
		PendingIntent pendingIntent = PendingIntent.getBroadcast(WarnWindow.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);				
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), pendingIntent);
		am.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis()+(10*1000),
				(24*60*60*1000), pendingIntent);
		
    }

}
