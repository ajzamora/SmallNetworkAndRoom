package com.example.smallnetworkandroom.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.smallnetworkandroom.StockRepository;


public class StockDataActivityViewModel extends AndroidViewModel {
    private StockRepository mStockRepo;
    private String ticker;
    public StockDataActivityViewModel(@NonNull Application application, String ticker) {
        super(application);
        this.ticker = ticker;
        mStockRepo = StockRepository.getInstance(application.getApplicationContext());
        //mStockRepo.fetchAndSave(ticker);
    }



    public LiveData<String> getStockHistoryWithSymbol() {
        mStockRepo.fetchAndSave(ticker);
        return mStockRepo.getStockHistoryWithStock();
    }
}


