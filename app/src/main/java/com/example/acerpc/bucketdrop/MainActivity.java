package com.example.acerpc.bucketdrop;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

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
            Log.d(myAdapter.TAG, "Change of resultz was made");
            Log.d(myAdapter.TAG, "onChange: "+ resultz);
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



        // ----- Query the realm database to load the items ------
        realm = Realm.getDefaultInstance();
        int filterOption = load();
        loadResults(filterOption);



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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = true;
        int selection = Filter.NONE;
        switch (item.getItemId()){
            case R.id.action_add:
                addDropButton(toolbar);
                break;
            case R.id.action_show_complete:
                selection = Filter.COMPLETE;
                break;
            case R.id.action_show_incomplete:
                selection = Filter.INCOMPLETE;
                break;
            case R.id.action_sort_ascending_date:
                selection = Filter.LEAST_TIME_LEFT;
                break;
            case R.id.action_sort_descending_date:
                selection = Filter.MOST_TIME_LEFT;
                break;
            default:
                handled = false;
                break;
        }
        save(selection);
        loadResults(selection);
        return handled;
    }

    @Override
    protected void onStop() {
        super.onStop();
        resultz.removeChangeListener(myRealmChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    private void save(int filterOption){
        SharedPreferences mySharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt("filter", filterOption);
        editor.apply();
    }


    private int load(){
        SharedPreferences mySharedPreferences = getPreferences(MODE_PRIVATE);
        return mySharedPreferences.getInt("filter", Filter.NONE);
    }

    private void loadResults(int filterOption) {
        RealmQuery<Drop> query = realm.where(Drop.class);
        switch (filterOption) {
            case Filter.NONE:
                resultz = query.findAllAsync();
                break;
            case Filter.COMPLETE:
                resultz = query.equalTo("completed", true).findAllAsync();
                break;
            case Filter.INCOMPLETE:
                resultz = query.equalTo("completed", false).findAllAsync();
                break;
            case Filter.LEAST_TIME_LEFT:
                resultz = query.findAllSortedAsync("goalTime");
                break;
            case Filter.MOST_TIME_LEFT:
                resultz = query.findAllSortedAsync("goalTime", Sort.DESCENDING);
                break;
            default:
                break;
        }
        resultz.addChangeListener(myRealmChangeListener);

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
