package com.ubosque.deliverylab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
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
    private BroadcastReceiver broadcastReceiver;

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
            if(stores == null) {
                stores = new ArrayList<>();
            }
            storeListView.setAdapter(new StoreAdapter(MainActivity.this, stores));
        }
    }

    private static final String TAG = "DeliveryLabMainActivity";

    private RecyclerView storeListView;
    private TextView userAddressTextView;

    private String currentAddress = "No Seleccionado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storeListView = findViewById(R.id.store_layout_view);
        userAddressTextView = findViewById(R.id.txtUserAddress);

        userAddressTextView.setOnClickListener(view ->
                startActivity(new Intent(this, SetAddressActivity.class)));

        storeListView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        GetStoreTask storeTask = new GetStoreTask();

        storeTask.execute();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");

                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(message)
                        .setTitle("Delivery UBosque")
                        .setPositiveButton("OK", (x, y) -> {})
                        .create().show();
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        userAddressTextView.setText(currentAddress);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        Log.d(TAG, "token is " + token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
        Log.i(TAG, "En el metodo start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "En el metodo on resume");
        IntentFilter intentFilter = new IntentFilter("INTENT_ACTION_SEND_MESSAGE");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "En el metodo on pause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
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