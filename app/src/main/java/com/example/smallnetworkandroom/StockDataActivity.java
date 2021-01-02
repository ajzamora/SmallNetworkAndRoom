package com.example.smallnetworkandroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.smallnetworkandroom.viewmodels.DateDeserializer;
import com.example.smallnetworkandroom.viewmodels.MyViewModelFactory;
import com.example.smallnetworkandroom.viewmodels.StockDataActivityViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;

public class StockDataActivity extends AppCompatActivity {
    private StockDataActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_data);
        Log.d("ASDF", "StockDataActivity OnCreate");
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.textView);
        textView.setText("Waiting for " + message);
        Log.d("ASDF", "StockDataActivity ticker is " + message);


        // Capture the layout's TextView and set the string as its text
        mViewModel = new ViewModelProvider(this, new MyViewModelFactory(this.getApplication(), message)).get(StockDataActivityViewModel.class);

        mViewModel.getStockHistoryWithSymbol().observe(this, data -> {


            if (data != null) {
                //set text to data
                Log.d("ASDF", "StockDataActivity not null stockHistory " + data);
                Log.d("ASDF", "StockDataActivity setting to textview as gson hashmap");
                textView.setText(data);
                //TODO: now make this data again a POJO
                Type mapType = new TypeToken<HashMap<Date, Float>>() {}.getType();

                Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();

                HashMap<Date, Float> stockHistoryMap = gson.fromJson(data, mapType);
                Log.d("ASDF", "StockDataActivity stock history as map " + stockHistoryMap.toString());
                Log.d("ASDF", "StockDataActivity stock history count " + stockHistoryMap.size());

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ASDF", "StockDataActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ASDF", "StockDataActivity onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ASDF", "StockDataActivity onDestory");
    }
}