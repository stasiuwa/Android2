package com.example.myapplication.Data.Databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.Data.DAO.PhoneDAO;
import com.example.myapplication.Data.Models.PhoneModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {PhoneModel.class}, version = 1, exportSchema = false)
public abstract class PhoneRoomDatabase extends RoomDatabase {
    public abstract PhoneDAO phoneDAO();

    //    implementacja singeltona
    private static volatile PhoneRoomDatabase INSTANCE;

    public static PhoneRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (PhoneRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            PhoneRoomDatabase.class,
                            "phones")
//                            najprostrza migracja - skasowanie i utworzenie bazy od nowa
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    usługa wykonawcza do wykonywania zadań w osobnym wątku
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

//    obiekt obsługujący wywołania zwrotne (call backs)
//    związane ze zdarzeniami bazy danych
//    np. onCreate, onOpen
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

//      uruchamiane przy tworzeniu bazy (pierwsze
//      uruchomienie aplikacji, gdy baza nie istnieje)
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute( () -> {
                /*
                wykonanie operacji w osobnym wątku.
                Parametrem metody execute() jest obiekt implementujący interfejs Runnable,
                może być zastąpiony wyrażeniem lambda
                 */
                PhoneDAO dao = INSTANCE.phoneDAO();

                dao.addPhone(new PhoneModel("Google", "Pixel 6", "Android 12", "jbzd.com.pl"));
                dao.addPhone(new PhoneModel("Samsung", "Galaxy S21", "Android 12", "jbzd.com.pl"));
                dao.addPhone(new PhoneModel("Xiaomi", "Mi 11 Ultra", "Android 12", "jbzd.com.pl"));
                dao.addPhone(new PhoneModel("Sony", "Xperia Z1", "Android 4.3", "jbzd.com.pl"));
            });
        }
    };
}
