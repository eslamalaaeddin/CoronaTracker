package com.example.covid19.data_loaders;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;

import com.example.covid19.activities.MainActivity;
import com.example.covid19.QueryUtils;
import com.example.covid19.data_classes.GlobalStatus;

import java.util.List;

public class GlobalStatusLoader extends AsyncTaskLoader <List <GlobalStatus>> {
    public GlobalStatusLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<GlobalStatus> loadInBackground() {

        return QueryUtils.fetchGlobalData(MainActivity.REQUEST_URL);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }
}
