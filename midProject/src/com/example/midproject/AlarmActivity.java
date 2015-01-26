package com.example.midproject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


public class AlarmActivity extends Activity {
	
	Bundle bundle;
	MediaPlayer mp;
	AlertDialog ad;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        long currentTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
		Date date = new Date(currentTime);
		
		Toast.makeText(getBaseContext(), "闹钟时间到 " + formatter.format(date), Toast.LENGTH_SHORT).show();
		
		bundle = this.getIntent().getExtras();
		
		mp = MediaPlayer.create(this, R.raw.test);
		mp.start();

        
        //显示对话框
        new AlertDialog.Builder(AlarmActivity.this).
            setCancelable(false).
            setTitle("我的任务闹钟提醒").//设置标题
            setMessage(bundle.getString("task_value")).//设置内容
            setPositiveButton("知道了", new OnClickListener(){//设置按钮
                public void onClick(DialogInterface dialog, int which) {
//                	stopService(new Intent("com.example.alarmstudy.MUSIC"));
                	mp.stop();
                    AlarmActivity.this.finish();//关闭Activity
                }
            }).create().show();
        
        
    }
    
    

}
