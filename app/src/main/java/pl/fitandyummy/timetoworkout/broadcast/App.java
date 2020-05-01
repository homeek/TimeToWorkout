package pl.fitandyummy.timetoworkout.broadcast;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANEL_CHUJ_ID = "chanelCHUJ";
    public static final String CHANEL_CHUJX_ID = "chanelCHUJX";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channelCHUJ = new NotificationChannel(
                    CHANEL_CHUJ_ID,
                    "chanel CHUJ",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationChannel channelCHUJX = new NotificationChannel(
                    CHANEL_CHUJX_ID,
                    "chanel CHUJX",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channelCHUJ);
            manager.createNotificationChannel(channelCHUJX);
        }
    }
}