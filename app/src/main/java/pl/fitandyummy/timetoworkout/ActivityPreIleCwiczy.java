package pl.fitandyummy.timetoworkout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
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

public class ActivityPreIleCwiczy extends AppCompatActivity {

    TextView textVietData, textViewGodzina;
    public Typeface text1;
    private EditText telebim;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timepicker;

    private ImageView TheButtonStart, doubleSet, tripleSet, periodValueOFf;

    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;

    private int periodValueInt;
    private SeekBar seekBarValue;
    public TextView periodValue;

    private AdView mAdView;

    public boolean doubleSetBoolean, tripleSetBoolean, trzysziesciSekundRestBoolean, szescdziesiatSekundRestBoolen, periodBoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_ile_cwiczy);

        periodBoolean = false;

        //tworzy array list - musi bo sie wyjebie

        //baner AdMob
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~9718212776");
        //  MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //deklaracja obiektow z xml
        TheButtonStart = (ImageView) findViewById(R.id.savebutton);
        doubleSet = (ImageView) findViewById(R.id.doubleSeries);
        tripleSet = (ImageView) findViewById(R.id.tripleSeries);
        textViewGodzina = (TextView) findViewById(R.id.godzinaTextV);
        textVietData = (TextView) findViewById(R.id.dataTextV);
        telebim = (EditText) findViewById(R.id.telebim);
        periodValue = (TextView) findViewById(R.id.periodValue);
        seekBarValue = (SeekBar) findViewById(R.id.seekBarPeriod);
        periodValueOFf = (ImageView) findViewById(R.id.seekBarOff);

        text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");

        textViewGodzina.setTypeface(text1);
        textVietData.setTypeface(text1);

        telebim.setTypeface(text1);

        //seekbary
        seekBarValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                periodValue.setText("" + progress + " s");
                periodValueInt = progress;
                periodBoolean = true;
                periodValueOFf.setImageResource(R.drawable.tripleon);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

// pobiera date i godzine z classy calendar
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int h = c.get(Calendar.HOUR_OF_DAY);
        final int m = c.get(Calendar.MINUTE);

        textViewGodzina.setText(h + ":" + String.format("%02d", m));
        textVietData.setText(day + "/" + String.format("%02d", month + 1) + "/" + year + " ");

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
                // TextView godzina = (TextView) findViewById(R.id.edittext_godzina);
                timegodziny = hourOfDay;
                timeminuty = minute;
                textViewGodzina.setText(hourOfDay + ":" + minute);
            }
        }, h, m, true);
        textViewGodzina.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicker.show();
            }
        }));

        // gbutton START
        TheButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (telebim.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), " Paste exercise name ", Toast.LENGTH_LONG).show();

                } else if (doubleSetBoolean && !tripleSetBoolean) {
                    String nazwaCwiczenia = telebim.getText().toString();

                    Intent doIleCwiczyIntent = new Intent(getApplicationContext(), ActivityIleCwiczySuperDouble.class);

                    doIleCwiczyIntent.putExtra("nazwaCwiczenia", nazwaCwiczenia);
                    doIleCwiczyIntent.putExtra("boolean30", trzysziesciSekundRestBoolean);
                    doIleCwiczyIntent.putExtra("boolean60", szescdziesiatSekundRestBoolen);
                    doIleCwiczyIntent.putExtra("booleanPeriod", periodBoolean);
                    doIleCwiczyIntent.putExtra("periodValue", periodValueInt);
                    doIleCwiczyIntent.putExtra("year", year);
                    doIleCwiczyIntent.putExtra("month", month);
                    doIleCwiczyIntent.putExtra("day", day);
                    doIleCwiczyIntent.putExtra("h", h);
                    doIleCwiczyIntent.putExtra("m", m);

                    startActivity(doIleCwiczyIntent);
                } else if (!doubleSetBoolean && !tripleSetBoolean) {

                    String nazwaCwiczenia = telebim.getText().toString();
                    Intent doIleCwiczyIntent = new Intent(getApplicationContext(), ActivityIleCwiczy.class);

                    doIleCwiczyIntent.putExtra("nazwaCwiczenia", nazwaCwiczenia);
                    doIleCwiczyIntent.putExtra("boolean30", trzysziesciSekundRestBoolean);
                    doIleCwiczyIntent.putExtra("boolean60", szescdziesiatSekundRestBoolen);
                    doIleCwiczyIntent.putExtra("booleanPeriod", periodBoolean);
                    doIleCwiczyIntent.putExtra("periodValue", periodValueInt);
                    doIleCwiczyIntent.putExtra("year", year);
                    doIleCwiczyIntent.putExtra("month", month);
                    doIleCwiczyIntent.putExtra("day", day);
                    doIleCwiczyIntent.putExtra("h", h);
                    doIleCwiczyIntent.putExtra("m", m);

                    startActivity(doIleCwiczyIntent);

                } else if (!doubleSetBoolean && tripleSetBoolean) {

                    String nazwaCwiczenia = telebim.getText().toString();
                    Intent doIleCwiczyIntent = new Intent(getApplicationContext(), ActivityIleCwiczySuperTriple.class);

                    doIleCwiczyIntent.putExtra("nazwaCwiczenia", nazwaCwiczenia);
                    doIleCwiczyIntent.putExtra("boolean30", trzysziesciSekundRestBoolean);
                    doIleCwiczyIntent.putExtra("boolean60", szescdziesiatSekundRestBoolen);
                    doIleCwiczyIntent.putExtra("booleanPeriod", periodBoolean);
                    doIleCwiczyIntent.putExtra("periodValue", periodValueInt);
                    doIleCwiczyIntent.putExtra("year", year);
                    doIleCwiczyIntent.putExtra("month", month);
                    doIleCwiczyIntent.putExtra("day", day);
                    doIleCwiczyIntent.putExtra("h", h);
                    doIleCwiczyIntent.putExtra("m", m);

                    startActivity(doIleCwiczyIntent);
                }
            }
        });

