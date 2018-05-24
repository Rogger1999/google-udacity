package com.tiredpapi.baking.fragmetnsex.data;

import com.tiredpapi.baking.bakerapp.R;

import java.util.ArrayList;
import java.util.List;

public class ImageData {

    private static final List<Integer> images = new ArrayList<Integer>() {{
        add(R.drawable.ic_android_black_24dp);
        add(R.drawable.ic_autorenew_black_24dp);
    }};

    private static final List<Integer> images1 = new ArrayList<Integer>() {{
        add(R.drawable.ic_android_black_24dp);
        add(R.drawable.ic_autorenew_black_24dp);
    }};

    private static final List<Integer> all = new ArrayList<Integer>() {{
        addAll(images);
        addAll(images1);
    }};

    public static List<Integer> getAll() {
        return all;
    }


    public static List<Integer> getImages() {
        return images;
    }
}
