package com.ubosque.deliverylab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.ubosque.deliverylab.adapters.AddressAdapter;
import com.ubosque.deliverylab.database.UserAddress;
import com.ubosque.deliverylab.viewmodels.UserAddressViewModel;

import java.util.Arrays;
import java.util.UUID;

public class SetAddressActivity extends AppCompatActivity {

    private static final String TAG = "DeliveryLabSetAddressActivity";
    private static final Integer MAPS_ACTIVITY_CODE = 1;

    private UserAddressViewModel userAddressViewModel;
    private RecyclerView addressListView;
    private UserAddress currentPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_address);

        addressListView = findViewById(R.id.address_list_view);

        addressListView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        userAddressViewModel = new ViewModelProvider(this).get(UserAddressViewModel.class);

        Places.initialize(getApplicationContext(), "AIzaSyBY17XFC3wb2QZR_T1FCxqlpjc2STEYnnY");
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.ADDRESS,
                Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.

                currentPlace = new UserAddress();
                currentPlace.setAddress(place.getAddress());
                currentPlace.setLatitude(place.getLatLng().latitude);
                currentPlace.setLongitude(place.getLatLng().longitude);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                Log.i(TAG, "Place coordinates: " + place.getLatLng());
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("latitude", place.getLatLng().latitude);
                intent.putExtra("longitude", place.getLatLng().longitude);
                startActivityForResult(intent, MAPS_ACTIVITY_CODE);
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        userAddressViewModel.getAllUserAddress().observe(this, userAddresses -> {
            Log.i(TAG, "User addresses is " + userAddresses);
            addressListView.setAdapter(new AddressAdapter(getApplicationContext(), userAddresses));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MAPS_ACTIVITY_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "Adicionar la dirección seleccionada al listado");
                String uuid = UUID.randomUUID().toString();
                currentPlace.setId(uuid);
                userAddressViewModel.insert(currentPlace);
            }
        }
    }
}