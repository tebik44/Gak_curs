package com.digitcreativestudio.alarmapp;

import static android.app.PendingIntent.getActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Button;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    Ringtone ringtone;
    Button some_button;
    MediaPlayer player;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Будильник!", Toast.LENGTH_LONG).show();
        player = MediaPlayer.create(context, R.raw.alarm);
        player.start();
    }

}
