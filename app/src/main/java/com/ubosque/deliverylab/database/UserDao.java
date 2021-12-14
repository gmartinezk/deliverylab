package com.ubosque.deliverylab.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(UserAddress userAddress);

    @Query("SELECT * FROM user_address ORDER BY id")
    LiveData<List<UserAddress>> getAllUserAddress();

}
