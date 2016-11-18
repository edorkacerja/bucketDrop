package com.example.acerpc.bucketdrop.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by AcerPC on 11/18/2016.
 */

public class SimpleTouchCallback extends ItemTouchHelper.Callback {

    SwipeListener mySwipeListener;
    public SimpleTouchCallback(SwipeListener sl){
        mySwipeListener = sl;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mySwipeListener.onSwipe(viewHolder.getLayoutPosition());
    }
}
