package com.example.myapplication.Data.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "phones")
public class PhoneModel implements Parcelable {
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

    @Ignore
    public PhoneModel(Parcel in) {
        this.phoneID = in.readLong();
        this.brand = in.readString();
        this.model = in.readString();
        this.OSVersion = in.readString();
        this.website = in.readString();
    }

    public void setPhoneID(long phoneID) {
        this.phoneID = phoneID;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(getPhoneID());
        dest.writeString(getBrand());
        dest.writeString(getModel());
        dest.writeString(getOSVersion());
        dest.writeString(getWebsite());
    }

    public static final Creator<PhoneModel> CREATOR = new Creator<PhoneModel>() {
        @Override
        public PhoneModel createFromParcel(Parcel source) {
            return new PhoneModel(source);
        }

        @Override
        public PhoneModel[] newArray(int size) {
            return new PhoneModel[size];
        }
    };
}
