package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


class ClickListener_RecyclerItem extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";
    private final OnClickListener_RecyclerItem listener;
    private final GestureDetectorCompat detectGesture;

    public ClickListener_RecyclerItem(Context context, final RecyclerView recyclerView, OnClickListener_RecyclerItem listener) {
        this.listener = listener;
        detectGesture = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                View viewChild = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (viewChild != null && ClickListener_RecyclerItem.this.listener != null) {

                    ClickListener_RecyclerItem.this.listener.onItemClick(viewChild, recyclerView.getChildAdapterPosition(viewChild));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

                View viewChild = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (viewChild != null && ClickListener_RecyclerItem.this.listener != null) {
                    Log.d(TAG, "onLongPress: calling listener.onItemLongClick");
                    ClickListener_RecyclerItem.this.listener.onItemLongClick(viewChild, recyclerView.getChildAdapterPosition(viewChild));
                }
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");
        if (detectGesture != null) {
            boolean result = detectGesture.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent(): returned: " + result);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent(): returned: false");
            return false;
        }
    }

    interface OnClickListener_RecyclerItem {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}

