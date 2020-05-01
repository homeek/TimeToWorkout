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



    TextView textVietData, textViewGodzina;
    public Typeface text1;



    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timepicker;




    private ImageView TheButtonStart, trzydziesciSekundOn, szescdziesiatSekundOn;


    public String lap2, lap;

    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;

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





   // textViewGodzina = (TextView) findViewById(R.id.godzinaTextV);
  //  textVietData = (TextView) findViewById(R.id.dataTextV);








   /* text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");



        textViewGodzina.setTypeface(text1);
        textVietData.setTypeface(text1);*/












    // pobiera date i godzine z classy calendar
    final Calendar c = Calendar.getInstance();
    final int year = c.get(Calendar.YEAR);
    final int month = c.get(Calendar.MONTH);
    final int day = c.get(Calendar.DAY_OF_MONTH);

    final int h = c.get(Calendar.HOUR_OF_DAY);
    final int m = c.get(Calendar.MINUTE);


      //  textViewGodzina.setText(h + ":" + String.format("%02d",m));

      //  textVietData.setText(day + "/" + String.format("%02d", month + 1) + "/" + year+" ");


//datapicker pobiera date i wstawia w tym przypadku w edittext
   /* datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker datedayPicker, int i, int i1, int i2 ) {

            // TextView data = (TextView) findViewById(R.id.edittext_data);

            dataRok=i;
            dataMiesiac=i1;
            dataDzien=i2;
            textVietData.setText(i2 + "/" + String.format("%02d", i1 + 1) + "/" + i+" ");
        }



    },year, month, day);




        textVietData.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            datePickerDialog.show();
        }
    });


//to samo z godziną
    timepicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TextView godzina = (TextView) findViewById(R.id.edittext_godzina);
            timegodziny = hourOfDay;
            timeminuty = minute;
            textViewGodzina.setText(hourOfDay + ":" + minute);
        }
    },h,m,true);


        textViewGodzina.setOnClickListener((new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            timepicker.show();
        }
    }));*/


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

        Intent doListyTreningowIntent = new Intent(getApplicationContext(),activity_lista_treningow.class);
        startActivity(doListyTreningowIntent);

    }



}
