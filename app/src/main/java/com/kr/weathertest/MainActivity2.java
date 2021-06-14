package com.kr.weathertest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    Button btbSearch;
    EditText etCityName;
    TextView city , temp , weather;
    ListView listView;


    final String key = "e62e6a9e08b2d9e9924fe1b6c229eb0b";  //api키



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().hide();

        btbSearch = findViewById(R.id.btnSearch);   //서치 버튼
        etCityName = findViewById(R.id.etCityName); //에딧 텍스트
        city = findViewById(R.id.cityName);         //도시이름
        temp = findViewById(R.id.temp1);            //온도
        weather = findViewById(R.id.weather);       //날씨
        listView = findViewById(R.id.DailyWeather); //리스트뷰


        btbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityname = etCityName.getText().toString();
                if(cityname.isEmpty()){
                    Toast.makeText(MainActivity2.this, "입력하세요", Toast.LENGTH_SHORT).show();
                }else{
                    loadWeatherByCityName(cityname);
                }
            }
        });

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
                            Toast.makeText(MainActivity2.this, "server error", Toast.LENGTH_SHORT).show();
                        }else{


                            JsonObject main = result.get("main").getAsJsonObject(); //메인
                            double temp1 = main.get("temp").getAsDouble();          //메인 안에 온도
                            temp.setText(temp1+"C");

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
                            loadDailyForecast(lon , lat);

                        }
                    }
                });


    }


    // 리스트뷰에 출력되는 7일의 날씨 정보
    private void loadDailyForecast(double lon, double lat) {
        Ion.with(this)
                .load("https://api.openweathermap.org/data/2.5/onecall?lat="+lat+
                        "&lon="+lon+"&exclude=hourly,minutely,current&units=metric&lang=kr&appid="+key)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("result", result.toString());
                        if (e != null) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity2.this, "server error", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("result", result.toString());
                            List<Weather> weatherList = new ArrayList<>();
                            String timeZone = result.get("timezone").getAsString();
                            JsonArray daily = result.get("daily").getAsJsonArray();
                            for(int i = 1; i < daily.size(); i++){
                                Long date = daily.get(i).getAsJsonObject().get("dt").getAsLong();
                                Double temp = daily.get(i).getAsJsonObject().get("temp").getAsJsonObject().get("day").getAsDouble();
                                weatherList.add(new Weather(date , timeZone , temp));
                            }

                            DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(MainActivity2.this , weatherList);
                            listView.setAdapter(dailyWeatherAdapter);
                        }

                    }


                });
    }
}
