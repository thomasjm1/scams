package edu.cmu.eps.scams.messaging;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmFacade {
    private static final long INTERVAL_IN_MILLISECONDS = 10000;

    public static void setAlarm(Activity activity) {
        Intent alarmIntent = new Intent(activity, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + INTERVAL_IN_MILLISECONDS,
                INTERVAL_IN_MILLISECONDS,
                pendingIntent);
    }
}
