package com.kr.weathertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    final String key = "e62e6a9e08b2d9e9924fe1b6c229eb0b";  //api키
    TextView city , temp , weather, temp_max, temp_min, status, address, timezone;

    private Object View;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.Button1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(MainActivity.this, "버튼 수정입니다.", Toast.LENGTH_SHORT).show();
            }
        });



        temp = findViewById(R.id.temp);
        temp_min = findViewById(R.id.temp_min);
        temp_max = findViewById(R.id.temp_max);
        status = findViewById(R.id.status);
        city = findViewById(R.id.address);




    }


    private void loadWeatherByCityName(double lon, double lat) {
        Ion.with(this)
                .load("https://api.openweathermap.org/data/2.5/onecall?lat="+lat+
                        "&lon="+lon+"&exclude=hourly,minutely&units=metric&lang=kr&appid="+key)   //api주소
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("rrr" , result.toString());
                        if(e != null){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "server error", Toast.LENGTH_SHORT).show();
                        }else{

                            //Current
                            JsonObject current = result.get("current").getAsJsonObject(); //메인
                            double temp1 = current.get("temp").getAsDouble();          //메인 안에 온도
                            temp.setText(temp1+"C");

                            double temp5 = current.get("timezone").getAsDouble(); //temp_max = 최고온도
                            address.setText((int) temp5);

                            //Daily
                            JsonObject daily = result.get("daily").getAsJsonObject();
                            double temp3 = daily.get("min").getAsDouble(); //temp_min = 최저온도
                            temp_min.setText((int) temp3);


                            double temp4 = daily.get("max").getAsDouble(); //temp_max = 최고온도
                            temp_max.setText((int) temp4);



                            JsonObject weather = result.get("weather").getAsJsonObject();
                            double temp2 = weather.get("main").getAsDouble(); //main = 날씨 매개 변수 그룹(비 , 눈 , 맑음 , 번개등)
                            status.setText((int) temp2);




                        }
                    }
                });


    }




}