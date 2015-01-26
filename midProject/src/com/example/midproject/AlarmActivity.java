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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy��-MM��dd��-HHʱmm��ss��");
		Date date = new Date(currentTime);
		
		Toast.makeText(getBaseContext(), "����ʱ�䵽 " + formatter.format(date), Toast.LENGTH_SHORT).show();
		
		bundle = this.getIntent().getExtras();
		
		mp = MediaPlayer.create(this, R.raw.test);
		mp.start();

        
        //��ʾ�Ի���
        new AlertDialog.Builder(AlarmActivity.this).
            setCancelable(false).
            setTitle("�ҵ�������������").//���ñ���
            setMessage(bundle.getString("task_value")).//��������
            setPositiveButton("֪����", new OnClickListener(){//���ð�ť
                public void onClick(DialogInterface dialog, int which) {
//                	stopService(new Intent("com.example.alarmstudy.MUSIC"));
                	mp.stop();
                    AlarmActivity.this.finish();//�ر�Activity
                }
            }).create().show();
        
        
    }
    
    

}
