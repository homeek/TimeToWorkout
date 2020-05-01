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


    private ImageButton StartWork, StopWork, ResetWork, StartRest, StopRest, ResetRest;
    TextView mocarnoscTxt, nasza, sprobuj,textVietData, textViewGodzina, textView,  textView2, sumaWork, sumaRest;
    public Typeface text1;

    private EditText telebim;

    public Chronometer chronometerWork;
    public Chronometer chronometerRest;
    public boolean runningWork;
    public boolean runningRest;
    public long pauseoffsetWork;
    public long pauseoffsetRest;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timepicker;

    public ScrollView mSvlaps2, mSvlaps;

    private int mRest, mLaps, elapsedMillisWork, elapsedMilisRest;

    private long intSumaWork, intSumaRest;


    private ImageView TheButtonStart, trzydziesciSekundOn, szescdziesiatSekundOn, doubleSet, tripleSet, periodValueOFf;


    public String lap2, lap;

    public int dataRok;
    public int dataMiesiac;
    public int dataDzien;
    public int timegodziny;
    public int timeminuty;

    private int periodValueInt;
    private SeekBar seekBarValue;
    public TextView periodValue;

    private AdView mAdView;

    public boolean doubleSetBoolean, tripleSetBoolean, trzysziesciSekundRestBoolean, szescdziesiatSekundRestBoolen, periodBoolean =false;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_ile_cwiczy);


        periodBoolean = false;


//        createRecyclerViewWork();
    //    createRecyclerViewRest();

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

     //   chronometerWork = (Chronometer) findViewById(R.id.chronoWork);
      //  chronometerRest = (Chronometer) findViewById(R.id.godzinaTextV);



        TheButtonStart = (ImageView) findViewById(R.id.savebutton);


      //  trzydziesciSekundOn = (ImageView) findViewById(R.id.trzydziesciSOff);
     //   szescdziesiatSekundOn = (ImageView) findViewById(R.id.szescdziesiatSOff);
        doubleSet = (ImageView) findViewById(R.id.doubleSeries);
        tripleSet = (ImageView) findViewById(R.id.tripleSeries);


          textViewGodzina = (TextView) findViewById(R.id.godzinaTextV);
          textVietData = (TextView) findViewById(R.id.dataTextV);
   //     textView2 = (TextView) findViewById(R.id.mEtLaps);
    //    textView = (TextView) findViewById(R.id.mEtLaps2);
       // mSvlaps = (ScrollView) findViewById(R.id.mSvLaps);
       // mSvlaps2 = (ScrollView) findViewById(R.id.mSvLaps2);
    //    sumaWork = (TextView) findViewById(R.id.sumaWork);
    //    sumaRest = (TextView) findViewById(R.id.sumaRest);


        telebim = (EditText) findViewById(R.id.telebim);

        periodValue = (TextView ) findViewById(R.id.periodValue);

        seekBarValue = (SeekBar) findViewById(R.id.seekBarPeriod);

        periodValueOFf = (ImageView) findViewById(R.id.seekBarOff);






        text1 = Typeface.createFromAsset(getAssets(),"fonts/KO.ttf");

//        chronometerWork.setTypeface(text1);
  //      chronometerRest.setTypeface(text1);

        textViewGodzina.setTypeface(text1);
        textVietData.setTypeface(text1);

//        sumaRest.setTypeface(text1);
  //      sumaWork.setTypeface(text1);

        telebim.setTypeface(text1);





        //seekbary



        seekBarValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                periodValue.setText(""+progress+" s");
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


        textViewGodzina.setText(h + ":" + String.format("%02d",m));

        textVietData.setText(day + "/" + String.format("%02d", month + 1) + "/" + year+" ");


