package com.example.myapplication.Data.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phones")
public class PhoneModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private long phoneID;

    @ColumnInfo(name = "Brand")
    String brand;
    @ColumnInfo(name = "Model")
    String model;
    @ColumnInfo(name = "OS Version")
    String OSVersion;
    @ColumnInfo(name = "Website")
    String website;

    public PhoneModel(String brand, String model, String OSVersion, String website) {
        this.brand = brand;
        this.model = model;
        this.OSVersion = OSVersion;
        this.website = website;
    }
//jeżeli konieczne są dodatkowe konstruktory należy
//    je poprzedzić adnotacją @Ignore
//żeby biblioteka Room z nich nie korzystała

    public long getPhoneID() {
        return phoneID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public void setOSVersion(String OSVersion) {
        this.OSVersion = OSVersion;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
