package pl.fitandyummy.timetoworkout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/*import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;*/
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ActivityIleCwiczy extends AppCompatActivity {

    public Typeface text1;
    private TextView telebim;
    public Chronometer chronometerWork;
    public Chronometer chronometerRest;
    public boolean runningWork;
    public boolean runningRest;
    public long pauseoffsetWork;
    public long pauseoffsetRest;

    private int mRest, mLaps;
    private int intSumaWork, intSumaRest;

    private ImageView TheButton, StopButton, trzydziesciSekundOn, szescdziesiatSekundOn, periodValueOFf;

    int periodValueInttt;
    private SeekBar seekBarValue;
    public TextView periodValue;

    public String lap2, lap;

    private AdView mAdView;

    public boolean trzysziesciSekundRestBoolean, szescdziesiatSekundRestBoolen, periodBooleannn = false;
    public boolean stopSaveBoolen;

    private RecyclerView mRecyclerViewWork, mRecyclerViewRest;
    private RecyclerView.Adapter mAdapterWork, mAdapterRest;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<ElementyTreningu> listaETWork, listaETRest;
    public ArrayList<ElementyListyCwiczen> listaCW;

    int year;
    int month;
    int day;
    int h;
    int m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ile_cwiczy);

        createRecyclerViewWork();
        createRecyclerViewRest();

        //tworzy array list - musi bo sie wyjebie

        listaETWork = new ArrayList<ElementyTreningu>();
        listaETRest = new ArrayList<ElementyTreningu>();
        listaCW = new ArrayList<ElementyListyCwiczen>();

        mAdapterWork = new AdapterET(listaETWork);
        mAdapterRest = new AdapterET(listaETRest);

        mRecyclerViewWork.setAdapter(mAdapterWork);
        mRecyclerViewRest.setAdapter(mAdapterWork);

        mAdapterWork.notifyDataSetChanged();

//baner AdMob
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~9718212776");
        //   MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        chronometerWork = (Chronometer) findViewById(R.id.chronoWorkRaz);
        chronometerRest = (Chronometer) findViewById(R.id.godzinaTextV);

        TheButton = (ImageView) findViewById(R.id.button2);
        trzydziesciSekundOn = (ImageView) findViewById(R.id.trzydziesciSOff);
        szescdziesiatSekundOn = (ImageView) findViewById(R.id.szescdziesiatSOff);

        StopButton = (ImageView) findViewById(R.id.savebutton);
        telebim = (TextView) findViewById(R.id.telebim);
        periodValue = (TextView) findViewById(R.id.periodValue);
        seekBarValue = (SeekBar) findViewById(R.id.seekBarPeriod);
        periodValueOFf = (ImageView) findViewById(R.id.seekBarOff);

        setPeriodValueText();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                listaETRest.remove(viewHolder.getAdapterPosition());
                mAdapterRest.notifyDataSetChanged();
            }
        });

        helper.attachToRecyclerView(mRecyclerViewRest);

        ItemTouchHelper helper2 = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                listaETWork.remove(viewHolder.getAdapterPosition());
                mAdapterWork.notifyDataSetChanged();
            }
        });

        helper2.attachToRecyclerView(mRecyclerViewWork);

        Intent intent = getIntent();
        String nazwaCiczenia = intent.getStringExtra("nazwaCwiczenia");
        int rok = intent.getIntExtra("year", 99);
        int miesiac = intent.getIntExtra("month", 99);
        int dzien = intent.getIntExtra("day", 99);
        int godzina = intent.getIntExtra("h", 99);
        int minuty = intent.getIntExtra("m", 99);
        int periodValueInt = intent.getIntExtra("periodValue", 3600);
        boolean intentTrzysziesciSekundRestBoolean = intent.getBooleanExtra("boolean30", false);
        boolean intentSzescdziesiatSekundRestBoolen = intent.getBooleanExtra("boolean60", false);
        boolean periodBoolean = intent.getBooleanExtra("booleanPeriod", false);

        trzysziesciSekundRestBoolean = intentTrzysziesciSekundRestBoolean;
        szescdziesiatSekundRestBoolen = intentSzescdziesiatSekundRestBoolen;

        year = rok;
        month = miesiac;
        day = dzien;
        h = godzina;
        m = minuty;

        periodValueInttt = periodValueInt;
        periodBooleannn = periodBoolean;

        setPeriodValueText();
        telebim.setText(nazwaCiczenia);
        text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");
        chronometerWork.setTypeface(text1);
        chronometerRest.setTypeface(text1);
        telebim.setTypeface(text1);
        ustawieniPeriodBoolean();

        //seekbary
        seekBarValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                periodValue.setTextColor(0xFFB73E02);
                periodValue.setText("" + progress + " s");
                periodValueInttt = progress;
                periodBooleannn = true;
                periodValueOFf.setImageResource(R.drawable.tripleon);

                chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometerRest) {
                        if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > periodValueInttt * 1000) {
                            onClickStartWrok();
                            letsWork();
                            onClickStopRest();
                            onClickResetRest();
                        }
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(ActivityIleCwiczy.this, "REST period " + periodValueInttt + " s", Toast.LENGTH_SHORT).show();
            }
        });

