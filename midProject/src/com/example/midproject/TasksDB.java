package com.example.midproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TasksDB extends SQLiteOpenHelper {
	
	private final static String DATABASE_NAME = "TASKS.db";
	private final static int DATABASE_VERSION = 1;
	
	
	private final static String TABLE_TASK = "task_new";
	private final static String TABLE_FINISH = "task_finish";
	public final static String TASK_ID = "task_id";
	public final static String TASK_VALUE = "task_value";
	
	

	public TasksDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
//		String sql = "CREATE TABLE " + TABLE_NAME + "(" + BOOK_ID + " INTEGER primary key autoincrement, "
//				+ BOOK_NAME + " text, " + BOOK_AUTHOR + " text);";
		String sql = "CREATE TABLE " + TABLE_TASK + "(" + TASK_ID + " INTEGER primary key autoincrement, "
				+ TASK_VALUE + " text);";
		db.execSQL(sql);
		
		String sql2 = "CREATE TABLE " + TABLE_FINISH + "(" + TASK_ID + " INTEGER primary key autoincrement, "
				+ TASK_VALUE + " text);";
		db.execSQL(sql2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE IF EXISTS " + TABLE_TASK;
		db.execSQL(sql);
		String sql2 = "DROP TABLE IF EXISTS " + TABLE_FINISH;
		db.execSQL(sql2);
		onCreate(db);
	}
	
	public Cursor select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_TASK, null, null, null, null, null, null);
		return cursor;
	}
	
	// task
	public Cursor select(String table_name) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(table_name, null, null, null, null, null, null);
		return cursor;
	}
	
	
	// task
	public long insert(String table_name, String task_value) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(TASK_VALUE, task_value);
		long row = db.insert(table_name, null, cv);
		return row;
	}
	
	public void delete(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = TASK_ID + " =?";
		String[] whereValue = {Integer.toString(id)};
		db.delete(TABLE_TASK, where, whereValue);
	}
	
	// task
	public void delete(String table_name, int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = TASK_ID + " =?";
		String[] whereValue = {Integer.toString(id)};
		db.delete(table_name, where, whereValue);
	}
	
	// task
	public void update (int id, String table_name, String task_value) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = TASK_ID + " =?";
		String[] whereValue = {Integer.toString(id)};
		
		ContentValues cv = new ContentValues();
		cv.put(TASK_VALUE, task_value);
		db.update(table_name, cv, where, whereValue);
	}
	
	public Cursor select(String table_name, String value) {
		String sql = "select * from " + table_name + " where task_value like ?";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, new String[]{"%"+value+"%"});
		return cursor;
	}

}