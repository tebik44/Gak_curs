package com.digitcreativestudio.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    Ringtone ringtone;

    @Override
    public void onReceive(Context context, Intent intent) {

          Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
          ringtone = RingtoneManager.getRingtone(context, notificationUri);
          if (ringtone == null) {
              notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
              ringtone = RingtoneManager.getRingtone(context, notificationUri);
          }
          if (ringtone != null) {
              ringtone.play();

          }
//        Toast.makeText(context, "Будильник!", Toast.LENGTH_LONG).show();
//        player = MediaPlayer.create(context, R.raw.alarm);
//        player.start();
    }

    protected void Destroy() {
        if (ringtone!= null && ringtone.isPlaying()) {
            ringtone.stop();
        }

    }
}
