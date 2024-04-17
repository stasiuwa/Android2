package com.example.myapplication.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Data.Models.PhoneModel;
import com.example.myapplication.R;

public class AddPhoneActivity extends AppCompatActivity {

    EditText brand, model, OSversion, website;
    Button websiteButton, clearButton, addButton;
    PhoneModel toEditPhone;
    boolean toEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_phone);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dodaj Telefon");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        brand = findViewById(R.id.phoneBrandEditText);
        model = findViewById(R.id.phoneModelEditText);
        OSversion = findViewById(R.id.phoneOSversionEditText);
        website = findViewById(R.id.phoneWebsiteEditText);

        websiteButton = findViewById(R.id.phoneWebsiteButton);
        clearButton = findViewById(R.id.phoneCancelButton);
        addButton = findViewById(R.id.phoneAddButton);

        if (getIntent().getExtras() != null && getIntent().getExtras().getParcelable("phone") != null) {
            toEdit = true;
            toEditPhone = getIntent().getExtras().getParcelable("phone");

            brand.setText(toEditPhone.getBrand());
            model.setText(toEditPhone.getModel());
            OSversion.setText(toEditPhone.getOSVersion());
            website.setText(toEditPhone.getWebsite());

        }

        websiteButton.setOnClickListener( view -> {
            String address = website.getText().toString();
            address = (address.startsWith("http://") || address.startsWith("https://")) ? address : "http://" + address;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
            startActivity(browserIntent);

        });

        clearButton.setOnClickListener( view -> {
            Intent intent = new Intent(this, PhoneDatabaseActivity.class);
            Bundle bundle = new Bundle();

            bundle.putBoolean("sucess", false);
            intent.putExtras(bundle);

            setResult(RESULT_OK, intent);
            finish();
        });

        addButton.setOnClickListener( view -> {
            if (validateEditTexts()) {
                Intent intent = new Intent(this, PhoneDatabaseActivity.class);
                Bundle bundle = new Bundle();

                if(toEdit) {
                    toEditPhone.setBrand(brand.getText().toString());
                    toEditPhone.setModel(model.getText().toString());
                    toEditPhone.setOSVersion(OSversion.getText().toString());
                    toEditPhone.setWebsite(website.getText().toString());
                    bundle.putBoolean("editing", true);
                } else {
                    toEditPhone = new PhoneModel(
                            brand.getText().toString(),
                            model.getText().toString(),
                            OSversion.getText().toString(),
                            website.getText().toString()
                    );
                    bundle.putBoolean("editing", false);
                }
                bundle.putParcelable("phone", toEditPhone);
                bundle.putBoolean("success", true);

                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
    private boolean validateEditTexts() {
        String errorMsg = "Uzupełnij te dane!";
        if (brand.getText().toString().isEmpty()){
            brand.setError(errorMsg);
            return false;
        }
        if (model.getText().toString().isEmpty()) {
            model.setError(errorMsg);
            return false;
        }
        if (OSversion.getText().toString().isEmpty()) {
            OSversion.setError(errorMsg);
            return false;
        }
        if (website.getText().toString().isEmpty()) {
            website.setError(errorMsg);
            return false;
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString("brand", brand.getText().toString());
        outState.putString("model", model.getText().toString());
        outState.putString("OSversion", OSversion.getText().toString());
        outState.putString("website", website.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        brand.setText(savedInstanceState.getString("brand"));
        model.setText(savedInstanceState.getString("model"));
        OSversion.setText(savedInstanceState.getString("OSversion"));
        website.setText(savedInstanceState.getString("website"));

        super.onRestoreInstanceState(savedInstanceState);
    }
}