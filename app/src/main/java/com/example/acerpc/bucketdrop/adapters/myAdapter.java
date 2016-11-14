package com.example.acerpc.bucketdrop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acerpc.bucketdrop.R;

import java.util.ArrayList;

/**
 * Created by AcerPC on 11/14/2016.
 */

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {

    private LayoutInflater myLayoutInflater;
    private ArrayList<String> myList = new ArrayList<String>();
    myViewHolder myHolder;
    public myAdapter (Context context){
        myLayoutInflater = LayoutInflater.from(context);
        populateArraylist();
    }

    private void populateArraylist() {
        for(int i=0; i<100; i++){
            myList.add("item number "+ i);
        }
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         myHolder = new myViewHolder(myLayoutInflater.inflate(R.layout.single_row_drop, parent, false));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        myHolder.myTxtView.setText(myList.get(position));

    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        private TextView myTxtView;
        public myViewHolder(View itemView){
            super(itemView);
            myTxtView = (TextView) itemView.findViewById(R.id.recycler_drop_name);
        }

    }
}
