package com.ubosque.deliverylab.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserAddress.class}, version = 1)
public abstract class DeliveryDatabase extends RoomDatabase {

    private static volatile DeliveryDatabase INSTANCE;

    public static DeliveryDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (DeliveryDatabase.class) {
                if(INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DeliveryDatabase.class, "delivery_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();

}

