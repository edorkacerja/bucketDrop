package com.example.acerpc.bucketdrop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView backgroundImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backgroundImage = (ImageView) findViewById(R.id.background_pic);
        initBackgroundImg();
    }

    private void initBackgroundImg(){
        Glide.with(this).load(R.drawable.background).into(backgroundImage);
    }
}
