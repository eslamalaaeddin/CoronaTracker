package com.example.covid19;

import android.text.TextUtils;
import android.util.Log;

import com.example.covid19.data_classes.CountryStatus;
import com.example.covid19.data_classes.GlobalStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<GlobalStatus> fetchGlobalData(String requestUrl) {
        Log.i(LOG_TAG , "fetchEarthquakeData() Called : TEST");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of countries
        List<GlobalStatus> earthquakes = extractDataFromJSON(jsonResponse);

        return earthquakes;
    }

    //to convert URL string to a URL object

    private static URL createUrl (String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    //this method makes an HTTP request and returns a json response

    private static String makeHttpRequest (URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the status JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //this method reads the data stream returned with the response and convert it to a immutable string

    private static String readFromStream (InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<GlobalStatus> extractDataFromJSON (String statusJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(statusJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding countries to.
        List<GlobalStatus> globalStatusList = new ArrayList<>();

        try {

            JSONObject baseResponse = new JSONObject(statusJSON);

            JSONObject global = baseResponse.getJSONObject("Global");

            int newConfirmed = global.getInt("NewConfirmed");
            int totalConfirmed = global.getInt("TotalConfirmed");


            int newDeaths = global.getInt("NewDeaths");
            int totalDeaths = global.getInt("TotalDeaths");

            int newRecovered = global.getInt("NewRecovered");
            int totalRecovered = global.getInt("TotalRecovered");

                //Creating a GlobalStatus object

                GlobalStatus globalStatus = new GlobalStatus(newConfirmed , totalConfirmed , newDeaths , totalDeaths , newRecovered , totalRecovered);

                //adding a GlobalStatus to the list

            globalStatusList.add(globalStatus);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return globalStatusList ;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    public static List<CountryStatus> fetchCountriesData (String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of countries
        List<CountryStatus> countryStatusList = extractCountriesDataFromJSON(jsonResponse);

        // Return the list of countries
        return countryStatusList;

    }

    private static List<CountryStatus> extractCountriesDataFromJSON (String statusJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(statusJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding countries to
        List<CountryStatus> countryStatusList = new ArrayList<>();

        try {

            JSONObject baseResponse = new JSONObject(statusJSON);

            JSONArray countries =baseResponse.getJSONArray("Countries");

         for (int i = 0 ; i < countries.length() ; i++) {

             JSONObject currentCountry = countries.getJSONObject(i);


             String countryName = currentCountry.getString("Country");

             int newConfirmed = currentCountry.getInt("NewConfirmed");
             int totalConfirmed = currentCountry.getInt("TotalConfirmed");

             int newDeaths = currentCountry.getInt("NewDeaths");
             int totalDeaths = currentCountry.getInt("TotalDeaths");

             int newRecovered = currentCountry.getInt("NewRecovered");
             int totalRecovered = currentCountry.getInt("TotalRecovered");

             CountryStatus countryStatus = new CountryStatus(newConfirmed
                     , totalConfirmed
                     , newDeaths ,
                     totalDeaths
                     , newRecovered
                     , totalRecovered
                     ,countryName);

             countryStatusList.add(countryStatus);
         }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        DetailedCountryActivity.countryStatusesList = countryStatusList;

        return countryStatusList ;
    }

}
