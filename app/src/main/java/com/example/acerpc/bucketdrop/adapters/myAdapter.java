package com.example.acerpc.bucketdrop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.acerpc.bucketdrop.R;
import com.example.acerpc.bucketdrop.beans.Drop;

import io.realm.RealmResults;

/**
 * Created by AcerPC on 11/14/2016.
 */

public class myAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM = 0;
    public static final int FOOTER = 1;
    private LayoutInflater myLayoutInflater;
    public static final String TAG = "Edor";
    private RealmResults<Drop> myResults;
    myItemViewHolder myItemHolder;
    myFooterViewHolder myFooterHolder;


    public myAdapter(Context context, RealmResults<Drop> results) {
        myLayoutInflater = LayoutInflater.from(context);
        updateResults(results);
    }


    //---------- method triggered when a change to the database is made ----------
    public void updateResults(RealmResults<Drop> newResults) {
        myResults = newResults;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (myResults == null || position < myResults.size()) {
            return ITEM;
        } else {
            return FOOTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            return new myItemViewHolder(myLayoutInflater.inflate(R.layout.single_row_drop, parent, false));
        } else {
            return new myFooterViewHolder(myLayoutInflater.inflate(R.layout.footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof myItemViewHolder){
            myItemHolder = (myItemViewHolder) holder;
            Drop myDrop = myResults.get(position);
            myItemHolder.myTxtView.setText(myDrop.getGoal());
        }else{
            //do nothing
        }

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ");
        return myResults.size()+1;
    }


    // ----------- my custom viewholder class -----------
    class myItemViewHolder extends RecyclerView.ViewHolder {
        private TextView myTxtView;

        public myItemViewHolder(View itemView) {
            super(itemView);
            myTxtView = (TextView) itemView.findViewById(R.id.recycler_drop_name);
        }
    }



    class myFooterViewHolder extends RecyclerView.ViewHolder{
        private Button myButton;

        public myFooterViewHolder(View itemView) {
            super(itemView);
            myButton = (Button) itemView.findViewById(R.id.btn_footer_add);
        }

    }

}