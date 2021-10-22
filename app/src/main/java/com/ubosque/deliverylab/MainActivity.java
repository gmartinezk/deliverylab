package com.ubosque.deliverylab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.ubosque.deliverylab.adapters.StoreAdapter;
import com.ubosque.deliverylab.model.Store;
import com.ubosque.deliverylab.services.DeliveryApi;
import com.ubosque.deliverylab.services.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    // MainThread / Background Thread

    class GetStoreTask extends AsyncTask<Void, Void, List<Store>> {

        @Override
        protected List<Store> doInBackground(Void... voids) {
            Retrofit instance = RetrofitClientInstance.getRetrofitInstance();

            DeliveryApi deliveryApi = instance.create(DeliveryApi.class);

            Call<List<Store>> listCall = deliveryApi.listStores();

            try {
                Response<List<Store>> storeListResponse = listCall.execute();
                List<Store> storeList = storeListResponse.body();
                return storeList;
            } catch (Exception e) {
                Log.e(TAG, "Error recuperando lista de tiendas", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Store> stores) {
            super.onPostExecute(stores);
            storeListView.setAdapter(new StoreAdapter(MainActivity.this, stores));
        }
    }

    private static final String TAG = "DeliveryLabMainActivity";

    private RecyclerView storeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storeListView = findViewById(R.id.store_layout_view);
        storeListView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        GetStoreTask storeTask = new GetStoreTask();

        storeTask.execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "En el metodo start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "En el metodo on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "En el metodo on pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "En el metodo on stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "En el metodo on destroy");
    }
}