//datapicker pobiera date i wstawia w tym przypadku w edittext
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

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
        }));


    // gbutton START

        TheButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (telebim.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), " Paste exercise name ", Toast.LENGTH_LONG).show();

                } else if( doubleSetBoolean&&!tripleSetBoolean) {


                    String nazwaCwiczenia = telebim.getText().toString();


                    Intent doIleCwiczyIntent = new Intent(getApplicationContext(), ActivityIleCwiczySuperDouble.class);

                    doIleCwiczyIntent.putExtra("nazwaCwiczenia", nazwaCwiczenia);
                    doIleCwiczyIntent.putExtra("boolean30", trzysziesciSekundRestBoolean);
                    doIleCwiczyIntent.putExtra("boolean60", szescdziesiatSekundRestBoolen);
                    doIleCwiczyIntent.putExtra("booleanPeriod", periodBoolean);
                    doIleCwiczyIntent.putExtra("periodValue", periodValueInt );
                    doIleCwiczyIntent.putExtra("year", year);
                    doIleCwiczyIntent.putExtra("month", month);
                    doIleCwiczyIntent.putExtra("day", day);
                    doIleCwiczyIntent.putExtra("h", h);
                    doIleCwiczyIntent.putExtra("m", m);


                    startActivity(doIleCwiczyIntent);
                } else if( !doubleSetBoolean&&!tripleSetBoolean) {


                    String nazwaCwiczenia = telebim.getText().toString();


                    Intent doIleCwiczyIntent = new Intent(getApplicationContext(), ActivityIleCwiczy.class);

                    doIleCwiczyIntent.putExtra("nazwaCwiczenia", nazwaCwiczenia);
                    doIleCwiczyIntent.putExtra("boolean30", trzysziesciSekundRestBoolean);
                    doIleCwiczyIntent.putExtra("boolean60", szescdziesiatSekundRestBoolen);
                    doIleCwiczyIntent.putExtra("booleanPeriod", periodBoolean);
                    doIleCwiczyIntent.putExtra("periodValue", periodValueInt );
                    doIleCwiczyIntent.putExtra("year", year);
                    doIleCwiczyIntent.putExtra("month", month);
                    doIleCwiczyIntent.putExtra("day", day);
                    doIleCwiczyIntent.putExtra("h", h);
                    doIleCwiczyIntent.putExtra("m", m);


                    startActivity(doIleCwiczyIntent);

                }else if( !doubleSetBoolean&&tripleSetBoolean) {


                    String nazwaCwiczenia = telebim.getText().toString();


                    Intent doIleCwiczyIntent = new Intent(getApplicationContext(), ActivityIleCwiczySuperTriple.class);

                    doIleCwiczyIntent.putExtra("nazwaCwiczenia", nazwaCwiczenia);
                    doIleCwiczyIntent.putExtra("boolean30", trzysziesciSekundRestBoolean);
                    doIleCwiczyIntent.putExtra("boolean60", szescdziesiatSekundRestBoolen);
                    doIleCwiczyIntent.putExtra("booleanPeriod", periodBoolean);
                    doIleCwiczyIntent.putExtra("periodValue", periodValueInt );
                    doIleCwiczyIntent.putExtra("year", year);
                    doIleCwiczyIntent.putExtra("month", month);
                    doIleCwiczyIntent.putExtra("day", day);
                    doIleCwiczyIntent.putExtra("h", h);
                    doIleCwiczyIntent.putExtra("m", m);


                    startActivity(doIleCwiczyIntent);
                }













              /*  if (!runningWork&&!runningRest) {
                    onClickStartWrok();
                    start();
                } else


             // GO REST
                if ( runningWork &&!runningRest){


                 //NORMAL REST

                    if (!trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){

                        //Toast.makeText(ActivityIleCwiczy.this, "kiedy szary", Toast.LENGTH_SHORT).show();


                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {

                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 10000){
                                }
                            }
                        });

                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();
                    } else


                 //30s REST

                    if (trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){

                        Toast.makeText(ActivityPreIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();

                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();

                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {
                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 30000){

                                    onClickStartWrok();
                                    letsWork();
                                    onClickStopRest();
                                    onClickResetRest();
                                }
                            }
                        });

                    }

                  //60s REST
                    if (!trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){

                        Toast.makeText(ActivityPreIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();
                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();

                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {
                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 60000){

                                    onClickStartWrok();
                                    letsWork();
                                    onClickStopRest();
                                    onClickResetRest();
                                }
                            }
                        });

                    }

     //90s REST
                    if (trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){

                        Toast.makeText(ActivityPreIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();
                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();

                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {
                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 90000){

                                    onClickStartWrok();
                                    letsWork();
                                    onClickStopRest();
                                    onClickResetRest();
                                }
                            }
                        });

                    }






                } else


      //LEST WORK
                if (!runningWork&&runningRest){






                    if (trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen) {

                        Toast.makeText(ActivityPreIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();

                    }

                    if (!trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen) {

                        Toast.makeText(ActivityPreIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();
                    }

                    if (trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen) {

                        Toast.makeText(ActivityPreIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();

                    }


                    if (!trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen) {


                        onClickStartWrok();
                        letsWork();
                        onClickStopRest();
                        onClickResetRest();
                    }




                }*/
            }
        });

