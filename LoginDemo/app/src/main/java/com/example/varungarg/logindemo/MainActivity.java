package com.example.varungarg.logindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static com.example.varungarg.logindemo.R.id.passwordEditText;

public class MainActivity extends AppCompatActivity {


    public void login(View view){

        EditText userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        Log.i("Info", "Button Pressed");
        Log.i("Username", userNameEditText.getText().toString());
        Log.i("Password", passwordEditText.getText().toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
