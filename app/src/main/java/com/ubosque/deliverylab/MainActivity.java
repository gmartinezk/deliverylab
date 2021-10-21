package com.ubosque.deliverylab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.ubosque.deliverylab.adapters.StoreAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DeliveryLabMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView storeListView = findViewById(R.id.store_layout_view);
        String[] dataSet = {"El corral", "KFC", "Fridays"};
        storeListView.setAdapter(new StoreAdapter(dataSet));
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