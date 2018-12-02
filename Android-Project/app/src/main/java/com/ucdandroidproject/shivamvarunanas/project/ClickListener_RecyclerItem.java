package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/*CREATED BY SHIVAM RATHORE
 Class to implement Listeners which allows to display the full image from an image tapped on the browse list*/
class ClickListener_RecyclerItem extends RecyclerView.SimpleOnItemTouchListener {

    private final OnClickListener_RecyclerItem listener;
    private final GestureDetectorCompat detectGesture;

    //Constructor
    public ClickListener_RecyclerItem(Context currentCon, final RecyclerView object_recycler_View, OnClickListener_RecyclerItem onClickListener_recyclerItem) {
        this.listener = onClickListener_recyclerItem;

        //Gesture Detector allows to detect gestures. Here listeners are created as anonymous inner classes and overriden the 2 abstract methods
        // We detect the position of the tap, and the position is returned via callback mechanism
        detectGesture = new GestureDetectorCompat(currentCon, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                View viewChild = object_recycler_View.findChildViewUnder(e.getX(), e.getY());
                if (viewChild != null && ClickListener_RecyclerItem.this.listener != null) {
                    ClickListener_RecyclerItem.this.listener.onItemClick(viewChild, object_recycler_View.getChildAdapterPosition(viewChild));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

                View viewChild = object_recycler_View.findChildViewUnder(e.getX(), e.getY());
                if (viewChild != null && ClickListener_RecyclerItem.this.listener != null) {

                    ClickListener_RecyclerItem.this.listener.onItemLongClick(viewChild, object_recycler_View.getChildAdapterPosition(viewChild));
                }
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        if (detectGesture != null) {
            boolean result = detectGesture.onTouchEvent(e);
            return result;
        } else {
            return false;
        }
    }

    //Interface to implement callback mechanism for the tap functionality. This interface is implemented in Main Activity Screen 2
    interface OnClickListener_RecyclerItem {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}

