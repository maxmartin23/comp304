package com.example.MaxMartinNamiraMalek_COMP304_002_Lab5_Ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.ArrayList;

public class RestaurantsActivity extends AppCompatActivity {
    JSONObject results;
    ListView restaurantList;
    ArrayList<String> restaurantNames = new ArrayList<String>();
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        restaurantList = (ListView)findViewById(R.id.cuisines);
       // String url = "https://maps.googleapis.com/maps/api/place/search/json?location=43.757255,-79.445934&radius=1000&types=restaurant&key=AIzaSyCg11oWdhpZ-kXaz9fecP0y5T8VCmYdSys";
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + getIntent().getStringExtra("cuisine") + "+Restaurant&sensor=true&location=43.757255,-79.445934&radius=1000&key=AIzaSyCg11oWdhpZ-kXaz9fecP0y5T8VCmYdSys";
        new JsonTask().execute(url);
        progress = new ProgressDialog(this);
        progress.setTitle("Getting restaurants");
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();
    }

    private void listSetup()
    {
        JSONArray restaurants = null;
        try {
            restaurants = results.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < restaurants.length(); ++i){
            try {
                restaurantNames.add(restaurants.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurantNames);
        restaurantList.setAdapter(adapter);
        restaurantList.setOnItemClickListener((parent, view, position, id) -> {
            double lat = 0;
            double lng = 0;
            try {
                JSONObject location = results.getJSONArray("results").getJSONObject(position).getJSONObject("geometry").getJSONObject("location");
                lat = location.getDouble("lat");
                lng = location.getDouble("lng");
                String restaurantName = restaurantNames.get(position);
                Toast.makeText(this, ""+ lat + ", " + lng, Toast.LENGTH_SHORT).show();
                //TODO create map activity and putExtra lat, lng, restaurantName
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.println(100, "", result);
            try {
                progress.dismiss();
                results = new JSONObject(result);
                listSetup();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}