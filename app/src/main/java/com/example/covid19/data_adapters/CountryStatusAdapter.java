package com.example.covid19.data_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.covid19.R;
import com.example.covid19.data_classes.CountryStatus;

import java.util.List;

public class CountryStatusAdapter extends ArrayAdapter <CountryStatus>  implements Filterable {
    Context context;
    int resource;


    TextView newCountryConfirmedTV;
    TextView totalCountryConfirmedTV;

    TextView newCountryDeathsTV;
    TextView totalCountryDeathsTV;

    TextView newCountryRecoveredTV;
    TextView totalCountryRecoveredTV;

    TextView countryNameTV;



    TextView counterTv;

    int counter = 1;

    public CountryStatusAdapter(@NonNull Context context, int resource, @NonNull List<CountryStatus> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        CountryStatus countryStatus = getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.country_layout , parent , false);

                newCountryConfirmedTV = convertView.findViewById(R.id.new_country_confirmedTV);
                totalCountryConfirmedTV = convertView.findViewById(R.id.total_country_confirmedTV);

                newCountryDeathsTV = convertView.findViewById(R.id.new_country_DeathsTV);
                totalCountryDeathsTV = convertView.findViewById(R.id.total_country_deathsTV);

                newCountryRecoveredTV = convertView.findViewById(R.id.new_country_recoveredTV);
                 totalCountryRecoveredTV = convertView.findViewById(R.id.total_country_recoveredTV);

                 countryNameTV = convertView.findViewById(R.id.country_nameTV);

        countryNameTV.setText(countryStatus.getCountryName());

        return convertView;
    }


}
