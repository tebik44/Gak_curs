package com.digitcreativestudio.alarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {


    Button buttonstartSetDialog;

    TimePickerDialog timePickerDialog;

    final static int RQS_1 = 1;

    RecyclerView rv_alarm;
    ArrayList<Model> dataSet;
    AlarmAdapter adapter;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dataSet = new ArrayList<>();
        rv_alarm = findViewById(R.id.rv_alarm);
        rv_alarm.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_alarm.setLayoutManager(layoutManager);

        adapter = new AlarmAdapter(dataSet);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        adapter.setInitial(getBaseContext(), alarmManager);
        rv_alarm.setAdapter(adapter);

        buttonstartSetDialog = (Button) findViewById(R.id.startSetDialog);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                textAlarmPrompt.setText("");
                openTimePickerDialog(false);

            }
        });

        final Context context = this;


    }

    private void deleteAllAlarm(){
        for(int x=0; x<dataSet.size(); x++){
            cancelAlarm(dataSet.get(x).getKey());
        }
    }

    private void cancelAlarm(int key){
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), RQS_1, intent, key);
        alarmManager.cancel(pendingIntent);
    }


    private void saveAllAlarm(ArrayList<Model> listDataSementara){
        for(int i=0; i<listDataSementara.size(); i++){
            try{
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                cal.setTime(sdf.parse(listDataSementara.get(i).getValue()));// all done
                setAlarm(listDataSementara.get(i).getKey(), cal);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(MainActivity.this,
                onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Set Alarm Time");

        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                calSet.add(Calendar.DATE, 1);
                Log.i("some_teg", " =<0");
            } else if (calSet.compareTo(calNow) > 0) {
                Log.i("some_teg", " > 0");
            } else {
                Log.i("some_teg", " else ");
            }

            setAlarm(calSet);
        }
    };

    private void setAlarm(Calendar targetCal) {


        int key = (int)(Math.random() * 1000 + 1);

        Intent intent = new Intent(getBaseContext(), Alarm_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, key);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);

        dataSet.add(new Model(key, targetCal.getTime().toString()));
        adapter.notifyDataSetChanged();
    }

    private void setAlarm(int key, Calendar targetCal) {

        Intent intent = new Intent(getBaseContext(), Alarm_Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, key);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);

        dataSet.add(new Model(key, targetCal.getTime().toString()));
        adapter.notifyDataSetChanged();
    }
}
