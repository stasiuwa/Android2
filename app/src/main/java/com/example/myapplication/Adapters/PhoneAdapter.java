package com.example.myapplication.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.Models.PhoneModel;
import com.example.myapplication.R;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneAdapterViewHolder> {

    List<PhoneModel> mPhoneList;
    Activity activity;

    public PhoneAdapter(Activity context) {
        this.activity = context;
        mPhoneList = null;
    }

    @NonNull
    @Override
    public PhoneAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = activity.getLayoutInflater().inflate(R.layout.list_phone, parent, false);
        return new PhoneAdapterViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapterViewHolder holder, int position) {
        PhoneModel phone = mPhoneList.get(position);
        holder.setBrand(phone.getBrand());
        holder.setModel(phone.getModel());
        holder.setVersion(phone.getOSVersion());
        holder.setWebsite(phone.getWebsite());
    }

    @Override
    public int getItemCount() {
        return (mPhoneList != null) ? mPhoneList.size() : 0;
    }

    public void setPhoneList(List<PhoneModel> phoneList) {
        this.mPhoneList = phoneList;
        notifyDataSetChanged();
    }

    public class PhoneAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView brandTextView, modelTextView, versionTextView, websiteTextView;

        public PhoneAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.phone_brand);
            modelTextView = itemView.findViewById(R.id.phone_model);
            versionTextView = itemView.findViewById(R.id.phone_version);
            websiteTextView = itemView.findViewById(R.id.phone_website);

            itemView.setOnClickListener(this);
        }

        public void setBrand(String brand){
            brandTextView.setText(brand);
        }
        public void setModel(String model){
            modelTextView.setText(model);
        }
        public void setVersion(String version){
            versionTextView.setText(version);
        }
        public void setWebsite(String website){
            websiteTextView.setText(website);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if ( position != RecyclerView.NO_POSITION) {
                PhoneModel phone = mPhoneList.get(position);
            }
        }
    }
}
