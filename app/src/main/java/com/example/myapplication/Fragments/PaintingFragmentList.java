package com.example.myapplication.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.Activities.Lab5Activity;
import com.example.myapplication.R;
import com.example.myapplication.Fragments.placeholder.PaintingContent;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class PaintingFragmentList extends Fragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PaintingFragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
//        wczytanie listy obrazkow
        List<PaintingContent.PaintingItem> paintingItemList = PaintingContent.getPaintingItems();
//        ustawienie adaptera
        PaintingAdapter paintingAdapter = new PaintingAdapter(paintingItemList, getActivity());
        RecyclerView recyclerView = view.findViewById(R.id.paintingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(paintingAdapter);

        paintingAdapter.setItemClickListener(position -> {
            PaintingFragmentDetails fragmentList = PaintingFragmentDetails.newInstance(position);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, fragmentList)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}