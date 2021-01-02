package com.example.smallnetworkandroom.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.smallnetworkandroom.StockRepository;
import com.example.smallnetworkandroom.model.StockEntity;

import java.util.List;

public class MainActViewModel extends AndroidViewModel {
    private final LiveData<List<StockEntity>> mStockList;
    private StockRepository mStockRepo;

    public MainActViewModel(@NonNull Application application) {
        super(application);
        mStockRepo = StockRepository.getInstance(application.getApplicationContext());
        mStockList = mStockRepo.getAvailableStocks();
    }

    public LiveData<List<StockEntity>> getAvailableStockList() {
        return mStockList;
    }


}
