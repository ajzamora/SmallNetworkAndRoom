package com.example.smallnetworkandroom.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.smallnetworkandroom.viewmodels.Converters;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * We dont need livedata if room is only use for cache, however in our app
 * we need livedata as were using one database for our data and its mutable
 * because we're relying on 4 api calls to get the data
 * this pose a question, which one is less costly
 * having one mulitple database where data is not livedata or having one database with livedata
 * answer might be obvious but good to keep in mind
 */
@Dao
@TypeConverters({Converters.class})
public interface StockEntityDAO {

    @Query("SELECT * FROM stock_table WHERE ticker = :symbol")
    public LiveData<StockEntity> loadSpecificStock(String symbol);

    @Query("SELECT * FROM stock_table")
    public LiveData<List<StockEntity>> loadAllStock();

    @Query("SELECT stockHistory FROM stock_table WHERE ticker = :symbol")
    public LiveData<String> loadStockHistoryJSON(String symbol);

    @Query("UPDATE stock_table SET stockHistory=:newHistory WHERE ticker = :symbol")
    void updateStockHistory(HashMap<Date, Float> newHistory, String symbol);

    //TODO: need query for and update for each data, otherwise,
    // new Entity for each network call to minimize query and confusion

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertStock(StockEntity stock);

    //I think needed when user swipe
    @Delete
    public void deleteStock(StockEntity stock);

    //ONLY USE FOR TESTING
    @Query("DELETE FROM stock_table")
    void deleteAll();


}
