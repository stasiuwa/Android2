package com.example.myapplication;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    private ArrayList<Integer> mNumberList;
    private Activity mActivity;

    public MyAdapter(ArrayList<Integer> mNumberList, Activity mActivity) {
        this.mNumberList = mNumberList;
        this.mActivity = mActivity;
    }

//    wywoływane gdy tworzony jest nowy wiersz
    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowRootView = mActivity.getLayoutInflater().inflate(R.layout.list_row,parent,false);
        MyAdapterViewHolder myAdapterViewHolder = new MyAdapterViewHolder(rowRootView);
        return myAdapterViewHolder;
    }

//    wywoływane zawsze gdy ma byc wyświetlony nowy wiersz
    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        int value = mNumberList.get(position);
        holder.mNumberEditText.setTag(position);
        holder.mNumberEditText.setText(Integer.toString(value));
    }

    @Override
    public int getItemCount() {
        return mNumberList.size();
    }

//    viewHolder zarzadza pojedynczym wierszem listy, to dobre miejsce na zaimplementowanie słuchaczy
    public class MyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, TextWatcher {

        Button mPlusButton;
        EditText mNumberEditText;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mNumberEditText = itemView.findViewById(R.id.numberEditText);
            mNumberEditText.addTextChangedListener(this);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int number = 0;
            try {
                number = Integer.parseInt(mNumberEditText.getText().toString());
            } catch (NumberFormatException e) {
                int index = (Integer) mNumberEditText.getTag();
                mNumberList.set(index,number);
            }
        }

        @Override
        public void onClick(View view) {
            int number = 0;
            try {
                number = Integer.parseInt(mNumberEditText.getText().toString());
                number++;
            } catch (NumberFormatException ignored) {
                mNumberEditText.setText(Integer.toString(number));
            }
        }
    }
}
