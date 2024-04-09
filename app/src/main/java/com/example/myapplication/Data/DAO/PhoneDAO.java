package com.example.myapplication.Data.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.Data.Models.PhoneModel;

import java.util.List;

@Dao
public interface PhoneDAO{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addPhone(PhoneModel phone);

    @Query("SELECT * FROM phones")
    LiveData<List<PhoneModel>> getAllPhones();
    @Delete
    void deletePhone(PhoneModel phoneModel);
    @Query("DELETE FROM phones")
    void deleteAll();
    @Update
    void updatePhone(PhoneModel phone);
}
