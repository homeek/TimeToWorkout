package pl.fitandyummy.timetoworkout;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

/*import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;*/

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Calendar;

import pl.fitandyummy.timetoworkout.broadcast.potwierdz_trening_Notyfication_reciver;
import pl.fitandyummy.timetoworkout.broadcast.ustaw_trening_Notyfication_reciver;

public class ActivityUstawiaczTreningow extends AppCompatActivity {

    public Typeface text1;

    public EditText telebim, textVietData, textViewGodzina;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timepicker;

    public AlarmManager alarmManager;
    public AlarmManager alarmManager2;
    public PendingIntent pendingIntent;
    public PendingIntent pendingIntent2;

    private ImageView saveButton;

    public String przekazywana;

    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;

    private AdView mAdView;

    private RecyclerView.Adapter mAdapterWork, mAdapterRest;
    public ArrayList<ElementyTreningu> listaETWork, listaETRest;
    public ArrayList<ElementyListyCwiczen> listaCW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ustawiacz_treningow);

        //tworzy array list - musi bo sie wyjebie
        listaETWork = new ArrayList<ElementyTreningu>();
        listaETRest = new ArrayList<ElementyTreningu>();
        listaCW = new ArrayList<ElementyListyCwiczen>();

        mAdapterWork = new AdapterET(listaETWork);
        mAdapterRest = new AdapterET(listaETRest);
        mAdapterWork.notifyDataSetChanged();

        //baner AdMob
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~9718212776");
        //  MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //deklaracja obiektow z xml
        saveButton = (ImageView) findViewById(R.id.savebutton);
        textVietData = (EditText) findViewById(R.id.dataTextV);
        textViewGodzina = (EditText) findViewById(R.id.godzinaTextV);
        telebim = (EditText) findViewById(R.id.telebim);
        przekazywana = telebim.getText().toString();

        textViewGodzina.setTypeface(text1);
        textVietData.setTypeface(text1);

        telebim.setTypeface(text1);

// pobiera date i godzine z classy calendar
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int h = c.get(Calendar.HOUR_OF_DAY);
        final int m = c.get(Calendar.MINUTE);

//datapicker pobiera date i wstawia w tym przypadku w edittext
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datedayPicker, int i, int i1, int i2) {
                dataRok = i;
                dataMiesiac = i1;
                dataDzien = i2;
                textVietData.setText(i2 + "/" + String.format("%02d", i1 + 1) + "/" + i + " ");
            }


        }, year, month, day);
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
                timegodziny = hourOfDay;
                timeminuty = minute;
                textViewGodzina.setText(hourOfDay + ":" + String.format("%02d", minute));
            }
        }, h, m, true);
        textViewGodzina.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker.show();
            }
        }));

        //funkcje banera reklamowy AdMob

        mAdView.setAdListener(new AdListener() {
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
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (telebim.getText().toString().equals("") || textVietData.getText().toString().equals("") || textViewGodzina.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), " Uzupełnij dane  ", Toast.LENGTH_LONG).show();

                } else {

                    //ustawianie alarmu notyfikacji

                    //pobieranie daty z tej chwili do wyswietlenia notyfikacji kończącej
                    c.get(Calendar.YEAR);
                    c.get(Calendar.MONTH);
                    c.get(Calendar.DAY_OF_MONTH);
                    c.get(Calendar.HOUR_OF_DAY);
                    c.get(Calendar.MINUTE);

//tworzy intencje zbudowaną z osobnej class'ie
                    Intent intent2 = new Intent(getApplicationContext(), potwierdz_trening_Notyfication_reciver.class);
                    pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 456, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

//jednorazowy alarm notyfikacji konczącej
                    alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager2.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent2);

                    //pobiera date i czas z datapickera
                    c.set(Calendar.DAY_OF_MONTH, dataDzien);
                    c.set(Calendar.MONTH, dataMiesiac);
                    c.set(Calendar.YEAR, dataRok);
                    c.set(Calendar.HOUR_OF_DAY, timegodziny);
                    c.set(Calendar.MINUTE, timeminuty);

                    //tworzy intencje zbudowaną w osobnej class'ie
                    Intent intent = new Intent(getApplicationContext(), ustaw_trening_Notyfication_reciver.class);
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 321, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//alarm notyfikacji z interwałem dziennym razy okres
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

// wiadomosc toast witaj na bombie
                    Toast.makeText(getApplicationContext(), " Reminder set ! ", Toast.LENGTH_LONG).show();
                    przekazywana = telebim.getText().toString();
                    saveData();

                    Intent doListyTreningowIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(doListyTreningowIntent);
                }
            }
        });
    }

    //TU SIE KONCZY onCreate()

    public void doListyTreningowBaton(View view) {
        Intent doListyTreningowIntent = new Intent(getApplicationContext(), activity_lista_treningow.class);
        startActivity(doListyTreningowIntent);
    }

    private void saveData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorr = preferences.edit();
        editorr.putString("key", przekazywana);
        editorr.putInt("godzinaZakonczenia", dataDzien);
        editorr.putInt("minuta", dataMiesiac);
        editorr.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("szered pref", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.apply();
    }
}