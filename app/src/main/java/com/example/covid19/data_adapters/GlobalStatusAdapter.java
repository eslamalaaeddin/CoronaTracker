package com.example.covid19.data_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.covid19.R;
import com.example.covid19.data_classes.GlobalStatus;

import java.util.List;

public class GlobalStatusAdapter extends ArrayAdapter <GlobalStatus> {

    Context context;
    int resource;

    TextView newConfirmedTV;
    TextView totalConfirmedTV;

    TextView newDeathsTV;
    TextView totalDeathsTV;

    TextView newRecoveredTV;
    TextView totalRecoveredTV;



    public GlobalStatusAdapter(@NonNull Context context, int resource, @NonNull List<GlobalStatus> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        GlobalStatus globalStatus = getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.corona_layout_global , parent , false);

        newConfirmedTV = convertView.findViewById(R.id.new_confirmedTV);
        totalConfirmedTV = convertView.findViewById(R.id.total_confirmedTV);

        newDeathsTV = convertView.findViewById(R.id.new_deathsTV);
        totalDeathsTV = convertView.findViewById(R.id.total_deathsTV);

        newRecoveredTV = convertView.findViewById(R.id.new_recoveredTV);
        totalRecoveredTV = convertView.findViewById(R.id.total_recoveredTV);


        /**
         * Setting TextView to the values returned from the API.
         */

        newConfirmedTV.setText(String.valueOf(globalStatus.getNewConfirmed()));
        totalConfirmedTV.setText(String.valueOf(globalStatus.getTotalConfirmed()));

        newDeathsTV.setText(String.valueOf(globalStatus.getNewDeaths()));
        totalDeathsTV.setText(String.valueOf(globalStatus.getTotalDeaths()));

        newRecoveredTV.setText(String.valueOf(globalStatus.getNewRecovered()));
        totalRecoveredTV.setText(String.valueOf(globalStatus.getTotalRecovered()));






        return convertView;
    }
}
