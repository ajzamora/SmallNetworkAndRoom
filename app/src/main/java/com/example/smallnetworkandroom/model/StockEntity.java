package com.example.smallnetworkandroom.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.smallnetworkandroom.viewmodels.Converters;

import java.util.Date;
import java.util.HashMap;

@Entity(tableName = "stock_table")
public class StockEntity {

    @PrimaryKey
    @NonNull
    public String ticker;

    public StockEntity(@NonNull String ticker) {
        this.ticker = ticker;
    }

    public Float yearlyHigh;
    public Float yearlyLow;
    public String ebitda;
    public String marketCap;
    public int volume;
    public Float earningsShare;
    public Float bookValue;
    public Float lastTradePrice;

    @Nullable
    public HashMap<Date, Float> stockHistory;

    public HashMap<Date, Float> grossMargin;
    public HashMap<Date, Float> returnOnAssets;
    public HashMap<Date, Float> returnOnEquity;
    public HashMap<Date, Float> currentRatio;
    public HashMap<Date, Float> debtToEquity;

}
