package com.example.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.covid19.R;
import com.example.covid19.data_classes.CountryStatus;

import java.util.Date;
import java.util.List;

public class DetailedCountryActivity extends AppCompatActivity {
TextView timeView;
String currentDateTimeString;
static List <CountryStatus> countryStatusesList;

    TextView newCountryConfirmedTV;
    TextView totalCountryConfirmedTV;

    TextView newCountryDeathsTV;
    TextView totalCountryDeathsTV;

    TextView newCountryRecoveredTV;
    TextView totalCountryRecoveredTV;

    TextView countryNameTV;

    public static final String COUNTRY_POSITION = "country_position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_country);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        int position = intent.getIntExtra(COUNTRY_POSITION , 0);

        newCountryConfirmedTV = findViewById(R.id.new_country_confirmedTV);
        totalCountryConfirmedTV = findViewById(R.id.total_country_confirmedTV);

        newCountryDeathsTV = findViewById(R.id.new_country_DeathsTV);
        totalCountryDeathsTV = findViewById(R.id.total_country_deathsTV);

        newCountryRecoveredTV = findViewById(R.id.new_country_recoveredTV);
        totalCountryRecoveredTV = findViewById(R.id.total_country_recoveredTV);

        countryNameTV = findViewById(R.id.country_nameTV);

       //CountryStatus countryStatus = LocationActivity.myData.get(64);
        CountryStatus countryStatus = countryStatusesList.get(position);
        newCountryConfirmedTV.setText(String.valueOf(countryStatus.getNewConfirmed()));
        totalCountryConfirmedTV.setText(String.valueOf(countryStatus.getTotalConfirmed()));

        newCountryDeathsTV.setText(String.valueOf(countryStatus.getNewDeaths()));
        totalCountryDeathsTV.setText(String.valueOf(countryStatus.getTotalDeaths()));

        newCountryRecoveredTV.setText(String.valueOf(countryStatus.getNewRecovered()));
        totalCountryRecoveredTV.setText(String.valueOf(countryStatus.getTotalRecovered()));
        actionBar.setTitle(countryStatus.getCountryName());
        countryNameTV.setText(countryStatus.getCountryName());
    }


}
