package com.ubosque.deliverylab.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ubosque.deliverylab.database.DeliveryRepository;
import com.ubosque.deliverylab.database.UserAddress;

import java.util.List;

public class UserAddressViewModel extends AndroidViewModel {

    private DeliveryRepository deliveryRepository;

    public UserAddressViewModel(@NonNull Application application) {
        super(application);
        deliveryRepository = new DeliveryRepository(application);
    }

    public LiveData<List<UserAddress>> getAllUserAddress() {
        return deliveryRepository.getAllUserAddress();
    }

    public void insert(UserAddress userAddress) { deliveryRepository.insert(userAddress); }

}
