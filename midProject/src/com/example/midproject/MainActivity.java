package com.example.midproject;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity 
  implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
	
	private TasksDB mTasksDB;
	private Cursor mCursor;
	private Cursor fCursor;
	private ImageButton addButton;
	private ImageButton searchButton;
	private EditText searchValue;
	public static  ListView taskList;
	public static  ListView finishList;
	private View fgx;
	
	public static TasksListAdapter adapter_finish;
	public static TasksListAdapter adapter_new;
	
	private int TASK_ID = 0;
	private String task_value;
	
	public static MainActivity instance = null;
	Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        instance = this;
        context = this;
        
        setUpViews();
        
    }
    
    public void setUpViews() {
		mTasksDB = new TasksDB(this);
		mCursor = mTasksDB.select("task_new");
		fCursor = mTasksDB.select("task_finish");
		
		addButton = (ImageButton)findViewById(R.id.addButton);
		searchButton = (ImageButton)findViewById(R.id.searchButton);
		searchValue = (EditText)findViewById(R.id.searchText);
		taskList = (ListView)findViewById(R.id.list_task);
		finishList = (ListView)findViewById(R.id.list_finish);
		fgx = (View)findViewById(R.id.fgx);
		fgx.getBackground().setAlpha(70);
		
		
		adapter_new = new TasksListAdapter(this, mCursor);
		taskList.setAdapter(adapter_new);
		taskList.setOnItemClickListener(this);
		
		adapter_finish = new TasksListAdapter(this, fCursor) ;
		finishList.setAdapter(adapter_finish);
		finishList.setOnItemLongClickListener(this);
		
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,EditWindow.class));
				
			}
			
		});
		
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String search_value = searchValue.getText().toString();
				if (!search_value.equals("")) {
					Intent mIntent = new Intent();
					mIntent.setClass(MainActivity.this, SearchWindow.class);
					Bundle tBumdle = new Bundle();
					tBumdle.putString("search_value", searchValue.getText().toString());
					mIntent.putExtras(tBumdle);
					startActivity(mIntent);
				}
				
			}
			
		});
		
	}
    
    
	


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
public class TasksListAdapter extends BaseAdapter {
		
		private Context mContext;
		private Cursor mCursor;

		public TasksListAdapter(Context mContext, Cursor mCursor) {
			super();
			this.mContext = mContext;
			this.mCursor = mCursor;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mCursor.getCount();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView mTextView = new TextView(mContext);
			mCursor.moveToPosition(position);
			mTextView.setText(mCursor.getString(1));
			mTextView.setTextColor(0xffffff);
			return mTextView;
		}
		
	}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    // TODO Auto-generated method stub
    	mCursor.moveToPosition(position);
		TASK_ID = mCursor.getInt(0);
		task_value = mCursor.getString(1);
		Intent mIntent = new Intent();
		mIntent.setClass(MainActivity.this, SelectPicPopupWindow.class);
		Bundle tBumdle = new Bundle();
		tBumdle.putString("task_id", Integer.toString(TASK_ID));
		tBumdle.putString("task_value", task_value);
		mIntent.putExtras(tBumdle);
		startActivity(mIntent);
    }
    
    public void deleteFinishTask() {
		if (TASK_ID == 0) {
			return;
		}
		
		mTasksDB.delete("task_finish",TASK_ID);
		fCursor.requery();
		finishList.invalidateViews();
//		adapter_finish.changeCursor(newCursor);
		Toast.makeText(this, "Delete Successed!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		fCursor.moveToPosition(position);
		TASK_ID = fCursor.getInt(0);
		task_value = fCursor.getString(1);
		deleteFinishTask();
		adapter_finish.notifyDataSetChanged();
		return false;
	}
}