//przycisk powrotu na pasku
        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(doMainIntent);
            }
        });









    // 30 60 90s rest
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



                }else

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

                if (!doubleSetBoolean&&!tripleSetBoolean){
                    onDoubleSet();



                    Toast.makeText(ActivityPreIleCwiczy.this, "DOUBLE SUPER SET", Toast.LENGTH_SHORT).show();



                }else  if (!doubleSetBoolean&&tripleSetBoolean) {
                    onDoubleSwith();



                    Toast.makeText(ActivityPreIleCwiczy.this, "DOUBLE SUPER SET", Toast.LENGTH_SHORT).show();
                }

                else  if (doubleSetBoolean&&!tripleSetBoolean) {

                    offDoubleSet();



                    Toast.makeText(ActivityPreIleCwiczy.this, "NORMAL SET", Toast.LENGTH_SHORT).show();
                }




            }
        });

        tripleSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!doubleSetBoolean&&!tripleSetBoolean){
                    onTripleSet();



                    Toast.makeText(ActivityPreIleCwiczy.this, "TRIPLE SUPER SET", Toast.LENGTH_SHORT).show();



                }else  if (doubleSetBoolean&&!tripleSetBoolean) {
                    onTripleSwith();



                    Toast.makeText(ActivityPreIleCwiczy.this, "TRIPLE SUPER SET", Toast.LENGTH_SHORT).show();



                }else  if (!doubleSetBoolean&&tripleSetBoolean) {

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


                if (periodBoolean){

                    periodBoolean = false;
                    periodValueOFf.setImageResource(R.drawable.tripleoff);
                    periodValue.setText(" ");



                } else

                if ( !periodBoolean){


                    if(periodValueInt==0){

                        Toast.makeText(ActivityPreIleCwiczy.this, "tst", Toast.LENGTH_SHORT).show();

                    } else


                    if(periodValueInt>0){



                        periodValue.setText(""+periodValueInt+"s");
                        periodValue.setTextColor(0xFFB73E02);
                        periodBoolean = true;
                        periodValueOFf.setImageResource(R.drawable.tripleon);

                        Toast.makeText(ActivityPreIleCwiczy.this, "REST period "+periodValueInt+" s", Toast.LENGTH_SHORT).show();
                    }















                }








              /*  if (periodBoolean){

                    periodBoolean = false;

                    periodValueOFf.setImageResource(R.drawable.tripleoff);

                } else

                if ( !periodBoolean){


                    Toast.makeText(ActivityPreIleCwiczy.this, "Set Rest Period", Toast.LENGTH_SHORT).show();



                }*/


                //Toast.makeText(ActivityPreIleCwiczy.this, "nie wiem kiedy to", Toast.LENGTH_SHORT).show();

            }
        });


    }//TU SIE KONCZY onCreate()







/*
  //zmiana The Buttona i kolorow  chronometra

    private void start() {

        //tworzy array list - musi bo sie wyjebie

        listaETRest = new ArrayList<ElementyTreningu>();
        mAdapterRest = new AdapterET(listaETRest);
        mRecyclerViewRest.setAdapter(mAdapterRest);
        mAdapterRest.notifyDataSetChanged();


        //tworzy array list - musi bo sie wyjebie

        listaETWork = new ArrayList<ElementyTreningu>();
        mAdapterWork = new AdapterET(listaETWork);
        mRecyclerViewWork.setAdapter(mAdapterWork);
        mAdapterWork.notifyDataSetChanged();


        TheButtonStart.setImageResource(R.drawable.work);

        mLaps = 0;
        mRest = 0;
        textView2.setText("");
        textView.setText("");

        //telebim.setTextColor(0xFFFF7F2A);
        //telebim.setText("WORK");
        //telebim.setTextColor(0xFFFF7F2A);
        chronometerWork.setTextSize(30);
        chronometerWork.setTextColor(0xFFFF7F2A);
        chronometerRest.setTextSize(20);
        chronometerRest.setTextColor(Color.DKGRAY);

    }

    private void goRest() {






        TheButtonStart.setImageResource(R.drawable.rest);


       // telebim.setTextColor(0xFFB73E02);
      //  telebim.setText("REST");
      //  telebim.setTextColor(0xFFB73E02);
        chronometerRest.setTextSize(30);
        chronometerRest.setTextColor(0xFFB73E02);
        chronometerWork.setTextSize(20);
        chronometerWork.setTextColor(Color.DKGRAY);












        lap = chronometerWork.getText().toString();
        textView2.append("SET "+ String.valueOf(mLaps)+" - "+lap+ "\n");

        mLaps++;



        //tworzy array list
        int position = 0;

        listaETRest.add(0,new ElementyTreningu("SET  no.", mLaps, lap));

                //odswierza i pokazuje array list
        mAdapterRest.notifyItemChanged(position);
        mAdapterRest.notifyDataSetChanged();

        //dodawanie czasów

        int elapsedMillisWork = (int) (SystemClock.elapsedRealtime() - chronometerWork.getBase()) ;

        intSumaWork=intSumaWork+elapsedMillisWork;

        long intSumaWorkToString = intSumaWork;

        sumaWork.setText(String.valueOf(intSumaWorkToString));

     /*   mSvlaps.post(new Runnable() {
            @Override
            public void run() {
                mSvlaps.smoothScrollTo(0, textView2.getBottom());

            }
        });

    }*/