// główny button petla if else

        TheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!runningWork && !runningRest) {
                    onClickStartWrok();
                    start();

                } else if (runningWork && !runningRest) {

                    //NORMAL REST
                    if (!periodBooleannn) {
                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {

                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > periodValueInttt * 1000) {
                                }
                            }
                        });

                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();

                    } else if (periodBooleannn) {
                        Toast.makeText(ActivityIleCwiczy.this, "REST period " + periodValueInttt + " s", Toast.LENGTH_SHORT).show();
                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();

                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {
                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > periodValueInttt * 1000) {
                                    onClickStartWrok();
                                    letsWork();
                                    onClickStopRest();
                                    onClickResetRest();
                                }
                            }
                        });
                    }
                }
 // GO REST
                                // ZMIANA BUTTONA NA SEEKBAR ZPSTAWIC POKI CO
                //30s REST

              /*  if ( runningWork &&!runningRest){

//NORMAL REST
                    if (!trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){
                        //Toast.makeText(ActivityIleCwiczy.this, "kiedy szary", Toast.LENGTH_SHORT).show();
                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {

                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > periodValueInttt*1000){
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
                        Toast.makeText(ActivityIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();
                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();

                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {
                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > periodValueInttt*1000 ){
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
                        Toast.makeText(ActivityIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();
                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();
                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {
                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > periodValueInttt*1000){

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
                        Toast.makeText(ActivityIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();
                        onClickStartRest();
                        goRest();
                        onClickStopWork();
                        onClickResetWork();
                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {
                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > periodValueInttt*1000){

                                    onClickStartWrok();
                                    letsWork();
                                    onClickStopRest();
                                    onClickResetRest();
                                }
                            }
                        });
                    }*/

                else
 //LEST WORK
                    if (!runningWork && runningRest) {
                        if (periodBooleannn) {
                            Toast.makeText(ActivityIleCwiczy.this, "REST period " + periodValueInttt + " s", Toast.LENGTH_SHORT).show();
                        }

                        if (!periodBooleannn) {
                            onClickStartWrok();
                            letsWork();
                            onClickStopRest();
                            onClickResetRest();
                        }
                    }
            }
        });

        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);


        // 30 60 90s rest
