package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

class GraphView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    LinkedHashMap data = new LinkedHashMap<Integer, Float>();
    Integer base = 0;
    ArrayList<Float> tempValues = new ArrayList<>();

    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }




    public void insert1(float time){
        int temp = data.size();
        Integer km = base + (temp * 80000);
        data.put(km, time);
    }
    public void setTemp(ArrayList list){
        tempValues.addAll(list);
    }

    //@Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("GraphView"," !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
        Log.d("GraphView"," " + tempValues.size());
        for(int i=0;i<tempValues.size();i++){
            float x = tempValues.get(i);
            insert1(x);
            Log.d("GraphView"," " + tempValues.get(i).toString());
        }

        drawChart(canvas, data);
        data.clear();
    }

    public void drawChart(Canvas canvas, LinkedHashMap data) {

        int screenWidth = getWidth();

        float scale = 1.0f;
        //keep the graph squared
        float left = 0;
        float top = 0;
        float gWidth = screenWidth - 200;
        float gHeight = screenWidth - 200;
        float squared = (screenWidth - gWidth)/2;
        left = squared;
        top = squared;

        // Start of graph drawing:
        //-------------------------------------------------------------------------

        // Margin and inner dimensions for the entire graph
        float margin = (8 * scale);
        float innerWidth = (gWidth - 2*margin);
        float innerHeight = gHeight - 8*margin;



        // Draw Secs label top-left
        paint.setTextSize(45*scale);
        paint.setColor(Color.parseColor("#F4F4F4"));
        canvas.drawText("Secs", left+10, top+30*scale, paint);
        // get xmin, xmax, ymin, ymax for the graph
        float xmin = 0;
        float xmax = 0;
        float ymin = 0;
        float ymax = 0;
        boolean check = false;

        Iterator<Integer> Iterator = data.keySet().iterator();
        while(Iterator.hasNext()){
            Integer time = Iterator.next();
            System.out.println(time);
            float scaleWidth = (Float) data.get(time);
            System.out.println(scaleWidth);
            if (!check) {
                xmin = time;
                xmax = time;
                ymin = scaleWidth;
                ymax = scaleWidth;
                check = true;
            }

            if (scaleWidth>ymax) ymax = scaleWidth;
            if (scaleWidth<ymin) ymin = scaleWidth;
            if (time>xmax) xmax = time;
            if (time<xmin) xmin = time;
        }

        float r = (ymax - ymin);
        ymax = (ymax - (r / 2f)) + (r/1.5f);
        // Fixed min y
        ymin = 0;
        float barWidth = 1800*20;
        xmin -= (barWidth /2);
        xmax += (barWidth /2);

        float barWidthpx = (barWidth / (xmax - xmin)) * innerWidth;

        // time in secs labels on each bar
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(35*scale);

        Iterator = data.keySet().iterator();
        int km = 1;
        int colour =0;
        //draw the bars for the graph
        while(Iterator.hasNext()){

            Integer time = Iterator.next();
            float value = (Float) data.get(time);

            float px = (((time - xmin) / (xmax - xmin))) * innerWidth;
            float py = ((value - ymin) / (ymax - ymin)) * innerHeight;
            //System.out.println(px+" "+py);

            float barLeft = left + margin + px - barWidthpx/2;
            float barBottom = top + margin + innerHeight;

            float barTop = barBottom - py;
            float barRight = barLeft + barWidthpx;

            if(colour%2!=1) {
                paint.setColor(Color.parseColor("#3498DB"));
                canvas.drawRect(barLeft, barTop, barRight, barBottom, paint);
            }
            else{
                paint.setColor(Color.parseColor("#34dbcb"));
                canvas.drawRect(barLeft, barTop, barRight, barBottom, paint);
            }
            // Draw time per kM label text in each its own bar
            if (py>38*scale) {
                paint.setColor(Color.parseColor("#F4F4F4"));
                int offset = (int)(45*scale);
                // System.out.println("test"+value);
                canvas.drawText(String.format("%.0f", value), left+margin+px, barTop + offset, paint);
                //print each KM under the bar
                canvas.drawText(String.format(String.valueOf(km) + "KM"), left+margin+px, barBottom + offset, paint);
            }
            km++;
            colour++;
        }
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width > height ? height : width;
        setMeasuredDimension(size, size);
    }


}
