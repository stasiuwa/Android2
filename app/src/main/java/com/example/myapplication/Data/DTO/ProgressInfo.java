package com.example.myapplication.Data.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProgressInfo implements Parcelable {

    private int downloadedBytes;
    private int size;
    private String status;


    @Override
    public String toString() {
        return "ProgressInfo{" +
                "downloadedBytes=" + downloadedBytes +
                ", size=" + size +
                ", status='" + status + '\'' +
                '}';
    }

    public int getDownloadedBytes() {
        return downloadedBytes;
    }

    public void setDownloadedBytes(int downloadedBytes) {
        this.downloadedBytes = downloadedBytes;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProgressInfo(int downloadedBytes, int size, String status) {
        this.downloadedBytes = downloadedBytes;
        this.size = size;
        this.status = status;
    }

    protected ProgressInfo(Parcel in) {
        downloadedBytes = in.readInt();
        size = in.readInt();
        status = in.readString();
    }

    public static final Creator<ProgressInfo> CREATOR = new Creator<ProgressInfo>() {
        @Override
        public ProgressInfo createFromParcel(Parcel in) {
            return new ProgressInfo(in);
        }

        @Override
        public ProgressInfo[] newArray(int size) {
            return new ProgressInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(downloadedBytes);
        dest.writeInt(size);
        dest.writeString(status);
    }
}
