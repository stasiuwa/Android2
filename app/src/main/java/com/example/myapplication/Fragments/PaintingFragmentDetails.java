package com.example.myapplication.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Fragments.placeholder.PaintingContent;
import com.example.myapplication.R;

import java.util.List;

public class PaintingFragmentDetails extends Fragment{

    public static PaintingFragmentDetails newInstance(int position){
        PaintingFragmentDetails fragmentDetails = new PaintingFragmentDetails();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentDetails.setArguments(args);
        return fragmentDetails;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item_details, container, false);
        int position = getArguments().getInt("position");
        PaintingContent.PaintingItem item = PaintingContent.getPaintingItems().get(position);
        Log.d("ERROR", "PaintingFragmentDetails/onCreateView() - position: " + position);
        Log.d("DETAILS", "POSITION: " + position);
        String imagePath = item.getFilepath();
        Log.d("DETAILS", "filepath: " + imagePath + "  filename: " + item.getFilename());
        ImageView imageView = view.findViewById(R.id.paintingImageView);
        TextView textView = view.findViewById(R.id.imgText);
        textView.setText(imagePath);
        Log.d("DETAILS", "testwoty przed bitmapo1");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        Log.d("DETAILS", "testwoty po bitampo1");
        imageView.setImageBitmap(bitmap);
        Log.d("DETAILS", "testwoty ppo bitmapo2");
        return view;
    }
}
