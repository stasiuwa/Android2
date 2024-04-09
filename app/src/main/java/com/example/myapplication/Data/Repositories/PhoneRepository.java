package com.example.myapplication.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.myapplication.Data.DAO.PhoneDAO;
import com.example.myapplication.Data.Databases.PhoneRoomDatabase;
import com.example.myapplication.Data.Models.PhoneModel;

import java.util.List;

public class PhoneRepository {
    private PhoneDAO mPhoneDAO;
    private LiveData<List<PhoneModel>> mAllPhones;


    public PhoneRepository(Application application) {
        PhoneRoomDatabase phoneRoomDatabase = PhoneRoomDatabase.getDatabase(application);
//        Repozytorium korzysta z obiektu DAO do odwołań do bazy
        mPhoneDAO = phoneRoomDatabase.phoneDAO();
        mAllPhones = mPhoneDAO.getAllPhones();
    }

    /**
     * Dodanie telefonu do bazy
     * @param phone obiekt telefonu
     */
    public void addPhone(PhoneModel phone){
        PhoneRoomDatabase.databaseWriteExecutor.execute( () -> {
            mPhoneDAO.addPhone(phone);
        });
    }

    /**
     * @return wszystkie elementy z bazy
     */
    public LiveData<List<PhoneModel>> getAllPhones() {
        return mAllPhones;
    }

    /**
     * Usuniecie telefonu z bazy
     * @param phone telefon do usuniecia
     */
    public void deletePhone(PhoneModel phone) {
        PhoneRoomDatabase.databaseWriteExecutor.execute( () -> {
            mPhoneDAO.deletePhone(phone);
        });
    }

    /**
     * Skasowanie wszystkich elementów z bazy za pomoca obiektu DAO
     */
    public void deleteAll() {
        PhoneRoomDatabase.databaseWriteExecutor.execute( () -> {
            mPhoneDAO.deleteAll();
        });
    }

    /**
     * Zakutalizuj dane o telefonie
     * @param phone obiekt z bazy do zaktualizowania
     */
    public void updatePhone(PhoneModel phone){
        PhoneRoomDatabase.databaseWriteExecutor.execute( () -> {
            mPhoneDAO.updatePhone(phone);
        });
    }
}
