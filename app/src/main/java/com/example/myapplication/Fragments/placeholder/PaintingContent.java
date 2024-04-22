package com.example.myapplication.Fragments.placeholder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaintingContent {

    public static final List<PaintingItem> PAINTING_ITEMS = new ArrayList<PaintingItem>();

    public static void addItem(PaintingItem item) {
        PAINTING_ITEMS.add(item);
    }
    public static List<PaintingItem> getPaintingItems(){
        return PAINTING_ITEMS;
    }

    public static class PaintingItem{

        public String filename;
        public String filepath;

        public PaintingItem(String filename, String filepath) {
            this.filename = filename;
            this.filepath = filepath;
        }

        public String getFilepath() {
            return filepath;
        }

        public String getFilename() {
            return filename;
        }

        @Override
        public String toString() {
            return filename;
        }
    }
}