/*
        trzydziesciSekundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){
                    onTrzydziesciS();

         //chrono na 30s
                    Toast.makeText(ActivityIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();
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

                }else

                if (trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){
                    offTrzysiesciS();

         //chrono zerowy
                    Toast.makeText(ActivityIleCwiczy.this, "USER REST", Toast.LENGTH_SHORT).show();
                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 10000){
                            }
                        }
                    });

                }else

                if (trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){
                    offTrzysiesciS();

         //chrono 60s
                    Toast.makeText(ActivityIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();

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

                }else

                if (!trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){
                    onTrzydziesciS();

         //chrono 90s

                    Toast.makeText(ActivityIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();
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
            }
        });

        szescdziesiatSekundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!szescdziesiatSekundRestBoolen&&trzysziesciSekundRestBoolean){
                    onSzescdziesiatS();

                    //chrono 90s

                    Toast.makeText(ActivityIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();
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

                }else

                if (szescdziesiatSekundRestBoolen&&trzysziesciSekundRestBoolean){
                    offSzescdziesiatS();

                    //chrono 30s

                    Toast.makeText(ActivityIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();
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

                }if (!szescdziesiatSekundRestBoolen&&!trzysziesciSekundRestBoolean){
                    onSzescdziesiatS();

                    //chrono 60

                    Toast.makeText(ActivityIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();

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
                }else

                if (szescdziesiatSekundRestBoolen&&!trzysziesciSekundRestBoolean){
                    offSzescdziesiatS();

                    //chrono 0s
                    Toast.makeText(ActivityIleCwiczy.this, "USE REST", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {

                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 10000){
                            }
                        }
                    });
                }
            }
        });*/

        StopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stop();
            }
        });

        periodValueOFf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (periodBooleannn) {
                    periodBooleannn = false;
                    periodValueOFf.setImageResource(R.drawable.tripleoff);
                    periodValue.setText(" ");
                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {

                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > periodValueInttt * 1000) {
                            }
                        }
                    });

                } else if (!periodBooleannn) {

                    if (periodValueInttt == 0) {
                        Toast.makeText(ActivityIleCwiczy.this, "tst", Toast.LENGTH_SHORT).show();

                    } else if (periodValueInttt > 0) {
                        periodValue.setText("" + periodValueInttt + "s");
                        periodValue.setTextColor(0xFFB73E02);
                        periodBooleannn = true;
                        periodValueOFf.setImageResource(R.drawable.tripleon);
                        chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                            @Override
                            public void onChronometerTick(Chronometer chronometerRest) {
                                if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > periodValueInttt * 1000) {

                                    onClickStartWrok();
                                    letsWork();
                                    onClickStopRest();
                                    onClickResetRest();
                                }
                            }
                        });
                        Toast.makeText(ActivityIleCwiczy.this, "REST period " + periodValueInttt + " s", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void setPeriodValueText() {
        if (periodBooleannn) {
            periodValue.setText("" + periodValueInttt + "s");
        }

        if (!periodBooleannn) {
            periodValue.setText(" ");
        }
    }

    //TU SIE KONCZY onCreate()

//zmiana The Buttona i kolorow  chronometra

    private void start() {
        listaETRest = new ArrayList<ElementyTreningu>();
        mAdapterRest = new AdapterET(listaETRest);
        mRecyclerViewRest.setAdapter(mAdapterRest);
        mAdapterRest.notifyDataSetChanged();

 //tworzy array list - musi bo sie wyjebie

        listaETWork = new ArrayList<ElementyTreningu>();
        mAdapterWork = new AdapterET(listaETWork);
        mRecyclerViewWork.setAdapter(mAdapterWork);
        mAdapterWork.notifyDataSetChanged();

        TheButton.setImageResource(R.drawable.work);

        mLaps = 0;
        mRest = 0;

        chronometerWork.setTextSize(30);
        chronometerWork.setTextColor(0xFF05681C);
        chronometerRest.setTextSize(20);
        chronometerRest.setTextColor(Color.DKGRAY);
    }

    private void goRest() {
        stopSaveBoolen = true;
        StopButton.setImageResource(R.drawable.stop);
        TheButton.setImageResource(R.drawable.rest);
        chronometerRest.setTextSize(30);
        chronometerRest.setTextColor(0xFFB73E02);
        chronometerWork.setTextSize(20);
        chronometerWork.setTextColor(Color.DKGRAY);
        lap = chronometerWork.getText().toString();
        mLaps++;

//tworzy array list
        int position = 0;
        listaETRest.add(0, new ElementyTreningu("SET  no.", mLaps, lap));

//odswierza i pokazuje array list
        mAdapterRest.notifyItemChanged(position);
        mAdapterRest.notifyDataSetChanged();

//dodawanie czasów
        int elapsedMillisWork = (int) (SystemClock.elapsedRealtime() - chronometerWork.getBase());
        intSumaWork = intSumaWork + elapsedMillisWork;
    }

    private void letsWork() {
        stopSaveBoolen = true;
        StopButton.setImageResource(R.drawable.stop);
        TheButton.setImageResource(R.drawable.work);
        chronometerWork.setTextSize(30);
        chronometerWork.setTextColor(0xFF05681C);
        chronometerRest.setTextSize(20);
        chronometerRest.setTextColor(Color.DKGRAY);
        lap2 = chronometerRest.getText().toString();

        mRest++;

        //tworzy array list
        int position = 0;

        listaETWork.add(0, new ElementyTreningu("REST  no.", mRest, lap2));

//odswierza i pokazuje array list
        mAdapterWork.notifyItemChanged(position);
        mAdapterWork.notifyDataSetChanged();

//dodawanie czasów
        int elapsedMillisRest = (int) (SystemClock.elapsedRealtime() - chronometerRest.getBase());

        intSumaRest = intSumaRest + elapsedMillisRest;
    }

    //funkcje chronometrow

    // funkcje głównego buttona dla chronometrow

    private void onClickStartRest() {

        if (!runningRest) {
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
        if (!runningWork) {
            chronometerWork.setBase(SystemClock.elapsedRealtime() - pauseoffsetWork);
            chronometerWork.start();
            runningWork = true;


        }
    }

    public void onClickResetWork() {
        chronometerWork.setBase(SystemClock.elapsedRealtime());
        pauseoffsetWork = 0;
    }


    public void onClickResetRest() {
        chronometerRest.setBase(SystemClock.elapsedRealtime());
        pauseoffsetRest = 0;
    }


    public void Reset(View view) {

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

        mLaps = 0;
        mRest = 0;
        intSumaWork = 0;
        intSumaRest = 0;

        stopSaveBoolen = true;
        StopButton.setImageResource(R.drawable.stop);

        runningRest = false;
        runningWork = false;
        TheButton.setImageResource(R.drawable.start);
        //telebim.setText(" ");

        chronometerRest.stop();
        onClickResetRest();
        chronometerWork.stop();
        onClickResetWork();

        chronometerWork.setTextSize(20);
        chronometerWork.setTextColor(Color.DKGRAY);

        chronometerRest.setTextSize(20);
        chronometerRest.setTextColor(Color.DKGRAY);
    }


    //funkcje 30 60 senud

   /* public void onSzescdziesiatS() {

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
    }*/


    //recycler view
    public void createRecyclerViewWork() {
        mRecyclerViewWork = findViewById(R.id.mSvLaps2);
        mRecyclerViewWork.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapterWork = new AdapterET(listaETWork);

        mRecyclerViewWork.setLayoutManager(mLayoutManager);
        mRecyclerViewWork.setAdapter(mAdapterWork);
    }

    public void createRecyclerViewRest() {
        mRecyclerViewRest = findViewById(R.id.mSvLaps);
        mRecyclerViewRest.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapterRest = new AdapterET(listaETRest);

        mRecyclerViewRest.setLayoutManager(mLayoutManager);
        mRecyclerViewRest.setAdapter(mAdapterRest);
    }

    public void Stop() {
        if (!stopSaveBoolen) {
            listaCW = new ArrayList<ElementyListyCwiczen>();
            String przekazywanNazwaCwiczenia = telebim.getText().toString();

            int secsW = intSumaWork / 1000 / 60;
            int minsW = intSumaWork / 60000;

            String sekundyWork = String.format("%02d",
                    TimeUnit.MILLISECONDS.toSeconds(intSumaWork) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(intSumaWork)));

            int secsR = intSumaRest / 1000 / 60;
            int minsR = intSumaRest / 60000;

            String sekundyRest = String.format("%02d",

                    TimeUnit.MILLISECONDS.toSeconds(intSumaRest) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(intSumaRest)));

            listaCW.add(0, new ElementyListyCwiczen(przekazywanNazwaCwiczenia, minsW + ":" + sekundyWork, minsR + ":" + sekundyRest, day + "/" + String.format("%02d", month + 1) + "/" + year + " ",
                    h + ":" + String.format("%02d", m)));

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editorr = preferences.edit();
            editorr.putString("raz", "raxz");
            editorr.putInt("dwa", 2);
            editorr.putInt("czy", 3);
            editorr.putString("cztery", "cztery");
            editorr.putString("piec", "piec");
            editorr.apply();

            SharedPreferences sharedPreferences = getSharedPreferences("dupadupa", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(listaCW);
            editor.putString("dupacycki", json);
            editor.apply();

            Intent dolistyCwiczenIntent = new Intent(getApplicationContext(), activity_lista_cwiczen.class);

            startActivity(dolistyCwiczenIntent);
        } else if (stopSaveBoolen) {

            stopSaveBoolen = false;
            StopButton.setImageResource(R.drawable.save);
            chronometerRest.stop();
            chronometerWork.stop();

            chronometerWork.setTextSize(20);
            chronometerWork.setTextColor(Color.DKGRAY);

            chronometerRest.setTextSize(20);
            chronometerRest.setTextColor(Color.DKGRAY);
        }
    }

    public void ustawieniPeriodBoolean() {

        if (periodBooleannn) {
            periodValueOFf.setImageResource(R.drawable.szescdziesiaton);
        }

        if (!periodBooleannn) {
            periodValueOFf.setImageResource(R.drawable.szescdziesiatoff);
        }
    }

   /* public void ustawienieRestow(){

        if (trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){
            onTrzydziesciS();

            //chrono na 30s
            Toast.makeText(ActivityIleCwiczy.this, "REST period 30s", Toast.LENGTH_SHORT).show();
        }else

        if (!trzysziesciSekundRestBoolean&&!szescdziesiatSekundRestBoolen){
            offTrzysiesciS();
            offSzescdziesiatS();
            //chrono zerowy
            Toast.makeText(ActivityIleCwiczy.this, "USER REST", Toast.LENGTH_SHORT).show();

        }else
        if (!trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){
            offTrzysiesciS();
            onSzescdziesiatS();

            //chrono 60s

            Toast.makeText(ActivityIleCwiczy.this, "REST period 60s", Toast.LENGTH_SHORT).show();

        }else
        if (trzysziesciSekundRestBoolean&&szescdziesiatSekundRestBoolen){
            onTrzydziesciS();
            onSzescdziesiatS();

            //chrono 90s
            Toast.makeText(ActivityIleCwiczy.this, "REST period 90s", Toast.LENGTH_SHORT).show();
        }
    }*/
}