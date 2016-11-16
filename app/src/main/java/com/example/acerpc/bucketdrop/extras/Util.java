package com.example.acerpc.bucketdrop.extras;

import android.view.View;

import java.util.List;

/**
 * Created by AcerPC on 11/16/2016.
 */

public class Util {

    public static void makeVisible(List<View> views){
        for(View view:views)
            view.setVisibility(View.VISIBLE);
    }

    public static void makeUnvisible(List<View> views){
        for(View view:views)
            view.setVisibility(View.GONE);
    }
}
