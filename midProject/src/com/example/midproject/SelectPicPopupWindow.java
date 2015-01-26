package com.example.midproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class SelectPicPopupWindow extends Activity implements OnClickListener{
	
	Bundle mBundle = new Bundle();
	Intent mIntent = new Intent();
	
	private TasksDB mTasksDB;
	private Cursor mCursor;

	private Button btn_edit, btn_call, btn_finish,  btn_cancel;
	private LinearLayout layout;
	
	private int TASK_ID = 0;
	private String task_value;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_dialog);
		btn_edit = (Button) this.findViewById(R.id.btn_edit);
		btn_call = (Button) this.findViewById(R.id.btn_call);
		btn_finish = (Button) this.findViewById(R.id.btn_finish);
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		
		layout=(LinearLayout)findViewById(R.id.pop_layout);
		
		//添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
		layout.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", 
						Toast.LENGTH_SHORT).show();	
			}
		});
		//添加按钮监听
		btn_cancel.setOnClickListener(this);
		btn_call.setOnClickListener(this);
		btn_finish.setOnClickListener(this);
		btn_edit.setOnClickListener(this);
		
		mTasksDB = new TasksDB(this);
		mCursor = mTasksDB.select("task_new");
		Bundle bundle = this.getIntent().getExtras();
		TASK_ID = Integer.parseInt(bundle.getString("task_id"));
		task_value = bundle.getString("task_value");
		
	}
	
	//实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	public void goUpdate() {
		Intent mIntent = new Intent();
		mIntent.setClass(SelectPicPopupWindow.this, UpdateWindow.class);
		Bundle tBumdle = new Bundle();
		tBumdle.putString("task_id", Integer.toString(TASK_ID));
		tBumdle.putString("task_value", task_value);
		mIntent.putExtras(tBumdle);
		startActivity(mIntent);
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_edit:
			goUpdate();
			finish();
			break;
		case R.id.btn_call:	
			task_call();
			break;
		case R.id.btn_finish:
			task_finish();
			returnMain();
			break;
		case R.id.btn_cancel:	
			delete();
			returnMain();
			break;
		default:
			break;
		}
		finish();
	}
	
	public void returnMain(){
		MainActivity.instance.finish();
		mIntent.setClass(SelectPicPopupWindow.this, MainActivity.class);
		mIntent.putExtras(mBundle);
		startActivity(mIntent);
		finish();
	}
	
	public void delete() {
		if (TASK_ID == 0) {
			return ;
		}
		
		mTasksDB.delete("task_new",TASK_ID);
		MainActivity.adapter_new.notifyDataSetChanged();

	}
	
	public void task_finish() {
		delete();
		mTasksDB.insert("task_finish", task_value);
	}
	
	public void task_call() {
		Intent mIntent = new Intent();
		mIntent.setClass(SelectPicPopupWindow.this, WarnWindow.class);
		Bundle tBumdle = new Bundle();
		tBumdle.putString("task_id", Integer.toString(TASK_ID));
		tBumdle.putString("task_value", task_value);
		mIntent.putExtras(tBumdle);
		startActivity(mIntent);
	}
	
}
