package com.example.smallnetworkandroom;

import com.example.smallnetworkandroom.model.HistoricalPriceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface APIEndpoints {
    @Headers({"Ocp-Apim-Subscription-Key: 4b42d05faafc49098a9428db721823bd"})
    @GET("v1/company/{ticker}/prices")
    Call<HistoricalPriceResponse> getStockHistory(@Path("ticker") String ticker);

    //for stock quote
    //v1/company/{ticker}/quote


}
