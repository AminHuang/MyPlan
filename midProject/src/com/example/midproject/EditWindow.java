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

public class EditWindow extends Activity implements OnClickListener {
	
	private TasksDB mTasksDB;
	private Cursor mCursor;
	private EditText addText;
	private ImageButton addButton;
	private ListView taskList;
	private ListView finishList;
	
	private EditText editText;
	private Button btn_sure, btn_cancel;
	
	private LinearLayout layout;
	
	private String value;
	
	Bundle mBundle = new Bundle();
	Intent mIntent = new Intent();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		
		mTasksDB = new TasksDB(this);
		mCursor = mTasksDB.select("task_new");
		taskList = (ListView)findViewById(R.id.list_task);
		
		btn_sure = (Button) this.findViewById(R.id.edit_sure);
		btn_sure.getBackground().setAlpha(10);
		btn_cancel = (Button) this.findViewById(R.id.edit_cancel);
		btn_cancel.getBackground().setAlpha(10);
		editText = (EditText)this.findViewById(R.id.editTextContext);
		
		layout=(LinearLayout)findViewById(R.id.edit_layout);
		
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
		btn_sure.setOnClickListener(this);
		
	}
	
	//实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		//finish();
		return true;
	}
	
	public void returnMain(){
		MainActivity.instance.finish();
		mIntent.setClass(EditWindow.this, MainActivity.class);
		mIntent.putExtras(mBundle);
		startActivity(mIntent);
		finish();
	}
	
	public void addTask(String tableName, String value) {
		if (value.equals("")) {
			Toast.makeText(this, "没有内容！", Toast.LENGTH_SHORT).show();
			return;
		}
		
		mTasksDB.insert(tableName, value);
		
	}
	
	public void add() {
		String newValue = editText.getText().toString();
		addTask("task_new",newValue);
		
		Intent intent = new Intent("WidgetAction");
		intent.putExtra("msg", newValue);
		sendBroadcast(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_sure:
			add();
			returnMain();
			break;
		case R.id.edit_cancel:
			finish();
			break;
		default:
			break;
		}
		finish();
	}

}