/*
    private void letsWork() {




        TheButtonStart.setImageResource(R.drawable.work);

        //telebim.setTextColor(0xFFFF7F2A);
       // telebim.setText("WORK");
       // telebim.setTextColor(0xFFFF7F2A);
        chronometerWork.setTextSize(30);
        chronometerWork.setTextColor(0xFFFF7F2A);
        chronometerRest.setTextSize(20);
        chronometerRest.setTextColor(Color.DKGRAY);

        lap2 = chronometerRest.getText().toString();
        textView.append("REST "+ String.valueOf(mRest)+" - "+lap2+ "\n");

        mRest++;

        //tworzy array list
        int position = 0;

        listaETWork.add(0,new ElementyTreningu("REST  no.", mRest, lap2));

        //odswierza i pokazuje array list
        mAdapterWork.notifyItemChanged(position);
        mAdapterWork.notifyDataSetChanged();

        //dodawanie czasów

        int elapsedMillisRest = (int) (SystemClock.elapsedRealtime() - chronometerRest.getBase()) ;

        intSumaRest=intSumaRest+elapsedMillisRest;

        long intSumaRestToString = intSumaRest;

        sumaRest.setText(String.valueOf(intSumaRestToString));
*/
/*        mSvlaps2.post(new Runnable() {
            @Override
            public void run() {
                mSvlaps.smoothScrollTo(0, textView.getBottom());

            }
        });

    }*/
