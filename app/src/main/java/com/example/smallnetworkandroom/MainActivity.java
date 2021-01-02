package com.example.smallnetworkandroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.smallnetworkandroom.viewmodels.MainActViewModel;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private MainActViewModel mViewModel;
    Button button1;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ASDF", "MainActivity onCreate");

        button3 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button2);
        button1 = findViewById(R.id.button);

        button1.setText("WAITING");
        button2.setText("WAITING");
        button3.setText("WAITING");

        //Print list of current stocks
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainActViewModel.class);
        mViewModel.getAvailableStockList().observe(this, data -> {
            if (data != null && data.size() >= 3) {
                Log.d("ASDF", "what data is here? " + data);
                button1.setText(data.get(0).ticker);
                Log.d("ASDF","newly inserted button1 ticker" + button1.getText());
                button2.setText(data.get(1).ticker);
                Log.d("ASDF","newly inserted button2 ticker" + button2.getText());
                button3.setText(data.get(2).ticker);
                Log.d("ASDF","newly inserted button3 ticker" + button3.getText());
            }

        });


    }

    /** Called when the user taps the Send button */
    public void sendButton1(View view) {
        Intent intent = new Intent(this, StockDataActivity.class);
        intent.putExtra(EXTRA_MESSAGE, button1.getText());
        startActivity(intent);
    }

    public void sendButton2(View view) {
        Intent intent = new Intent(this, StockDataActivity.class);
        intent.putExtra(EXTRA_MESSAGE, button2.getText());
        startActivity(intent);
    }

    public void sendButton3(View view) {
        Intent intent = new Intent(this, StockDataActivity.class);
        intent.putExtra(EXTRA_MESSAGE, button3.getText());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ASDF", "MainActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ASDF", "MainActivity onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ASDF", "MainActivity onDestory");
    }
}


