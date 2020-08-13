package com.example.covid19.activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.DetailedCountryActivity;
import com.example.covid19.R;
import com.example.covid19.data_classes.CountryStatus;
import com.example.covid19.data_loaders.CountryStatusLoader;
import com.example.covid19.data_adapters.CountryStatusAdapter;

import java.util.ArrayList;
import java.util.List;



public class CountriesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks <List <CountryStatus>> {
    private static final String TAG="LocationActivity";

    RecyclerView countriesRecyclerView;
    CountriesAdapter countriesAdapter;
    CountryStatusAdapter countryAdapter;
    static List <CountryStatus> myData;
    List<CountryStatus> finalList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        countriesRecyclerView = findViewById(R.id.recycle_view);
        //important to show the size
        countryAdapter = new CountryStatusAdapter(this , R.layout.country_layout , new ArrayList<CountryStatus>());

        if (!checkConnectivity()) {
            Toast.makeText(this, "No internet available!", Toast.LENGTH_LONG).show();
        }

        else {
            getLoaderManager().initLoader(1, null, CountriesActivity.this);
        }
        
    }

    @NonNull
    @Override
    public Loader<List<CountryStatus>> onCreateLoader(int id, @Nullable Bundle args) {
        return new CountryStatusLoader(CountriesActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<CountryStatus>> loader, List<CountryStatus> data) {
        if ( countryAdapter!= null && data!= null ) {
            //progressBar.setVisibility(View.GONE);
            myData = data;
            Log.i(TAG, "ISLAM onCreate: "+myData.size());
            countryAdapter.clear();
            countryAdapter.addAll(data);

            //////////////////////////////////

            countriesAdapter = new CountriesAdapter(myData);

            countriesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            countriesRecyclerView.setAdapter(countriesAdapter);

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<CountryStatus>> loader) {
            countryAdapter.clear();
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

    class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> implements Filterable {

        List<CountryStatus> countriesData;
        List<CountryStatus> countriesDataFull;


        CountriesAdapter(List<CountryStatus> data) {
            countriesData = data;
            countriesDataFull = new ArrayList<>(countriesData);
        }

        @NonNull
        @Override
        public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item,parent,false);

            return new CountriesViewHolder(cardView);
        }

        @Override
        public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
            holder.countryName.setText(countriesData.get(holder.getAdapterPosition()).getCountryName());

        }

        @Override
        public int getItemCount() {
            return countriesData.size();
        }

        @Override
        public Filter getFilter() {
            return exampleFilter;
        }

        private Filter exampleFilter = new Filter() {
            @Override
            //background thread
            protected FilterResults performFiltering(CharSequence constraint) {
              List<CountryStatus> filteredList = new ArrayList<>();

              if (constraint == null || constraint.length() ==0) {
                  filteredList.addAll(countriesDataFull);
              }

              else{
                  String filterPattern = constraint.toString().toLowerCase().trim();
                  for (CountryStatus item : countriesDataFull) {
                      if (item.getCountryName().toLowerCase().contains(filterPattern)){
                           filteredList.add(item);
                      }
                  }

              }

              FilterResults results = new FilterResults();
              results.values = filteredList;
              return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countriesData.clear();
                countriesData.addAll((List)results.values);
                notifyDataSetChanged();
            }
        };

        class CountriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView countryName;
            public CountriesViewHolder(@NonNull View itemView) {
                super(itemView);
                countryName = itemView.findViewById(R.id.country_name_tv);

                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountriesActivity.this , DetailedCountryActivity.class);
                intent.putExtra(DetailedCountryActivity.COUNTRY_POSITION , getAdapterPosition());
                startActivity(intent);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                countriesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                countriesAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;

    }


}
