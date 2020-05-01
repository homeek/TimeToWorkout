package pl.fitandyummy.timetoworkout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class activity_lista_cwiczen extends AppCompatActivity {


    private InterstitialAd mInterstitialAd;

    public ArrayList<ElementyListyCwiczen> listaCW, listaZapisanychCW;
    public ArrayList<ElementyListyTreningow> listaLTR;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Typeface text1;
    private String nazwaCwiczen, godzinaStartu;

    private String nazwaCwiczenDoListyTreningow = "  ";

    private TextView telebim;

    private EditText koment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cwiczen);

        loadData();

        createRecyclerView();

        koment = findViewById(R.id.edittext_koment);
        text1 = Typeface.createFromAsset(getAssets(), "fonts/KO.ttf");
        telebim = findViewById(R.id.telebim);
        telebim.setTypeface(text1);
        koment.setTypeface(text1);

        MobileAds.initialize(this,
                "ca-app-pub-7671780201496787~9718212776");
        // "ca-app-pub-3940256099942544~3347511713");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7671780201496787/1452709944");
        // mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                startActivity(new Intent(activity_lista_cwiczen.this, activity_lista_treningow.class));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                listaZapisanychCW.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyDataSetChanged();
            }
        });

        helper.attachToRecyclerView(mRecyclerView);

//przycisk powrotu na pasku
        ImageView back = (ImageView) findViewById(R.id.toolbarArrowbackBtn);

    }

    public void createRecyclerView() {
        mRecyclerView = findViewById(R.id.listaTreningowRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdapterListyCwiczen(listaZapisanychCW);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadData() {
        SharedPreferences sharedPreferences2 = getSharedPreferences("cyckicycki", 0);
        Gson gson2 = new Gson();
        String json2 = sharedPreferences2.getString("cyckidupa", null);
        Type type2 = new TypeToken<ArrayList<ElementyListyCwiczen>>() {
        }.getType();
        listaZapisanychCW = gson2.fromJson(json2, type2);

        SharedPreferences sharedPreferences = getSharedPreferences("dupadupa", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dupacycki", null);
        Type type = new TypeToken<ArrayList<ElementyListyCwiczen>>() {
        }.getType();
        listaCW = gson.fromJson(json, type);

        if (listaZapisanychCW == null) {
            listaZapisanychCW = new ArrayList<ElementyListyCwiczen>();
        }

        listaZapisanychCW.addAll(0, listaCW);

    }

    private void saveCW() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorr = preferences.edit();
        editorr.putString("xxx", "xxx");
        editorr.putInt("qqq", 2);
        editorr.putInt("zzz", 3);
        editorr.putString("rrr", "rrr");
        editorr.putString("fff", "fff");
        editorr.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("cyckicycki", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(listaZapisanychCW);
        editor.putString("cyckidupa", json2);
        editor.apply();
    }

    public void saveDoListyTreningow() {
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        final int h = c.get(Calendar.HOUR_OF_DAY);
        final int m = c.get(Calendar.MINUTE);

        listaLTR = new ArrayList<ElementyListyTreningow>();

        int i2;
        for (i2 = 0; i2 < listaZapisanychCW.size(); i2++) {

            nazwaCwiczen = listaZapisanychCW.get(i2).getNazwaCwiczeniaa();
            nazwaCwiczenDoListyTreningow = nazwaCwiczenDoListyTreningow + nazwaCwiczen + ",  ";
        }

        int i1;
        for (i1 = 0; i1 < listaZapisanychCW.size(); i1++) {

            godzinaStartu = listaZapisanychCW.get(i1).getGodzinaa();
        }

        String komentarz = koment.getText().toString();

        listaLTR.add(new ElementyListyTreningow(komentarz, nazwaCwiczenDoListyTreningow, godzinaStartu, h + ":" + String.format("%02d", m), day + "/" + String.format("%02d", month + 1) + "/" + year + " "));

        SharedPreferences preferences3 = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorr = preferences3.edit();
        editorr.putString("xxx", "xxx");
        editorr.putString("qqq", "test");
        editorr.putString("zzz", "test3");
        editorr.putString("rrr", "rrr");
        editorr.putString("fff", "fff");
        editorr.apply();

        SharedPreferences sharedPreferences3 = getSharedPreferences("SPdoListyTreningow", 0);
        SharedPreferences.Editor editor = sharedPreferences3.edit();
        Gson gson3 = new Gson();
        String json3 = gson3.toJson(listaLTR);
        editor.putString("json3dpListyTreningow", json3);
        editor.apply();

        listaLTR.clear();
    }

    public void doNastepnegoCwiczenia(View view) {
        saveCW();
        saveDoListyTreningow();

        Intent doNastepnegoCwiczenia = new Intent(getApplicationContext(), ActivityPreIleCwiczy.class);
        startActivity(doNastepnegoCwiczenia);
    }


    public void doListyTreningow(View view) {
        if (koment.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), " Insert workout description ", Toast.LENGTH_LONG).show();
        } else {

            if (mInterstitialAd.isLoaded()) {
                saveDoListyTreningow();
                listaZapisanychCW.clear();
                saveCW();
                mInterstitialAd.show();

            } else {
                Intent doListyTreningow = new Intent(getApplicationContext(), activity_lista_treningow.class);
                startActivity(doListyTreningow);

                saveDoListyTreningow();
                listaZapisanychCW.clear();

                saveCW();
            }
        }
    }
}