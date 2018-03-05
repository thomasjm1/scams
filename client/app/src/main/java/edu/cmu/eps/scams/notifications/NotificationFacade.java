package edu.cmu.eps.scams.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import edu.cmu.eps.scams.R;

/**
 * Created by thoma on 3/4/2018.
 */

public class NotificationFacade {

    private static final String CHANNEL_ID = "EPS-SCAMS-CHANNEL";

    private int notificationIdCounter;

    public NotificationFacade(Context context) {
        this.notificationIdCounter = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void create(Context context, String title, String text) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_mode_comment_black_24dp)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(this.nextNotificationId(), notificationBuilder.build());
    }

    public int nextNotificationId() {
        int value = this.notificationIdCounter;
        this.notificationIdCounter = this.notificationIdCounter + 1;
        return value;
    }
}
