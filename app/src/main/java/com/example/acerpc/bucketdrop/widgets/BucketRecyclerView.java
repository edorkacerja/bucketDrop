package com.example.acerpc.bucketdrop.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.example.acerpc.bucketdrop.extras.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by AcerPC on 11/16/2016.
 */

public class BucketRecyclerView extends RecyclerView {

    private List<View> myEmptyViews = Collections.emptyList();
    private List<View> myNonEmptyViews = Collections.emptyList();

    private AdapterDataObserver myObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            toggleViews();
        }
    };








    public BucketRecyclerView(Context context) {
        super(context);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter != null){
            adapter.registerAdapterDataObserver(myObserver);
        }

        myObserver.onChanged();
    }

    public void hideIfEmpty(View... views) {
        myNonEmptyViews = Arrays.asList(views);
    }

    public void showIfEmpty(View... emptyViews) {
        myEmptyViews = Arrays.asList(emptyViews);
    }

    private void toggleViews(){
        if(getAdapter()!=null && myEmptyViews!=null && myNonEmptyViews!=null){
            if(getAdapter().getItemCount()==0){

                Util.makeVisible(myEmptyViews);
                setVisibility(View.GONE);
                Util.makeUnvisible(myNonEmptyViews);

            }else {

                Util.makeUnvisible(myEmptyViews);
                setVisibility(View.VISIBLE);
                Util.makeVisible(myNonEmptyViews);

            }

        }
    }
}
