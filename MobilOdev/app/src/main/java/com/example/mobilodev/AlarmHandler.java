package com.example.mobilodev;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class AlarmHandler extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "Alarm çalıyor", Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ringtone.stop();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 5000);
    }
}
