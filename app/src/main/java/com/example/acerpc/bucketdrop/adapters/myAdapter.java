package com.example.acerpc.bucketdrop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acerpc.bucketdrop.R;
import com.example.acerpc.bucketdrop.beans.Drop;

import io.realm.RealmResults;

/**
 * Created by AcerPC on 11/14/2016.
 */

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {

    private LayoutInflater myLayoutInflater;
    public static final String TAG = "Edor";
    private RealmResults<Drop> myResults;
    myViewHolder myHolder;
    public myAdapter (Context context, RealmResults<Drop> results){
        myLayoutInflater = LayoutInflater.from(context);
        updateResults(results);
    }

    public void updateResults(RealmResults<Drop> newResults){
        myResults = newResults;
        notifyDataSetChanged();
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
         myHolder = new myViewHolder(myLayoutInflater.inflate(R.layout.single_row_drop, parent, false));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: position - " + position);
        Drop myDrop = myResults.get(position);
        myHolder.myTxtView.setText(myDrop.getGoal());

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return myResults.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private TextView myTxtView;
        public myViewHolder(View itemView){
            super(itemView);
            Log.d(TAG, "myViewHolder: constructor ");
            myTxtView = (TextView) itemView.findViewById(R.id.recycler_drop_name);
        }
    }
}