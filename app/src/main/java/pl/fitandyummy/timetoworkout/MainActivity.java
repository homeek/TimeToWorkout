package pl.fitandyummy.timetoworkout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ImageView TheButtonStart;

    // private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*

//baner AdMob
    // MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~8122554600");
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
    mAdView = findViewById(R.id.adView);
    AdRequest adRequest = new AdRequest.Builder()
            //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build();
        mAdView.loadAd(adRequest);*/

//deklaracja obiektow z xml
        TheButtonStart = (ImageView) findViewById(R.id.savebutton);

// gbutton START
        TheButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doIleCwiczyIntent = new Intent(getApplicationContext(), ActivityPreIleCwiczy.class);
                startActivity(doIleCwiczyIntent);
            }
        });

//funkcje banera reklamowy AdMob

      /*  mAdView.setAdListener(new AdListener() {
        @Override
        public void onAdLoaded() {
            // Code to be executed when an ad finishes loading.
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            // Code to be executed when an ad request fails.
        }

        @Override
        public void onAdOpened() {
            // Code to be executed when an ad opens an overlay that
            // covers the screen.
        }

        @Override
        public void onAdLeftApplication() {
            // Code to be executed when the user has left the app.
        }

        @Override
        public void onAdClosed() {
            // Code to be executed when when the user is about to return
            // to the app after tapping on an ad.
        }
     });*/
    }

    //TU SIE KONCZY onCreate()

    // listner do listy treningow
    public void doListyTreningowBaton(View view) {
        Intent doListyTreningowIntent = new Intent(getApplicationContext(), activity_lista_treningow.class);
        startActivity(doListyTreningowIntent);
    }
}