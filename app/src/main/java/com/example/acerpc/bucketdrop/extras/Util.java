package com.example.acerpc.bucketdrop.extras;

import android.graphics.drawable.Drawable;
import android.os.Build;
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


    public static void setBackgroud(View view, Drawable drawable){
        if(moreThanJellyBean()) {
            view.setBackground(drawable);
        }else {
            view.setBackgroundDrawable(drawable);
        }
    }

    private static boolean moreThanJellyBean() {
        return Build.VERSION.SDK_INT > 15;
    }
}

