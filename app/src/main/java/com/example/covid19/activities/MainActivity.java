package com.example.covid19.activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covid19.R;
import com.example.covid19.data_classes.GlobalStatus;
import com.example.covid19.data_loaders.GlobalStatusLoader;
import com.example.covid19.data_adapters.GlobalStatusAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks <List<GlobalStatus>> {

    public static final String REQUEST_URL = "https://api.covid19api.com/summary";

    ListView listView;
    GlobalStatusAdapter adapter;
    ProgressBar progressBar;
    Button trackLocation;
    Loader loader;
    FrameLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent = findViewById(R.id.parent);
        trackLocation = findViewById(R.id.track_location);
        progressBar = findViewById(R.id.progress_bar);
        listView = findViewById(R.id.listView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkConnectivity()) {
            trackLocation.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "No internet connection!", Toast.LENGTH_LONG).show();
        }

        else {
            progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.VISIBLE);
            adapter = new GlobalStatusAdapter(this , R.layout.corona_layout_global , new ArrayList<GlobalStatus>());
            listView.setAdapter(adapter);
            getLoaderManager().initLoader(0 , null , MainActivity.this);
            trackLocation.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public Loader<List<GlobalStatus>> onCreateLoader(int id, @Nullable Bundle args) {
        return new GlobalStatusLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<GlobalStatus>> loader, List<GlobalStatus> data) {
        if ( adapter!= null && data!= null ) {
            progressBar.setVisibility(View.GONE);
            adapter.clear();
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<GlobalStatus>> loader) {
        adapter.clear();
    }

    private boolean checkConnectivity () {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                if (adapter!=null) {
                    adapter.clear();
                }
                if ( checkConnectivity()) {

                    adapter = new GlobalStatusAdapter(this , R.layout.corona_layout_global , new ArrayList<GlobalStatus>());
                    listView.setAdapter(adapter);
                    loader = getLoaderManager().initLoader(0, null, MainActivity.this);
                   // progressBar.setVisibility(View.VISIBLE);
                    trackLocation.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(this, "No internet connection!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    trackLocation.setVisibility(View.GONE);
                }


        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickTrackYourLocation (View view) {
        startActivity(new Intent(MainActivity.this , CountriesActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.clear();
            loader = getLoaderManager().initLoader(0, null, MainActivity.this);
        }
    }
}
