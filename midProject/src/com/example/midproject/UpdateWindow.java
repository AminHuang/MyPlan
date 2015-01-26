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

public class UpdateWindow extends Activity implements OnClickListener {
	
	private TasksDB mTasksDB;
	private Cursor mCursor;
	private EditText addText;
	private ImageButton addButton;
	private ListView taskList;
	private ListView finishList;
	
	private EditText editText;
	private Button btn_sure, btn_cancel;
	
	private LinearLayout layout;
	
	private int TASK_ID = 0;
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
		
		//���ѡ�񴰿ڷ�Χ�����������Ȼ�ȡ���㣬������ִ��onTouchEvent()��������������ط�ʱִ��onTouchEvent()��������Activity
		layout.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "��ʾ����������ⲿ�رմ��ڣ�", 
						Toast.LENGTH_SHORT).show();	
			}
		});
		//��Ӱ�ť����
		btn_cancel.setOnClickListener(this);
		btn_sure.setOnClickListener(this);
		
		Bundle bundle = this.getIntent().getExtras();
		TASK_ID = Integer.parseInt(bundle.getString("task_id"));
		value = bundle.getString("task_value");
		editText.setText(value);
		
	}
	
	//ʵ��onTouchEvent���������������Ļʱ���ٱ�Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		//finish();
		return true;
	}
	
	public void returnMain(){
		MainActivity.instance.finish();
		mIntent.setClass(UpdateWindow.this, MainActivity.class);
		mIntent.putExtras(mBundle);
		startActivity(mIntent);
		finish();
	}
	
	public void updateTask(int task_id, String tableName, String value) {
		if (value.equals("")) {
			Toast.makeText(this, "û�����ݣ�", Toast.LENGTH_SHORT).show();
			return;
		}
		
		mTasksDB.update (task_id, tableName, value);
		
	}
	
	public void update() {
		String newValue = editText.getText().toString();
		updateTask(TASK_ID, "task_new", newValue);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_sure:
			update();
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
