package com.example.acerpc.bucketdrop;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.acerpc.bucketdrop.adapters.CompletedListener;
import com.example.acerpc.bucketdrop.adapters.ItemClickListener;
import com.example.acerpc.bucketdrop.adapters.SimpleTouchCallback;
import com.example.acerpc.bucketdrop.adapters.myAdapter;
import com.example.acerpc.bucketdrop.beans.Drop;
import com.example.acerpc.bucketdrop.widgets.BucketRecyclerView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements ItemClickListener{
    Toolbar toolbar;
    ImageView backgroundImage;
    BucketRecyclerView myRecyclerView;
    View emptyView;
    LinearLayoutManager mLayoutManager;
    myAdapter mAdapter;
    FragmentManager myFragmentManager;
    Realm realm;
    RealmResults<Drop> resultz;


    // -------------- Listener for changes in the Realm database --------------
    private RealmChangeListener myRealmChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            Log.d(myAdapter.TAG, "onChange:  was made");
            mAdapter.updateResults(resultz);
        }
    };


    private CompletedListener myCompletedListener = new CompletedListener() {
        @Override
        public void onComplete(int position) {
            mAdapter.markComplete(position);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        myRecyclerView = (BucketRecyclerView) findViewById(R.id.my_recycler_view);
        backgroundImage = (ImageView) findViewById(R.id.background_pic);
        emptyView = findViewById(R.id.empty_drops);
        initBackgroundImg();



        // ----- Query the realm database to get all items ------
        realm = Realm.getDefaultInstance();
        resultz = realm.where(Drop.class).findAllAsync();
        Log.d(myAdapter.TAG, "onCreate: "+ resultz);



        // ----- Configure the Recycler View with and adapter and layout manager -----
        myRecyclerView.hideIfEmpty(toolbar);
        myRecyclerView.showIfEmpty(emptyView);
        mAdapter = new myAdapter(this, realm, resultz, this);
        myRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(mLayoutManager);


        // ------ Initialize Swipe to delete ---------
        SimpleTouchCallback mySimpleTouch = new SimpleTouchCallback(mAdapter);
        ItemTouchHelper myItemTouchHelper = new ItemTouchHelper(mySimpleTouch);
        myItemTouchHelper.attachToRecyclerView(myRecyclerView);


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

    // ----------------- Loading background image -----------------
    private void initBackgroundImg() {
        Glide.with(this).load(R.drawable.background).into(backgroundImage);
    }



    // ----------------- Show Dialog Fragments ------------------
    public void addDropButton(View view) {
        DialogAdd myDialog = new DialogAdd();
        myDialog.show(myFragmentManager, "Add Dialog");
    }

    public void showDialogMarkComplete(int position){
        DialogMark myMarkDialog = new DialogMark();
        myMarkDialog.setCompletedListener(myCompletedListener);
        Bundle myBundle = new Bundle();
        myBundle.putInt("POSITION", position);
        myMarkDialog.setArguments(myBundle);
        myMarkDialog.show(myFragmentManager, "Mark Complete?");
    }


}
