package com.example.myapplication.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Data.DTO.DownloadInfo;
import com.example.myapplication.Data.DTO.ProgressInfo;
import com.example.myapplication.R;
import com.example.myapplication.Services.FileManagerService;
import com.example.myapplication.databinding.ActivityLab4Binding;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

import android.Manifest;
public class Lab4Activity extends AppCompatActivity {

    private ActivityLab4Binding binding;
    private static final String lab4TAG = Lab4Activity.class.getSimpleName();
    private static final int STORAGE_PERMISSION_CODE = 23;
    private final FileProgressReceiver fileProgressReveicer = new FileProgressReceiver();

    @Override
    protected void onStart() {
        registerReceiver(fileProgressReveicer, new IntentFilter(FileManagerService.ACTION_BROADCAST));
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLab4Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);


        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Pobieranie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.fileInfoButton.setOnClickListener( v -> fileInfoDownload());
        binding.fileDownloadButton.setOnClickListener( v -> startFileManagerService());
//        binding.cancelDownloadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FileManagerService.isDownloading = false;
//            }
//        });

    }

    @Override
    protected void onStop() {
        unregisterReceiver(fileProgressReveicer);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void fileInfoDownload() {
//        nie działa wczytujac link z pola z widoku, na sztywno nie ma problemu ????
//        String urlAddress = binding.fileAddressEditText.getText().toString();
        String urlAddress = "https://cdn.kernel.org/pub/linux/kernel/v5.x/linux-5.4.36.tar.xz";
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Log.d(lab4TAG, "fileInfoDownload() - adres: " + urlAddress);
        CompletableFuture.supplyAsync( () -> {
            HttpsURLConnection connection = null;
            try {
                URL url = new URL(urlAddress);
                Log.d(lab4TAG, "fileInfoDownload()/lambda - adres jako obiekt typu URL = " + url);
                connection = (HttpsURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                double fileSize = Math.round((connection.getContentLength()/1048576.0)*100) / 100.0;
                String fileType = connection.getContentType();
                Log.d(lab4TAG, "rozmiar: " + fileSize + "\ntyp pliku:" + fileType);
                return new DownloadInfo(fileType, (fileSize) + "MB");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) connection.disconnect();
            }

            return null;
        }, executorService).thenAccept(result -> {
            runOnUiThread(() -> {
                binding.fileSizeTextView.setText(result.fileSize());
                binding.fileTypeTextView.setText(result.fileType());
            });
        });
    }

    private void startFileManagerService() {
        Log.d(FileManagerService.TAG, "startFileManagerService() - starting service");
        Intent intent = new Intent(this, FileManagerService.class);
        intent.putExtra("URL", binding.fileAddressEditText.getText().toString());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            startService(intent);
            Log.d(lab4TAG, "startFileManagerService() - service started");
        } else {
            startService(intent);
            Log.d(lab4TAG, "startFileManagerService() - service started");
        }
    }
    class FileProgressReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ProgressInfo progressInfo = intent.getParcelableExtra("progress");
            Log.i("PROGRESS INFO", progressInfo.toString());

            Double downloadedMB = Math.round((progressInfo.getDownloadedBytes()/1048576.0)*100) / 100.0;
            String temp = String.valueOf(downloadedMB) + "MB";
            binding.downloadedBytesTextView.setText(temp);

            binding.progressBar.setMax(progressInfo.getSize());
            binding.progressBar.setProgress(progressInfo.getDownloadedBytes(), true);

            if(progressInfo.getStatus().equals("Running")){
                binding.fileDownloadButton.setText("Pobieranie...");
            } else {
                binding.fileDownloadButton.setText("Pobierz plik");
            }
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("fileAddress", binding.fileAddressEditText.getText().toString());
        outState.putString("fileSize", binding.fileSizeTextView.getText().toString());
        outState.putString("fileType", binding.fileTypeTextView.getText().toString());
        outState.putString("downloadedBytes", binding.downloadedBytesTextView.getText().toString());
        outState.putInt("progress", binding.progressBar.getProgress());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        binding.fileAddressEditText.setText(savedInstanceState.getString("fileAddress"));
        binding.fileSizeTextView.setText(savedInstanceState.getString("fileSize"));
        binding.fileTypeTextView.setText(savedInstanceState.getString("fileType"));
        binding.downloadedBytesTextView.setText(savedInstanceState.getString("downloadedBytes"));
        binding.progressBar.setProgress(savedInstanceState.getInt("progress"));
        super.onRestoreInstanceState(savedInstanceState);
    }
}