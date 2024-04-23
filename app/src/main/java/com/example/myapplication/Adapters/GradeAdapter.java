package com.example.myapplication.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Data.Models.GradeModel;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeAdapterViewHolder> {

    private List<GradeModel> mGradesList;
    private Activity mActivity;

    public GradeAdapter(List<GradeModel> mGradesList, Activity mActivity) {
        this.mGradesList = mGradesList;
        this.mActivity = mActivity;

    }
    //    wywoływane gdy tworzony jest nowy wiersz
    @NonNull
    @Override
    public GradeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowRootView = mActivity.getLayoutInflater().inflate(R.layout.list_row,parent,false);
        return new GradeAdapterViewHolder(rowRootView);
    }

//    wywoływane zawsze gdy ma byc wyświetlony nowy wiersz
    @Override
    public void onBindViewHolder(@NonNull GradeAdapterViewHolder holder, int position) {

        GradeModel gradeModel = mGradesList.get(position);
        holder.mGradeTextView.setText(gradeModel.getName());
        holder.grade = gradeModel.getGrade();

        switch (gradeModel.getGrade()) {
            case 2:
                holder.buttons.check(R.id.grade_2);
                break;
            case 3:
                holder.buttons.check(R.id.grade_3);
                break;
            case 4:
                holder.buttons.check(R.id.grade_4);
                break;
            case 5:
                holder.buttons.check(R.id.grade_5);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mGradesList.size();
    }



//    viewHolder zarzadza pojedynczym wierszem listy, to dobre miejsce na zaimplementowanie słuchaczy
    public class GradeAdapterViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
        TextView mGradeTextView = itemView.findViewById(R.id.lessonLabel);
        RadioGroup buttons = itemView.findViewById(R.id.buttons);
        int grade;
        Map<Integer, Integer> radioButtons;

        public GradeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
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
                mGradesList.get(position).setName(mGradeTextView.getText().toString());
            }
        }
    }
}
