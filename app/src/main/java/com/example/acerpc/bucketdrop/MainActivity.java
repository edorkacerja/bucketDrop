package com.example.acerpc.bucketdrop;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.acerpc.bucketdrop.adapters.myAdapter;
import com.example.acerpc.bucketdrop.beans.Drop;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView backgroundImage;
    RecyclerView myRecyclerView;
    LinearLayoutManager mLayoutManager;
    myAdapter mAdapter;
    FragmentManager myFragmentManager;
    Realm realm;
    RealmResults<Drop> resultz;

    private RealmChangeListener myRealmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            Log.d(myAdapter.TAG, "onChange:  was made");
            mAdapter.updateResults(resultz);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        backgroundImage = (ImageView) findViewById(R.id.background_pic);
        initBackgroundImg();

        realm = Realm.getDefaultInstance();
        resultz = realm.where(Drop.class).findAllAsync();

        mAdapter = new myAdapter(this, resultz);
        myRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(mLayoutManager);


        setSupportActionBar(toolbar);
        myFragmentManager = getSupportFragmentManager();

    }


    @Override
    protected void onStart() {
        super.onStart();
        resultz.addChangeListener(myRealmChangeListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        resultz.removeChangeListener(myRealmChangeListener);
    }

    private void initBackgroundImg() {
        Glide.with(this).load(R.drawable.background).into(backgroundImage);
    }

    public void addDropButton(View view) {
        DialogAdd myDialog = new DialogAdd();
        Log.d(myAdapter.TAG, "addDropButton: CLICKEEDDDDDDDD");
        myDialog.show(myFragmentManager, "Add Dialog");
    }
}
