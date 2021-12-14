package com.ubosque.deliverylab.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DeliveryRepository {

    private static final String TAG = "DeliveryMainActivity";

    private UserDao userDao;

    public DeliveryRepository(Application application) {
        DeliveryDatabase db = DeliveryDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public LiveData<List<UserAddress>> getAllUserAddress() {
        return userDao.getAllUserAddress();
    }

    public void insert(UserAddress userAddress) {
        new insertAsyncTask(userDao).execute(userAddress);
    }

    private static class insertAsyncTask extends AsyncTask<UserAddress, Void, Void> {

        private UserDao userDao;

        insertAsyncTask(UserDao dao) {
            userDao = dao;
        }

        @Override
        protected Void doInBackground(final UserAddress... params) {
            userDao.insert(params[0]);
            return null;
        }
    }

}