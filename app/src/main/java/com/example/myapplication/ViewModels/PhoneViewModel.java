package com.example.myapplication.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.Data.Models.PhoneModel;
import com.example.myapplication.Data.Repositories.PhoneRepository;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {
    private final PhoneRepository mPhoneRepository;
    private final LiveData<List<PhoneModel>> mAllPhones;

    public PhoneViewModel(@NonNull Application application) {
        super(application);
        mPhoneRepository = new PhoneRepository(application);
        mAllPhones = mPhoneRepository.getAllPhones();
    }

    /**
     * Dodanie telefonu do repozytorium
     * @param phone obiekt do dodania
     */
    public void addPhone(PhoneModel phone) {
        mPhoneRepository.addPhone(phone);
    }

    /**
     * @return wszystkie obiekty z repozytorium
     */
    public LiveData<List<PhoneModel>> getAllPhones() {
        return mAllPhones;
    }

    /**
     * Usuwa obiekt z repozytorium
     * @param phone obiekt do usuniecia
     */
    public void deletePhone(PhoneModel phone){
        mPhoneRepository.deletePhone(phone);
    }

    /**
     * Usuwa wszystkie elementy z repozytorium
     */
    public void deleteAll(){
        mPhoneRepository.deleteAll();
    }

    /**
     * Aktualizuje dane o obiekcie w repozytorium
     * @param phone obiekt do aktualizacji
     */
    public void updatePhone(PhoneModel phone){
        mPhoneRepository.updatePhone(phone);
    }
}
