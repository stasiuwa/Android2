package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class GradeModel implements Parcelable {
    private String name;
    private int grade;

    public GradeModel(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    protected GradeModel(Parcel in) {
        name = in.readString();
        grade = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(grade);
    }

    public static final Creator<GradeModel> CREATOR = new Creator<GradeModel>() {
        @Override
        public GradeModel createFromParcel(Parcel in) {
            return new GradeModel(in);
        }

        @Override
        public GradeModel[] newArray(int size) {
            return new GradeModel[size];
        }
    };
}
