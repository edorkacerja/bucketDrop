package com.example.acerpc.bucketdrop.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.acerpc.bucketdrop.R;
import com.example.acerpc.bucketdrop.beans.Drop;
import com.example.acerpc.bucketdrop.extras.Util;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by AcerPC on 11/14/2016.
 */

public class myAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {
    public static final int ITEM = 0;
    public static final int FOOTER = 1;
    private LayoutInflater myLayoutInflater;
    public static final String TAG = "Edor";
    private RealmResults<Drop> myResults;
    myItemViewHolder myItemHolder;
    myFooterViewHolder myFooterHolder;
    Realm myRealm;
    ItemClickListener myClickListener;

    public myAdapter(Context context, Realm realm, RealmResults<Drop> results, ItemClickListener listener) {
        myLayoutInflater = LayoutInflater.from(context);
        myRealm = realm;
        updateResults(results);
        myClickListener = listener;
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
        if (holder instanceof myItemViewHolder) {
            myItemHolder = (myItemViewHolder) holder;
            Drop myDrop = myResults.get(position);
            myItemHolder.myTxtView.setText(myDrop.getGoal());
            myItemHolder.setBackgroud(myDrop.isCompleted());
        }

    }

    @Override
    public int getItemCount() {

        if (myResults == null) {
            return 0;
        } else {
            return myResults.size() + 1;
        }
    }

    @Override
    public void onSwipe(int position) {
        myRealm.beginTransaction();
        myResults.get(position).deleteFromRealm();
        myRealm.commitTransaction();
        notifyDataSetChanged();
        //notifyItemRemoved(position);
    }

    public void markComplete(int position) {
        myRealm.beginTransaction();
        myResults.get(position).setCompleted(true);
        myRealm.commitTransaction();
        notifyDataSetChanged();
    }


    // ----------- my custom viewholder class -----------
    private class myItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView myTxtView;
        Context context;
        View myItemView;

        myItemViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            myTxtView = (TextView) itemView.findViewById(R.id.recycler_drop_name);
            itemView.setOnClickListener(this);
            myItemView = itemView;
        }

        @Override
        public void onClick(View v) {
            myClickListener.showDialogMarkComplete(getAdapterPosition());
        }

        void setBackgroud(boolean completed) {
            Drawable myDrawable;
            if (completed) {
                myDrawable = ContextCompat.getDrawable(context, R.color.bg_drop_completed);
            } else {
                myDrawable = ContextCompat.getDrawable(context, R.drawable.row_drop_layout);
            }
            Util.setBackgroud(myItemView, myDrawable);
        }
    }


    class myFooterViewHolder extends RecyclerView.ViewHolder {
        private Button myButton;

        public myFooterViewHolder(View itemView) {
            super(itemView);
            myButton = (Button) itemView.findViewById(R.id.btn_footer_add);
        }

    }

}