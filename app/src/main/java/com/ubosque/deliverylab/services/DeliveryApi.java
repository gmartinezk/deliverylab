package com.ubosque.deliverylab.services;

import com.ubosque.deliverylab.model.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DeliveryApi {

    @GET("/stores")
    Call<List<Store>> listStores();

}
