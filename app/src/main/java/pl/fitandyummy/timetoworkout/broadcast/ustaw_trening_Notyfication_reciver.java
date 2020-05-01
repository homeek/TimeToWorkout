package pl.fitandyummy.timetoworkout.broadcast;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import pl.fitandyummy.timetoworkout.R;
import pl.fitandyummy.timetoworkout.activity_lista_treningow;

import static pl.fitandyummy.timetoworkout.broadcast.App.CHANEL_CHUJ_ID;
import static pl.fitandyummy.timetoworkout.broadcast.App.CHANEL_CHUJ_ID;

public class ustaw_trening_Notyfication_reciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        context.getApplicationContext();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context.getApplicationContext());

        Intent repeating_intent = new Intent(context, activity_lista_treningow.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 321, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString("key", "defaultValue");
        Integer godziny = preferences.getInt("godzinaZakonczenia", 99);
        Integer minuty = preferences.getInt("minuta", 99);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context.getApplicationContext(), CHANEL_CHUJ_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ico_ttw)
                .setColor(Color.RED)
                .setContentTitle("TTW remind You of Workout!!!.")
                .setContentText("Today's Workout ,  " + value + "  don't put it off!")
                .setAutoCancel(false);

        notificationManager.notify(321, notification.build());
    }
}