//przycisk powrotu na pasku
        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(doMainIntent);
            }
        });


        // 30 60 90s rest STARA WERSJA
/*
        trzydziesciSekundOn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){
                    onTrzydziesciS();

         //chrono na 30s
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();

                }else
                if (trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){
                    offTrzysiesciS();

         //chrono zerowy
                    Toast.makeText(ActivityPreIleCwiczy.this, "USER REST", Toast.LENGTH_SHORT).show();
                }else

                if (trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){
                    offTrzysiesciS();

         //chrono 60s
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();
                }else

                if (!trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){
                    onTrzydziesciS();

         //chrono 90s
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();
                }
            }
        });

        szescdziesiatSekundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!szescdziesiatSekundRestBoolen&&trzysziesciSekundRestBoolean){
                    onSzescdziesiatS();

                    //chrono 90s
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();

                }eLse
                if (szescdziesiatSekundRestBoolen&&trzysziesciSekundRestBoolean){
                    offSzescdziesiatS();

                    //chrono 30s
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();

                }if (!szescdziesiatSekundRestBoolen&&!trzysziesciSekundRestBoolean){
                    onSzescdziesiatS();

                    //chrono 60
                    Toast.makeText(ActivityPreIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();

                }else
                if (szescdziesiatSekundRestBoolen&&!trzysziesciSekundRestBoolean){
                    offSzescdziesiatS();

                    //chrono 0s
                    Toast.makeText(ActivityPreIleCwiczy.this, "USE REST", Toast.LENGTH_SHORT).show();
                }
            }
        });  */

        doubleSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doubleSetBoolean && !tripleSetBoolean) {
                    onDoubleSet();
                    Toast.makeText(ActivityPreIleCwiczy.this, "DOUBLE SUPER SET", Toast.LENGTH_SHORT).show();

                } else if (!doubleSetBoolean && tripleSetBoolean) {
                    onDoubleSwith();
                    Toast.makeText(ActivityPreIleCwiczy.this, "DOUBLE SUPER SET", Toast.LENGTH_SHORT).show();

                } else if (doubleSetBoolean && !tripleSetBoolean) {
                    offDoubleSet();
                    Toast.makeText(ActivityPreIleCwiczy.this, "NORMAL SET", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tripleSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!doubleSetBoolean && !tripleSetBoolean) {
                    onTripleSet();
                    Toast.makeText(ActivityPreIleCwiczy.this, "TRIPLE SUPER SET", Toast.LENGTH_SHORT).show();

                } else if (doubleSetBoolean && !tripleSetBoolean) {
                    onTripleSwith();
                    Toast.makeText(ActivityPreIleCwiczy.this, "TRIPLE SUPER SET", Toast.LENGTH_SHORT).show();

                } else if (!doubleSetBoolean && tripleSetBoolean) {
                    offTripleSet();
                    Toast.makeText(ActivityPreIleCwiczy.this, "NORMAL SET", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        periodValueOFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (periodBoolean) {
                    periodBoolean = false;
                    periodValueOFf.setImageResource(R.drawable.tripleoff);
                    periodValue.setText(" ");

                } else if (!periodBoolean) {
                    if (periodValueInt == 0) {
                        Toast.makeText(ActivityPreIleCwiczy.this, "tst", Toast.LENGTH_SHORT).show();

                    } else if (periodValueInt > 0) {
                        periodValue.setText("" + periodValueInt + "s");
                        periodValue.setTextColor(0xFFB73E02);
                        periodBoolean = true;
                        periodValueOFf.setImageResource(R.drawable.tripleon);

                        Toast.makeText(ActivityPreIleCwiczy.this, "REST period " + periodValueInt + " s", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    //TU SIE KONCZY onCreate()

    public void onDoubleSet() {
        doubleSet.setImageResource(R.drawable.doubleon);
        doubleSetBoolean = true;
    }

    public void onTripleSet() {
        tripleSet.setImageResource(R.drawable.tripleon);
        tripleSetBoolean = true;
    }

    public void offDoubleSet() {
        doubleSet.setImageResource(R.drawable.doubleoff);
        doubleSetBoolean = false;
    }

    public void offTripleSet() {
        tripleSet.setImageResource(R.drawable.tripleoff);
        tripleSetBoolean = false;
    }

    public void onDoubleSwith() {
        doubleSet.setImageResource(R.drawable.doubleon);
        tripleSet.setImageResource(R.drawable.tripleoff);
        doubleSetBoolean = true;
        tripleSetBoolean = false;
    }

    public void onTripleSwith() {
        tripleSet.setImageResource(R.drawable.tripleon);
        doubleSet.setImageResource(R.drawable.doubleoff);
        tripleSetBoolean = true;
        doubleSetBoolean = false;
    }

    public void doListyTreningowBaton(View view) {
        Intent doListyTreningowIntent = new Intent(getApplicationContext(), activity_lista_treningow.class);
        startActivity(doListyTreningowIntent);
    }
}

