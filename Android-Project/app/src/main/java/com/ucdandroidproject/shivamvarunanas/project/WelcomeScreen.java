package com.ucdandroidproject.shivamvarunanas.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/*CREATED BY SHIVAM RATHORE
WELCOME SCREEN IMPLEMENTATION*/


public class WelcomeScreen extends AppCompatActivity {


    private GifImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        imageView = findViewById(R.id.welcomeImage);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(progressBar.VISIBLE);

        //   final Typewriter writer = new Typewriter(this);
        try {
            InputStream inputStream = getAssets().open("batmanrun.gif");
            byte[] in = IOUtils.toByteArray(inputStream);
            imageView.setBytes(in);
            imageView.startAnimation();
        } catch (IOException e) {

        }
//        writer.setCharacterDelay(150);
//        writer.setTextSize(40);
//         setContentView(writer);
//        writer.animateText("FITNESS GURU");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                WelcomeScreen.this.startActivity(new Intent(WelcomeScreen.this, MainActivity.class
                ));
                WelcomeScreen.this.finish();
            }
        }, 3000);
    }
}
//class Typewriter extends android.support.v7.widget.AppCompatTextView {
//
//    private CharSequence mText;
//    private int mIndex;
//    private long mDelay = 500; //Default 500ms delay
//    private Handler mHandler = new Handler();
//    private Runnable characterAdder = new Runnable() {
//        @Override
//        public void run() {
//            setText(mText.subSequence(0, mIndex++));
//            if (mIndex <= mText.length()) {
//                mHandler.postDelayed(characterAdder, mDelay);
//            }
//        }
//    };
//
//    public Typewriter(Context context) {
//        super(context);
//    }
//    public Typewriter(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public void animateText(CharSequence text) {
//        mText = text;
//        mIndex = 0;
//
//        setText("");
//        mHandler.removeCallbacks(characterAdder);
//        mHandler.postDelayed(characterAdder, mDelay);
//    }
//
//    public void setCharacterDelay(long millis) {
//        mDelay = millis;
//    }
//}