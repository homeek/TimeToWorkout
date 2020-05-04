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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/*import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;*/

public class ActivityIleCwiczySuperDouble extends AppCompatActivity {

    public Typeface text1;
    private TextView telebimRest;
    private EditText telebimRaz, telebimDwa;

    public Chronometer chronometerWorkRaz;
    public Chronometer chronometerWorkDwa;
    public Chronometer chronometerRest;
    public boolean runningWorkRaz;
    public boolean runningWorkDwa;
    public boolean runningRest;
    public long pauseoffsetWorkRaz;
    public long pauseoffsetWorkDwa;
    public long pauseoffsetRest;

    private int mLapsRest, mLapsWorksRaz, mLapsWorksDwa;
    private int intSumaWorkRaz, intSumaWorkDwa, intSumaRest;
    private ImageView TheButton, StopButton, trzydziesciSekundOn, szescdziesiatSekundOn;
    public String lapRest, lapWorkDwa, lapWorkRaz;

    // private AdView mAdView;

    public boolean trzysziesciSekundRestBoolean, szescdziesiatSekundRestBoolen = false;
    public boolean stopSaveBoolen;

    private RecyclerView mRecyclerViewWorkRaz, mRecyclerViewWorkDwa, mRecyclerViewRest;
    private RecyclerView.Adapter mAdapterWorkRaz, mAdapterWorkDwa, mAdapterRest;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<ElementyTreningu> listaETWorkRaz, listaETWorkDwa, listaETRest;
    public ArrayList<ElementyListyCwiczen> listaCW;

    int year;
    int month;
    int day;

