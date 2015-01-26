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


public class SearchWindow extends ActionBarActivity 
  implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
	
	private TasksDB mTasksDB;
	private Cursor mCursor;
	private Cursor fCursor;
	private ImageButton searchButton;
	private EditText searchValue;
	private ListView taskList;
	private ListView finishList;
	
	private TasksListAdapter adapter_finish;
	private TasksListAdapter adapter_new;
	
	private int TASK_ID = 0;
	private String task_value;
	
	public static SearchWindow instance = null;
	
	private String value;
	
	Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_result);
        
        bundle = this.getIntent().getExtras();
		value = bundle.getString("search_value");
        
        setUpViews();
        
        searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				value = searchValue.getText().toString();
				if (!value.equals("")) {
					mCursor = mTasksDB.select("task_new",value);
					adapter_new.set(mCursor);
					adapter_new.notifyDataSetChanged();
					
					fCursor = mTasksDB.select("task_finish", value);
					adapter_finish.set(fCursor);
					adapter_finish.notifyDataSetChanged();
				}
				
			}
        });
        
        
        
    }
    
    public void setUpViews() {
    	
    	
		mTasksDB = new TasksDB(this);
		mCursor = mTasksDB.select("task_new",value);
		fCursor = mTasksDB.select("task_finish", value);
		
		searchButton = (ImageButton)findViewById(R.id.searchButton_searchLayout);
		searchValue = (EditText)findViewById(R.id.searchValue);
		
		taskList = (ListView)findViewById(R.id.list_task_find);
		finishList = (ListView)findViewById(R.id.list_finish_find);
		
		adapter_new = new TasksListAdapter(this, mCursor);
		taskList.setAdapter(adapter_new);
		
		adapter_finish = new TasksListAdapter(this, fCursor) ;
		finishList.setAdapter(adapter_finish);
		
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
		
		public void set(Cursor mCursor) {
			this.mCursor = mCursor;
		}

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
			return mTextView;
		}
		
	}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    // TODO Auto-generated method stub
    }
    

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		return false;
	}
}