/*

        //funkcje chronometrow

    // funkcje głównego buttona dla chronometrow

        private void onClickStartRest() {

            if (!runningRest){
                chronometerRest.setBase(SystemClock.elapsedRealtime() - pauseoffsetRest);
                chronometerRest.start();
                runningRest = true;

            }

        }
        private void onClickStopRest() {
            if (runningRest) {
                chronometerRest.stop();
                pauseoffsetRest = SystemClock.elapsedRealtime() - chronometerRest.getBase();
                runningRest = false;
            }
        }
        private void onClickStopWork() {

            if (runningWork) {
                chronometerWork.stop();
                pauseoffsetWork = SystemClock.elapsedRealtime() - chronometerWork.getBase();
                runningWork = false;
            }
        }

        private void onClickStartWrok() {

            if (!runningWork){
                chronometerWork.setBase(SystemClock.elapsedRealtime() - pauseoffsetWork);
                chronometerWork.start();
                runningWork = true;


            }
        }
*/
/*

        public void onClickResetWork() {

            chronometerWork.setBase(SystemClock.elapsedRealtime());
            pauseoffsetWork = 0;
        }



        public void onClickResetRest() {

            chronometerRest.setBase(SystemClock.elapsedRealtime());
            pauseoffsetRest = 0;
        }

*/


      /*  public void Reset(View view) {

            chronometerRest.stop();
            onClickResetRest();
            chronometerWork.stop();
            onClickResetWork();
            textView2.setText("");
            textView.setText("");
            runningRest = false;
            runningWork = false;
            TheButtonStart.setImageResource(R.drawable.start);
            //telebim.setText(" ");

            chronometerWork.setTextSize(20);
            chronometerWork.setTextColor(Color.DKGRAY);

            chronometerRest.setTextSize(20);
            chronometerRest.setTextColor(Color.DKGRAY);
        }*/



 //funkcje 30 60 senud

  /*

    public void onSzescdziesiatS() {

        szescdziesiatSekundOn.setImageResource(R.drawable.szescdziesiantson);
        szescdziesiatSekundRestBoolen = true;


    }

    public void offSzescdziesiatS(){

        szescdziesiatSekundOn.setImageResource(R.drawable.szesdziesiatsoff);
        szescdziesiatSekundRestBoolen = false;
    }

    public void onTrzydziesciS() {


        trzydziesciSekundOn.setImageResource(R.drawable.trzydziescison);
        trzysziesciSekundRestBoolean = true;
    }

    public void offTrzysiesciS(){

        trzydziesciSekundOn.setImageResource(R.drawable.trzydziescisoff);
        trzysziesciSekundRestBoolean = false;
    }

    */




    public void onDoubleSet(){

        doubleSet.setImageResource(R.drawable.doubleon);
        doubleSetBoolean = true;
    }

    public void onTripleSet(){

        tripleSet.setImageResource(R.drawable.tripleon);
        tripleSetBoolean = true;
    }


    public void offDoubleSet(){

        doubleSet.setImageResource(R.drawable.doubleoff);
        doubleSetBoolean = false;
    }

    public void offTripleSet(){

        tripleSet.setImageResource(R.drawable.tripleoff);
        tripleSetBoolean = false;
    }

    public void onDoubleSwith(){

        doubleSet.setImageResource(R.drawable.doubleon);
        tripleSet.setImageResource(R.drawable.tripleoff);
        doubleSetBoolean = true;
        tripleSetBoolean = false;
    }

    public void onTripleSwith(){

        tripleSet.setImageResource(R.drawable.tripleon);
        doubleSet.setImageResource(R.drawable.doubleoff);
        tripleSetBoolean = true;
        doubleSetBoolean = false;
    }





    /*
    //recycler view
    public void createRecyclerViewWork(){
        mRecyclerViewWork = findViewById(R.id.mSvLaps2);
        mRecyclerViewWork.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapterWork = new AdapterET(listaETWork);

        mRecyclerViewWork.setLayoutManager(mLayoutManager);
        mRecyclerViewWork.setAdapter(mAdapterWork);
    }

    public void createRecyclerViewRest(){
        mRecyclerViewRest = findViewById(R.id.mSvLaps);
        mRecyclerViewRest.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapterRest = new AdapterET(listaETRest);

        mRecyclerViewRest.setLayoutManager(mLayoutManager);
        mRecyclerViewRest.setAdapter(mAdapterRest);
    }

    public void Stop(View view) {

        listaCW = new ArrayList<ElementyListyCwiczen>();

        String przekazywanNazwaCwiczenia = telebim.getText().toString();


        listaCW.add(0, new ElementyListyCwiczen(przekazywanNazwaCwiczenia, 2, 4,"wczoraj", "teraz"));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorr = preferences.edit();
        editorr.putString("raz","raxz");
        editorr.putInt("dwa",2);
        editorr.putInt("czy",3);
        editorr.putString("cztery","cztery");
        editorr.putString("piec","piec");
        editorr.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("dupadupa",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listaCW);
        editor.putString("dupacycki",json);
        editor.apply();


        Intent dolistyCwiczenIntent = new Intent(getApplicationContext(),activity_lista_cwiczen.class);
        startActivity(dolistyCwiczenIntent);

    }


*/
    public void doListyTreningowBaton(View view) {

        Intent doListyTreningowIntent = new Intent(getApplicationContext(),activity_lista_treningow.class);
        startActivity(doListyTreningowIntent);

    }

  /*  public void onSeekOff(View view) {

        if (periodBoolean=true){

            periodBoolean = false;

            periodValueOFf.setImageResource(R.drawable.tripleoff);

        } else

        if ( periodBoolean = false){


            Toast.makeText(ActivityPreIleCwiczy.this, "Set Rest Period", Toast.LENGTH_SHORT).show();


            periodValueOFf.setImageResource(R.drawable.tripleon);
        }


        Toast.makeText(ActivityPreIleCwiczy.this, "nie wiem kiedy to", Toast.LENGTH_SHORT).show();
    }*/

   /* public void onTrzydziesciS(View view) {
    }

    public void onSzescdziesiatS(View view) {
    }*/
}

