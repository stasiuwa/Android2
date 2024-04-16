package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.PhoneAdapter;
import com.example.myapplication.Data.Models.PhoneModel;
import com.example.myapplication.R;
import com.example.myapplication.ViewModels.PhoneViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PhoneDatabaseActivity extends AppCompatActivity {

    List<PhoneModel> mPhonesList;
    PhoneAdapter adapter;
    PhoneViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phone_database);

//        kom1 - samo sie dodalo, co to erobi nie wiem
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Telefony");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        ustawienie adaptera jako adapter listy
        adapter = new PhoneAdapter(this);
        RecyclerView phonesRecyclerView = findViewById(R.id.phonesRecyclerView);
        phonesRecyclerView.setAdapter(adapter);

        phonesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(PhoneViewModel.class);

//        aktywnosc ma obserwowac zmiany w liscie elementow przechowywancyh w LiveData, w momencie
//        zmiany ma byc ustawiana nowa lista w adapterze
        viewModel.getAllPhones().observe(this, phoneModels -> adapter.setPhoneList(phoneModels) );

        FloatingActionButton fab = findViewById(R.id.phonesFAB);
        fab.setOnClickListener( view -> {
            LaunchAddPhoneActivity();
        });

    }

//    utworzenie menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.phone_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    obsługa wybieranych opcji z menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.phoneDelete){
            viewModel.deleteAll();
            return true;
        } else if (id == R.id.phoneAdd) {
//            PhoneModel p = new PhoneModel("marka", "model", "4.2", "joljol.pl");
//            viewModel.addPhone(p);
            LaunchAddPhoneActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ActivityResultLauncher<Intent> AddPhoneActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Intent data = o.getData();
                    if (data.getExtras().getBoolean("success")){
                        PhoneModel phone = data.getParcelableExtra("phone");
                        viewModel.addPhone(phone);
                    }
                }
            }
    );

    private void LaunchAddPhoneActivity() {
        AddPhoneActivityResultLauncher.launch(new Intent(this, AddPhoneActivity.class));
    }

}