    int h;
    int m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ile_cwiczy_super_double);

        createRecyclerViewWorkRaz();
        createRecyclerViewWorkDwa();
        createRecyclerViewRest();

        //tworzy array list - musi bo sie wyjebie

        listaETWorkRaz = new ArrayList<ElementyTreningu>();
        listaETWorkDwa = new ArrayList<ElementyTreningu>();
        listaETRest = new ArrayList<ElementyTreningu>();
        listaCW = new ArrayList<ElementyListyCwiczen>();

        mAdapterWorkRaz = new AdapterET(listaETWorkRaz);
        mAdapterWorkDwa = new AdapterET(listaETWorkDwa);
        mAdapterRest = new AdapterET(listaETRest);

        mRecyclerViewWorkRaz.setAdapter(mAdapterWorkRaz);
        mRecyclerViewWorkDwa.setAdapter(mAdapterWorkDwa);
        mRecyclerViewRest.setAdapter(mAdapterRest);

        mAdapterWorkRaz.notifyDataSetChanged();
        mAdapterWorkDwa.notifyDataSetChanged();
        mAdapterRest.notifyDataSetChanged();

        //baner AdMob

        // MobileAds.initialize(getApplicationContext(), "ca-app-pub-7671780201496787~8122554600");
       /* MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
             //   .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);*/
        //deklaracja obiektow z xml
        chronometerWorkRaz = (Chronometer) findViewById(R.id.chronoWorkRaz);
        chronometerWorkDwa = (Chronometer) findViewById(R.id.chronoWorkDwa);
        chronometerRest = (Chronometer) findViewById(R.id.chronoRest);

        TheButton = (ImageView) findViewById(R.id.button2);
        trzydziesciSekundOn = (ImageView) findViewById(R.id.trzydziesciSOff);
        szescdziesiatSekundOn = (ImageView) findViewById(R.id.szescdziesiatSOff);
        StopButton = (ImageView) findViewById(R.id.savebutton);

        telebimRaz = (EditText) findViewById(R.id.telebimWorkRaz);
        telebimDwa = (EditText) findViewById(R.id.telebimWorkDwa);
        telebimRest = (TextView) findViewById(R.id.telebimRest);

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
                listaETWorkRaz.remove(viewHolder.getAdapterPosition());
                mAdapterWorkRaz.notifyDataSetChanged();
            }
        });

        helper2.attachToRecyclerView(mRecyclerViewWorkRaz);

        Intent intent = getIntent();
        String nazwaCiczenia = intent.getStringExtra("nazwaCwiczenia");
        int rok = intent.getIntExtra("year", 99);
        int miesiac = intent.getIntExtra("month", 99);
        int dzien = intent.getIntExtra("day", 99);
        int godzina = intent.getIntExtra("h", 99);
        int minuty = intent.getIntExtra("m", 99);
        boolean intentTrzysziesciSekundRestBoolean = intent.getBooleanExtra("boolean30", false);
        boolean intentSzescdziesiatSekundRestBoolen = intent.getBooleanExtra("boolean60", false);

        trzysziesciSekundRestBoolean = intentTrzysziesciSekundRestBoolean;
        szescdziesiatSekundRestBoolen = intentSzescdziesiatSekundRestBoolen;

        year = rok;
        month = miesiac;
        day = dzien;
        h = godzina;
        m = minuty;

        telebimRaz.setText(nazwaCiczenia);
        text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");

        chronometerWorkRaz.setTypeface(text1);
        chronometerWorkDwa.setTypeface(text1);
        chronometerRest.setTypeface(text1);

        telebimRaz.setTypeface(text1);
        telebimDwa.setTypeface(text1);
        telebimRest.setTypeface(text1);

        ustawienieRestow();

        // główny button petla if else
        TheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!runningWorkRaz && !runningRest && !runningWorkDwa) {
                    onClickStartWrokRaz();
                    start();
                } else

                    // GO REST
                    if (!runningWorkRaz && runningWorkDwa && !runningRest) {

                        //NORMAL REST
                        if (!trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
                            chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                                @Override
                                public void onChronometerTick(Chronometer chronometerRest) {

                                    if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 10000) {
                                    }
                                }
                            });

                            onClickStartRest();
                            goRest();
                            onClickStopWorkDwa();
                            onClickResetWorkDwa();

                        } else

                            //30s REST
                            if (trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
                                Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 30s", Toast.LENGTH_SHORT).show();
                                onClickStartRest();
                                goRest();
                                onClickStopWorkDwa();
                                onClickResetWorkDwa();

                                chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                                    @Override
                                    public void onChronometerTick(Chronometer chronometerRest) {
                                        if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 30000) {
                                            onClickStartWrokRaz();
                                            letsWorkRaz();
                                            onClickStopRest();
                                            onClickResetRest();
                                        }
                                    }
                                });
                            }

                        //60s REST
                        if (!trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
                            Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 60s", Toast.LENGTH_SHORT).show();
                            onClickStartRest();
                            goRest();
                            onClickStopWorkDwa();
                            onClickResetWorkDwa();

                            chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                                @Override
                                public void onChronometerTick(Chronometer chronometerRest) {
                                    if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 60000) {
                                        onClickStartWrokRaz();
                                        letsWorkRaz();
                                        onClickStopRest();
                                        onClickResetRest();
                                    }
                                }
                            });
                        }

                        //90s REST
                        if (trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
                            Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 90s", Toast.LENGTH_SHORT).show();
                            onClickStartRest();
                            goRest();
                            onClickStopWorkDwa();
                            onClickResetWorkDwa();

                            chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                                @Override
                                public void onChronometerTick(Chronometer chronometerRest) {
                                    if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 90000) {
                                        onClickStartWrokRaz();
                                        letsWorkRaz();
                                        onClickStopRest();
                                        onClickResetRest();
                                    }
                                }
                            });
                        }

                    } else

                        //LEST WORK RAz
                        if (!runningWorkRaz && !runningWorkDwa && runningRest) {

                            if (trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
                                Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 90s", Toast.LENGTH_SHORT).show();
                            }

                            if (!trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
                                Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 60s", Toast.LENGTH_SHORT).show();
                            }

                            if (trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
                                Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 30s", Toast.LENGTH_SHORT).show();
                            }

                            if (!trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
                                onClickStartWrokRaz();
                                letsWorkRaz();
                                onClickStopRest();
                                onClickResetRest();
                            }


                        } else

                            //LEST WORK DWA
                            if (!runningWorkDwa && !runningRest && runningWorkRaz) {

                                if (trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
                                    Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 90s", Toast.LENGTH_SHORT).show();
                                    onClickStartWrokDwa();
                                    letsWorkDwa();
                                    onClickStopWorkRaz();
                                    onClickResetWorkRaz();
                                }

                                if (!trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
                                    Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 60s", Toast.LENGTH_SHORT).show();
                                    onClickStartWrokDwa();
                                    letsWorkDwa();
                                    onClickStopWorkRaz();
                                    onClickResetWorkRaz();
                                }

                                if (trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
                                    Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 30s", Toast.LENGTH_SHORT).show();
                                    onClickStartWrokDwa();
                                    letsWorkDwa();
                                    onClickStopWorkRaz();
                                    onClickResetWorkRaz();
                                }


                                if (!trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
                                    onClickStartWrokDwa();
                                    letsWorkDwa();
                                    onClickStopWorkRaz();
                                    onClickResetWorkRaz();
                                }
                            }
            }
        });

        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

    /*    back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doPreIleCwiczyIntent = new Intent(getApplicationContext(),ActivityPreIleCwiczy.class);
                startActivity(doPreIleCwiczyIntent);
            }
        });*/

        //przycisk powrotu na pasku
        //  ImageView ilebije = (ImageView) findViewById(R.id.toolchangeBtn);

        //klik do ilebije
  /*      ilebije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent doMainIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(doMainIntent);
            }
        });*/

        // 30 60 90s rest

        trzydziesciSekundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
                    onTrzydziesciS();

                    //chrono na 30s
                    Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 30s", Toast.LENGTH_SHORT).show();
                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 30000) {
                                onClickStartWrokRaz();
                                letsWorkRaz();
                                onClickStopRest();
                                onClickResetRest();
                            }
                        }
                    });

                } else if (trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
                    offTrzysiesciS();

                    //chrono zerowy
                    Toast.makeText(ActivityIleCwiczySuperDouble.this, "USER REST", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 10000) {
                            }
                        }
                    });

                } else if (trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
                    offTrzysiesciS();

                    //chrono 60s
                    Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 60s", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 60000) {
                                onClickStartWrokRaz();
                                letsWorkRaz();
                                onClickStopRest();
                                onClickResetRest();
                            }
                        }
                    });

                } else if (!trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
                    onTrzydziesciS();

                    //chrono 90s
                    Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 90s", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 90000) {
                                onClickStartWrokRaz();
                                letsWorkRaz();
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

                if (!szescdziesiatSekundRestBoolen && trzysziesciSekundRestBoolean) {
                    onSzescdziesiatS();

                    //chrono 90s
                    Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 90s", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 90000) {
                                onClickStartWrokRaz();
                                letsWorkRaz();
                                onClickStopRest();
                                onClickResetRest();
                            }
                        }
                    });

                } else if (szescdziesiatSekundRestBoolen && trzysziesciSekundRestBoolean) {
                    offSzescdziesiatS();

                    //chrono 30s
                    Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 30s", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 30000) {
                                onClickStartWrokRaz();
                                letsWorkRaz();
                                onClickStopRest();
                                onClickResetRest();
                            }
                        }
                    });
                }
                if (!szescdziesiatSekundRestBoolen && !trzysziesciSekundRestBoolean) {
                    onSzescdziesiatS();

                    //chrono 60
                    Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 60s", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {
                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 60000) {
                                onClickStartWrokRaz();
                                letsWorkRaz();
                                onClickStopRest();
                                onClickResetRest();
                            }
                        }
                    });

                } else if (szescdziesiatSekundRestBoolen && !trzysziesciSekundRestBoolean) {
                    offSzescdziesiatS();

                    //chrono 0s
                    Toast.makeText(ActivityIleCwiczySuperDouble.this, "USER REST", Toast.LENGTH_SHORT).show();

                    chronometerRest.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                        @Override
                        public void onChronometerTick(Chronometer chronometerRest) {

                            if ((SystemClock.elapsedRealtime() - chronometerRest.getBase()) > 10000) {
                            }
                        }
                    });
                }
            }
        });

        StopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stop();
            }
        });

        //funkcje banera reklamowy AdMob

       /* mAdView.setAdListener(new AdListener() {
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

    //zmiana The Buttona i kolorow  chronometra

    private void start() {

        //tworzy array list - musi bo sie wyjebie
        listaETRest = new ArrayList<ElementyTreningu>();
        mAdapterRest = new AdapterET(listaETRest);
        mRecyclerViewRest.setAdapter(mAdapterRest);
        mAdapterRest.notifyDataSetChanged();

        //tworzy array list - musi bo sie wyjebie
        listaETWorkRaz = new ArrayList<ElementyTreningu>();
        mAdapterWorkRaz = new AdapterET(listaETWorkRaz);
        mRecyclerViewWorkRaz.setAdapter(mAdapterWorkRaz);
        mAdapterWorkRaz.notifyDataSetChanged();

        //tworzy array list - musi bo sie wyjebie
        listaETWorkDwa = new ArrayList<ElementyTreningu>();
        mAdapterWorkDwa = new AdapterET(listaETWorkDwa);
        mRecyclerViewWorkDwa.setAdapter(mAdapterWorkDwa);
        mAdapterWorkDwa.notifyDataSetChanged();

        TheButton.setImageResource(R.drawable.work);

        mLapsWorksRaz = 0;
        mLapsWorksDwa = 0;
        mLapsRest = 0;

        chronometerWorkRaz.setTextSize(25);
        chronometerWorkRaz.setTextColor(0xFF05681C);
        chronometerWorkDwa.setTextSize(20);
        chronometerWorkDwa.setTextColor(Color.DKGRAY);
        chronometerRest.setTextSize(20);
        chronometerRest.setTextColor(Color.DKGRAY);
    }

    private void goRest() {

        stopSaveBoolen = true;
        StopButton.setImageResource(R.drawable.stop);

        TheButton.setImageResource(R.drawable.rest);

        chronometerRest.setTextSize(25);
        chronometerRest.setTextColor(0xFFB73E02);

        chronometerWorkRaz.setTextSize(20);
        chronometerWorkRaz.setTextColor(Color.DKGRAY);
        chronometerWorkDwa.setTextSize(20);
        chronometerWorkDwa.setTextColor(Color.DKGRAY);

        lapWorkDwa = chronometerWorkDwa.getText().toString();

        mLapsWorksDwa++;

        //tworzy array list
        int position = 0;

        listaETWorkDwa.add(0, new ElementyTreningu("W.", mLapsWorksDwa, lapWorkDwa));

        //odswierza i pokazuje array list
        mAdapterWorkDwa.notifyItemChanged(position);
        mAdapterWorkDwa.notifyDataSetChanged();

        //dodawanie czasów
        int elapsedMillisWorkDwa = (int) (SystemClock.elapsedRealtime() - chronometerWorkDwa.getBase());

        intSumaWorkDwa = intSumaWorkDwa + elapsedMillisWorkDwa;
    }

    private void letsWorkRaz() {
        stopSaveBoolen = true;
        StopButton.setImageResource(R.drawable.stop);

        TheButton.setImageResource(R.drawable.workdwa);

        chronometerWorkRaz.setTextSize(25);
        chronometerWorkRaz.setTextColor(0xFF05681C);
        chronometerWorkDwa.setTextSize(20);
        chronometerWorkDwa.setTextColor(Color.DKGRAY);
        chronometerRest.setTextSize(20);
        chronometerRest.setTextColor(Color.DKGRAY);

        lapRest = chronometerRest.getText().toString();

        mLapsRest++;

        //tworzy array list
        int position = 0;

        listaETRest.add(0, new ElementyTreningu("R.", mLapsRest, lapRest));

        //odswierza i pokazuje array list
        mAdapterRest.notifyItemChanged(position);
        mAdapterRest.notifyDataSetChanged();

        //dodawanie czasów
        int elapsedMillisRest = (int) (SystemClock.elapsedRealtime() - chronometerRest.getBase());

        intSumaRest = intSumaRest + elapsedMillisRest;
    }

    private void letsWorkDwa() {

        stopSaveBoolen = true;
        StopButton.setImageResource(R.drawable.stop);
        TheButton.setImageResource(R.drawable.workraz);
        chronometerWorkDwa.setTextColor(0xFF05681C);
        chronometerWorkRaz.setTextSize(20);
        chronometerWorkRaz.setTextColor(Color.DKGRAY);
        chronometerRest.setTextSize(20);
        chronometerRest.setTextColor(Color.DKGRAY);

        lapWorkRaz = chronometerWorkRaz.getText().toString();

        mLapsWorksRaz++;

        //tworzy array list
        int position = 0;

        listaETWorkRaz.add(0, new ElementyTreningu("W.", mLapsWorksRaz, lapWorkRaz));

        //odswierza i pokazuje array list
        mAdapterWorkRaz.notifyItemChanged(position);
        mAdapterWorkRaz.notifyDataSetChanged();

        //dodawanie czasów

        int elapsedMillisWorkRaz = (int) (SystemClock.elapsedRealtime() - chronometerWorkRaz.getBase());

        intSumaWorkRaz = intSumaWorkRaz + elapsedMillisWorkRaz;
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

    private void onClickStopWorkRaz() {

        if (runningWorkRaz) {
            chronometerWorkRaz.stop();
            pauseoffsetWorkRaz = SystemClock.elapsedRealtime() - chronometerWorkRaz.getBase();
            runningWorkRaz = false;
        }
    }

    private void onClickStopWorkDwa() {

        if (runningWorkDwa) {
            chronometerWorkDwa.stop();
            pauseoffsetWorkDwa = SystemClock.elapsedRealtime() - chronometerWorkDwa.getBase();
            runningWorkDwa = false;
        }
    }

    private void onClickStartWrokRaz() {

        if (!runningWorkRaz) {
            chronometerWorkRaz.setBase(SystemClock.elapsedRealtime() - pauseoffsetWorkRaz);
            chronometerWorkRaz.start();
            runningWorkRaz = true;
        }
    }

    private void onClickStartWrokDwa() {

        if (!runningWorkDwa) {
            chronometerWorkDwa.setBase(SystemClock.elapsedRealtime() - pauseoffsetWorkDwa);
            chronometerWorkDwa.start();
            runningWorkDwa = true;
        }
    }

    public void onClickResetWorkRaz() {

        chronometerWorkRaz.setBase(SystemClock.elapsedRealtime());
        pauseoffsetWorkRaz = 0;
    }

    public void onClickResetWorkDwa() {

        chronometerWorkDwa.setBase(SystemClock.elapsedRealtime());
        pauseoffsetWorkDwa = 0;
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
        listaETWorkRaz = new ArrayList<ElementyTreningu>();
        mAdapterWorkRaz = new AdapterET(listaETWorkRaz);
        mRecyclerViewWorkRaz.setAdapter(mAdapterWorkRaz);
        mAdapterWorkRaz.notifyDataSetChanged();

        //tworzy array list - musi bo sie wyjebie
        listaETWorkDwa = new ArrayList<ElementyTreningu>();
        mAdapterWorkDwa = new AdapterET(listaETWorkDwa);
        mRecyclerViewWorkDwa.setAdapter(mAdapterWorkDwa);
        mAdapterWorkDwa.notifyDataSetChanged();

        mLapsWorksRaz = 0;
        mLapsWorksDwa = 0;
        mLapsRest = 0;

        intSumaWorkRaz = 0;
        intSumaWorkDwa = 0;
        intSumaRest = 0;

        stopSaveBoolen = true;
        StopButton.setImageResource(R.drawable.stop);

        runningRest = false;
        runningWorkRaz = false;
        runningWorkDwa = false;
        TheButton.setImageResource(R.drawable.start);

        chronometerRest.stop();
        onClickResetRest();
        chronometerWorkRaz.stop();
        onClickResetWorkRaz();
        chronometerWorkDwa.stop();
        onClickResetWorkDwa();

        chronometerWorkRaz.setTextSize(20);
        chronometerWorkRaz.setTextColor(Color.DKGRAY);

        chronometerWorkDwa.setTextSize(20);
        chronometerWorkDwa.setTextColor(Color.DKGRAY);

        chronometerRest.setTextSize(20);
        chronometerRest.setTextColor(Color.DKGRAY);
    }

    //funkcje 30 60 senud

    public void onSzescdziesiatS() {
        szescdziesiatSekundOn.setImageResource(R.drawable.szescdziesiaton);
        szescdziesiatSekundRestBoolen = true;
    }

    public void offSzescdziesiatS() {
        szescdziesiatSekundOn.setImageResource(R.drawable.szescdziesiatoff);
        szescdziesiatSekundRestBoolen = false;
    }

    public void onTrzydziesciS() {
        trzydziesciSekundOn.setImageResource(R.drawable.trzydziescion);
        trzysziesciSekundRestBoolean = true;
    }

    public void offTrzysiesciS() {
        trzydziesciSekundOn.setImageResource(R.drawable.trzydziescioff);
        trzysziesciSekundRestBoolean = false;
    }

    //recycler view
    public void createRecyclerViewWorkRaz() {
        mRecyclerViewWorkRaz = findViewById(R.id.workRazRecyclerView);
        mRecyclerViewWorkRaz.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapterWorkRaz = new AdapterET(listaETWorkRaz);

        mRecyclerViewWorkRaz.setLayoutManager(mLayoutManager);
        mRecyclerViewWorkRaz.setAdapter(mAdapterWorkRaz);
    }

    public void createRecyclerViewWorkDwa() {
        mRecyclerViewWorkDwa = findViewById(R.id.workDwaRecyclerView);
        mRecyclerViewWorkDwa.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapterWorkDwa = new AdapterET(listaETWorkDwa);

        mRecyclerViewWorkDwa.setLayoutManager(mLayoutManager);
        mRecyclerViewWorkDwa.setAdapter(mAdapterWorkDwa);
    }

    public void createRecyclerViewRest() {
        mRecyclerViewRest = findViewById(R.id.restRecyclerView);
        mRecyclerViewRest.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapterRest = new AdapterET(listaETRest);

        mRecyclerViewRest.setLayoutManager(mLayoutManager);
        mRecyclerViewRest.setAdapter(mAdapterRest);
    }

    public void Stop() {

        if (!stopSaveBoolen) {

            listaCW = new ArrayList<ElementyListyCwiczen>();

            String przekazywanNazwaCwiczeniaPierwszego = telebimRaz.getText().toString();
            String przekazywanNazwaCwiczeniaDrugiego = telebimDwa.getText().toString();
            String przekazywanNazwaRest = telebimRest.getText().toString();

            String przekazywanNazwaCwiczenia = przekazywanNazwaCwiczeniaPierwszego + ", " + przekazywanNazwaCwiczeniaDrugiego;

            int secsWRaz = intSumaWorkRaz;
            int minsWRaz = intSumaWorkRaz / 60000;

            int secsWDwa = intSumaWorkDwa;
            int minsWDwa = intSumaWorkDwa / 60000;

            int secsW = secsWRaz + secsWDwa;
            int minsW = (intSumaWorkRaz + intSumaWorkDwa) / 60000;

            String sekundyWork = String.format("%02d",

                    TimeUnit.MILLISECONDS.toSeconds(secsW) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(secsW)));

            int secsR = intSumaRest;
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
            chronometerWorkRaz.stop();
            chronometerWorkDwa.stop();

            chronometerWorkRaz.setTextSize(20);
            chronometerWorkDwa.setTextColor(Color.DKGRAY);

            chronometerRest.setTextSize(20);
            chronometerRest.setTextColor(Color.DKGRAY);
        }
    }

    public void ustawienieRestow() {

        if (trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
            onTrzydziesciS();

            //chrono na 30s
            Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 30s", Toast.LENGTH_SHORT).show();

        } else if (!trzysziesciSekundRestBoolean && !szescdziesiatSekundRestBoolen) {
            offTrzysiesciS();
            offSzescdziesiatS();

            //chrono zerowy
            Toast.makeText(ActivityIleCwiczySuperDouble.this, "USER REST", Toast.LENGTH_SHORT).show();

        } else if (!trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
            offTrzysiesciS();
            onSzescdziesiatS();

            //chrono 60s
            Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 60s", Toast.LENGTH_SHORT).show();

        } else if (trzysziesciSekundRestBoolean && szescdziesiatSekundRestBoolen) {
            onTrzydziesciS();
            onSzescdziesiatS();

            //chrono 90s
            Toast.makeText(ActivityIleCwiczySuperDouble.this, "REST period 90s", Toast.LENGTH_SHORT).show();
        }
    }
}