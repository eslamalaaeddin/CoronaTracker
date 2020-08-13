package com.example.covid19.data_loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;

import com.example.covid19.activities.MainActivity;
import com.example.covid19.QueryUtils;
import com.example.covid19.data_classes.CountryStatus;

import java.util.List;

public class CountryStatusLoader extends AsyncTaskLoader <List <CountryStatus>> {

    public CountryStatusLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<CountryStatus> loadInBackground() {

        return QueryUtils.fetchCountriesData(MainActivity.REQUEST_URL);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }
}
