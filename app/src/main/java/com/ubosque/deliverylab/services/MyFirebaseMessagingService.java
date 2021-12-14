package com.ubosque.deliverylab.services;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "DeliveryLabFirebaseService";
    private static final String INTENT_ACTION_SEND_MESSAGE = "INTENT_ACTION_SEND_MESSAGE";

    @Override
    public void onNewToken(@NonNull String token) {
        Log.i(TAG, "Token refreshed: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String messageReceived = remoteMessage.getData().get("message");
        Log.i(TAG, "Message received is " + messageReceived);

        Intent intent = new Intent();
        intent.setAction(INTENT_ACTION_SEND_MESSAGE);
        intent.putExtra("message", messageReceived);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
