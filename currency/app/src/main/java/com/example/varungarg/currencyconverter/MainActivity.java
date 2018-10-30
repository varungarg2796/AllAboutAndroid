package com.example.varungarg.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public void convertCurrency(View view){
        Log.i("Info", "Button Pressed");
        EditText editText = (EditText) findViewById(R.id.editText);

        String amountInEuros = editText.getText().toString();

        double amountInEurosDouble = Double.parseDouble(amountInEuros);
        double amountInRupeesDouble = amountInEurosDouble * 83.48;

        String amountInRupeesString = String.format("%.2f", amountInRupeesDouble);

        Toast.makeText(this, "Euros:" + amountInEuros + " is Rs:" + amountInRupeesString,Toast.LENGTH_LONG).show();

        Log.i("Info", editText.getText().toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
