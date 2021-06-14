package com.kr.weathertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    Button btbSearch;
    EditText etCityName;
    TextView city , temp , weather , temp_max, temp_min, status, address;
    ListView listView;
    final String key = "e62e6a9e08b2d9e9924fe1b6c229eb0b";  //api키


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
        loadWeatherByCityName("서울");


    }


    //최상단 현재 날씨 정보
    private void loadWeatherByCityName(String cityname) {
        Ion.with(this)
                .load("https://api.openweathermap.org/data/2.5/weather?q="
                        + cityname +"&units=metric&lang=kr&appid="+key)   //api주소
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("result" , result.toString());
                        if(e != null){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "server error", Toast.LENGTH_SHORT).show();
                        }else{



                            JsonObject main = result.get("main").getAsJsonObject(); //메인
                            double temp1 = main.get("temp").getAsDouble();          //메인 안에 온도
                            temp.setText(temp1+"C");


                            double temp3 = main.get("temp_min").getAsDouble(); //temp_min = 최저온도
                            temp_min.setText((int) temp3);

                            double temp4 = main.get("temp_max").getAsDouble(); //temp_max = 최고온도
                            temp_max.setText((int) temp4);

                            JsonObject weatherr = result.get("weather").getAsJsonObject();
                            double temp2 = weatherr.get("main").getAsDouble(); //main = 날씨 매개 변수 그룹(비 , 눈 , 맑음 , 번개등)
                            status.setText((int) temp2);


                    /*
                    main 안에 있는 정보들
                    feels_like = 체감온도
                    temp_min = 최저온도
                    temp_max = 최고온도
                    humidity = 습기

                    weather 안의 정보
                    main = 날씨 매개 변수 그룹(비 , 눈 , 맑음 , 번개등)

                    wind안에 정보
                    speed = 풍속

                    cloud 안에 정보
                    all = 흐림 %

                    rain 안의 정보
                    1h = 지난 1시간동안의 강수량
                    3h = 지난 3시간동안의 강수량
                     */


                            JsonObject sys = result.get("sys").getAsJsonObject();   //sys
                            String country = sys.get("country").getAsString();      //sys안에 도시이름
                            city.setText(cityname+", ");



                            JsonObject coord = result.get("coord").getAsJsonObject(); // 좌표
                            double lon = coord.get("lon").getAsDouble();            //위도
                            double lat = coord.get("lat").getAsDouble();            //경도
                            //loadDailyForecast(lon , lat);

                        }
                    }
                });


    }




}