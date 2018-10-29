package com.example.varungarg.catpictures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity {

    int i =0;
    public void onclick(View v1){
        i++;
        ImageView cat2 =(ImageView) findViewById(R.id.catImageView);
        if (i%2 == 1) {
            cat2.setImageResource(R.drawable.cat1);
        }else if (i%2 == 0){
            cat2.setImageResource(R.drawable.cat2);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
