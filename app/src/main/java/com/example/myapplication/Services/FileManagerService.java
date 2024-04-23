package com.example.myapplication.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.Activities.Lab4Activity;
import com.example.myapplication.Data.DTO.ProgressInfo;
import com.example.myapplication.R;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FileManagerService extends Service {

//    z wykładu strona 210 - konfiguracja klasy serwisu

    public static final String TAG = FileManagerService.class.getSimpleName();
    private static final String NOTIFICATION_CHANNEL_ID = "com.example.service.DOWNLOAD_NOTIFICATION_CHANNEL";
    private static final String NOTIFICATION_CHANNEL_NAME = "com.example.service.DOWNLOAD_NOTIFICATION_CHANNEL";
    private static final int NOTIFICATION_ID = 1;

    public static final String ACTION_BROADCAST = "com.example.service.broadcast";
    public static final String TIME_EXTRA = "com.example.service.broadcast";

    private NotificationManager mNotificationManager;
    private HandlerThread mServiceHandlerThread;
    private Handler mServiceThreadHandler;
    private Handler mMainServiceHandler;

    public static volatile boolean isDownloading = true;

    public FileManagerService() {}

    /**
     * Wykonuje jednorazową konfigurację, niezależnie od tego czy usługę uruchomiono
     * za pomocą startService() czy bindService()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        mServiceHandlerThread = new HandlerThread(TAG);
        mServiceHandlerThread.start();
        mServiceThreadHandler = new Handler(mServiceHandlerThread.getLooper());
        mMainServiceHandler = new Handler(Looper.getMainLooper());
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Android 0 wymaga kanału powiadomień, na wykladzie bylo 0 ale IDE nie rozpoznaje
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
//            utwórz kanał dla powiadomienia
            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()");
//        Toast.makeText(this, R.string.downloadingEnded, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind()");
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFICATION_ID, getNotification(0));
        Log.d(TAG,"onStartCommand() - startForeground");
        stopForeground(false);
        mServiceThreadHandler.post(() -> {
            try {
                Log.d(TAG, intent.getStringExtra("URL"));
                downloadFile(intent.getStringExtra("URL"));
            } catch (IOException e){
                e.printStackTrace();
            }
            Log.d(TAG, "onStartCommand() - stopped service");
            mMainServiceHandler.post(this::stopSelf);
        });
        return super.onStartCommand(intent, flags, startId);
    }

    private void downloadFile(String urlAddress) throws IOException {
        isDownloading = true;
//        urlAddress = "https://cdn.kernel.org/pub/linux/kernel/v5.x/linux-5.4.36.tar.xz";
        HttpsURLConnection connection = null;
        DataInputStream inputStream = null;
        FileOutputStream outputStream = null;
        int progress = 0;
        int fileSize = 0;
        try {
            Log.i(TAG, "/downloadFile() - started downloading");
            URL address = new URL(urlAddress);
            String fileName = urlAddress.substring(urlAddress.lastIndexOf("/"));
            File outputFile = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "Download" + fileName);
            if (outputFile.exists()) {
                outputFile.delete();
            }
            connection = (HttpsURLConnection) address.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            inputStream = new DataInputStream(connection.getInputStream());
            outputStream = new FileOutputStream(outputFile.getPath());
            byte[] buffer = new byte[1024];
            int bytesRead;
            fileSize = connection.getContentLength();

            float percentageStep = 1;
            int nextThreshold = (int) (fileSize * (percentageStep/100));
            int sumBytes = 0;

            while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1 && isDownloading) {
                outputStream.write(buffer, 0, bytesRead);
                sumBytes += bytesRead;
                if (sumBytes >= nextThreshold){
                    progress += sumBytes;
                    sumBytes = 0;
                    int percentage = (int) ((progress/ (float) fileSize) * 100);
                    updateNotification(percentage);
                    Log.i(TAG,"/downloadFile() - Downloading progress: " + percentage + "%");
                    sendBroadcast(progress, fileSize, "Downloading");
                nextThreshold = (int) (fileSize * (percentageStep / 100));
                }
            }
            Log.i(TAG, "/downloadFile() - Downloaded");
            updateNotification(progress);
            sendBroadcast(progress, fileSize, "Done");
//            Toast.makeText(this, R.string.downloadingEnded, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            sendBroadcast(progress, fileSize, "ERROR");
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) inputStream.close();
            if (outputStream != null) outputStream.close();
            if (connection != null) connection.disconnect();
        }
    }
//    USŁUGI - wysyłanie rozgłoszeń

    private void sendBroadcast(int progress, int fileSize, String status) {
        Intent broadcastIntent = new Intent(ACTION_BROADCAST);
        ProgressInfo progressInfo = new ProgressInfo(progress,fileSize,status);
        broadcastIntent.putExtra("progress", progressInfo);
        sendBroadcast(broadcastIntent);
        Log.d(TAG + "/sendBroadcast() - ", "sent progress: " + progressInfo + " using broadcast");
//        Log.d(TAG, "sendTimeBroadcast() - sent time: " + time + " using broadcast");
    }

    private Notification getNotification(int progress) {
        Log.d(TAG,"/getNotification() start");
        Intent intent = new Intent(getApplicationContext(), Lab4Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this, NOTIFICATION_CHANNEL_ID
        ).setContentTitle("Pobieranie pliku")
                .setContentText("Pobieranie: " + progress + "%")
                .setOngoing(true)
                .setProgress(100, progress, false)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setTicker("Notification ticker")
                .setWhen(System.currentTimeMillis())
                .setChannelId(NOTIFICATION_CHANNEL_ID);

        return builder.build();
    }
    private void updateNotification(int progress){
        Log.d(TAG,"/updateNotification() - aktualizacja powiadomienia pobierania");
        mNotificationManager.notify(NOTIFICATION_ID, getNotification(progress));
    }
    public static void stopDownloading() {
        isDownloading = false;
    }
}