package com.example.acerpc.bucketdrop;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.acerpc.bucketdrop.adapters.myAdapter;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView backgroundImage;
    RecyclerView myRecyclerView;
    LinearLayoutManager mLayoutManager;
    myAdapter mAdapter;
    FragmentManager myFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mAdapter = new myAdapter(this);
        myRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(mLayoutManager);
        setSupportActionBar(toolbar);
        backgroundImage = (ImageView) findViewById(R.id.background_pic);
        initBackgroundImg();
        myFragmentManager = getSupportFragmentManager();
    }

    private void initBackgroundImg(){
        Glide.with(this).load(R.drawable.background).into(backgroundImage);
    }

    public void addDropButton(View view){
        DialogAdd myDialog = new DialogAdd();
        myDialog.show(myFragmentManager, "Add Dialog");
    }
}
