package com.example.smallnetworkandroom;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.smallnetworkandroom.model.HistoricalPriceResponse;
import com.example.smallnetworkandroom.model.Results;
import com.example.smallnetworkandroom.model.StockEntity;
import com.example.smallnetworkandroom.model.StockEntityDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockRepository {

    private static StockRepository instance;
    private StockEntityDAO stockDao;
    private APIEndpoints apiService;

    //this one can be mediator because we can keep on adding stockList
    private final MediatorLiveData<List<StockEntity>> currentStockList = new MediatorLiveData<>();

    //Placeholder for current Stock
    MediatorLiveData<String> stockHistory = new MediatorLiveData<>();

    private StockRepository(Context context) {
        StockRoomDatabase db = StockRoomDatabase.getDataBase(context.getApplicationContext());
        stockDao = db.stockDao();
        apiService = ServiceBuilder.buildService(APIEndpoints.class);

        //right away observe database
        //CurrentStockList is working now
        LiveData<List<StockEntity>> stockSource = stockDao.loadAllStock();
        currentStockList.addSource(stockSource, value -> {
            currentStockList.setValue(value);
        });

    }

    public static StockRepository getInstance(Context context) {
        if (instance == null) {
            instance = new StockRepository(context);
        }
        return instance;
    }


    //Testing room on create
    public MediatorLiveData<List<StockEntity>> getAvailableStocks() {
        return currentStockList;
    }

    public LiveData<String> getStockHistoryWithStock() {
        return stockHistory;
    }



    public void fetchAndSave(String symbol) {
        Log.d("ASDF", "addSource to stockHistory ticker is " + symbol);
        final LiveData<String> dbStockHistory = stockDao.loadStockHistoryJSON(symbol);
        stockHistory.setValue(null);
        stockHistory.addSource(dbStockHistory, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s == null || TextUtils.isEmpty(s) || s.equals("null")) {
                    //fetch data from api
                    Log.d("ASDF", "StockRepository fetchAndSave Stock with ticker is null so fetching");
                    fetchFromNetwork(symbol);
                } else {
                    stockHistory.removeSource(dbStockHistory);
                    stockHistory.setValue(s);
                    Log.d("ASDF", "StockRepository fetchAndSave onChange Stock not null, no changing anythin" + s);
                }
            }
        });
    }

    private void fetchFromNetwork(String symbol) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.d("ASDF", "StockRepository fetchFromNetwork now fetching network for stockhistory");
                apiService.getStockHistory(symbol).enqueue(new Callback<HistoricalPriceResponse>() {
                    @Override
                    public void onResponse(Call<HistoricalPriceResponse> call, Response<HistoricalPriceResponse> response) {
                        HashMap<Date, Float> newStockHistory = response.body().data.attributes.result;
                        Log.d("ASDF", "StockRepository fetchFromNetwork stockHistory from network " + newStockHistory);
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("ASDF", "StockRepository fetchFromNetwork Stock updated on dba");
                                stockDao.updateStockHistory(newStockHistory, symbol);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<HistoricalPriceResponse> call, Throwable t) {
                        Log.d("ASDF", "StockRepository retrofit failure why" +  t);
                    }
                });
            }
        });
    }


}
