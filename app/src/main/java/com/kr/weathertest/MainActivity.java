package com.kr.weathertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Object View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    Button btn = (Button)findViewById(R.id.button1);

    btn.setOnClickListener(new  void OnClickListener() {

        public void onClick(View v){
            startActivity(new Intent(MainActivity.this, MainActivity2.class));
        }
    };

}