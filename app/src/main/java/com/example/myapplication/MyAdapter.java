package com.example.myapplication;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

    private List<GradeModel> mGradesList;
    private Activity mActivity;

    public MyAdapter(List<GradeModel> mGradesList, Activity mActivity) {
        this.mGradesList = mGradesList;
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
        GradeModel gradeModel = mGradesList.get(position);
        holder.mGradeTextView.setText(gradeModel.getName());
        holder.grade = gradeModel.getGrade();
    }

    @Override
    public int getItemCount() {
        return mGradesList.size();
    }

//    viewHolder zarzadza pojedynczym wierszem listy, to dobre miejsce na zaimplementowanie słuchaczy
    public class MyAdapterViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
        TextView mGradeTextView;
        int grade = 2;
        Map<Integer, Integer> radioButtons;

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mGradeTextView = itemView.findViewById(R.id.lessonLabel);
            RadioGroup buttons = itemView.findViewById(R.id.buttons);
            buttons.setOnCheckedChangeListener(this);

            radioButtons = new HashMap<>();
            radioButtons.put(R.id.grade_2, 2);
            radioButtons.put(R.id.grade_3, 3);
            radioButtons.put(R.id.grade_4, 4);
            radioButtons.put(R.id.grade_5, 5);
        }


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Integer value = radioButtons.get(checkedId);
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                mGradesList.get(position).setGrade(value);
            }
        }
    }
}
