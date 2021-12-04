package com.example.MaxMartinNamiraMalek_COMP304_002_Lab5_Ex1;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> cuisines = new ArrayList<String>();
        cuisines.add("Mexican");
        cuisines.add("Chinese");
        cuisines.add("Indian");
        cuisines.add("Italian");
        cuisines.add("Filipino");
        cuisines.add("Greek");
        cuisines.add("Korean");
        cuisines.add("Japanese");
        cuisines.add("Lebanese");
        cuisines.add("Cannabis");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cuisines);
        ((ListView)findViewById(R.id.cuisines)).setAdapter(adapter);

        ((ListView)findViewById(R.id.cuisines)).setOnItemClickListener((parent, view, position, id) -> {
            int patientId = position + 1;
            Intent intent = new Intent(this, RestaurantsActivity.class);
            intent.putExtra("cuisine", cuisines.get(position));
            startActivity(intent);
        });
        }
}