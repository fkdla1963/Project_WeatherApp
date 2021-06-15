    package com.kr.weathertest;


    import android.content.Intent;
    import android.location.Address;
    import android.location.Geocoder;
    import android.location.Location;
    import android.location.LocationManager;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;

    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;


    import com.google.gson.JsonArray;
    import com.google.gson.JsonObject;
    import com.koushikdutta.async.future.FutureCallback;
    import com.koushikdutta.ion.Ion;

    import java.io.IOException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.List;

    public class MainActivity2 extends AppCompatActivity {

        Button btbSearch;
        Button btnref;
        Button resbtn ;
        TextView etCityName;
        TextView  temp, min , max , weather, wind, one , three , hum;
        ListView listView;

        private LocationManager locationManager;
        private String TAG = "LocationProvider";
        private String cityname = "" ;

        final String key = "e62e6a9e08b2d9e9924fe1b6c229eb0b";  //api키


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);

            getSupportActionBar().hide();

            Intent intent = getIntent();
            double lat = intent.getDoubleExtra("위도" , 0);
            double lon = intent.getDoubleExtra("경도" , 0);

            final Geocoder geocoder = new Geocoder(this);

            hum = findViewById(R.id.humidity);
            temp = findViewById(R.id.likeweathertv);
            min = findViewById(R.id.mintemp);
            max = findViewById(R.id.maxtemp);
            weather = findViewById(R.id.weather11);
            wind = findViewById(R.id.wind);

            listView = findViewById(R.id.DailyWeather); //리스트뷰
            loadWeatherByCityName(lat , lon);
            loadDailyForecast(lat , lon);

        }

        //최상단 현재 날씨 정보
//        private void loadWeatherByCityName(String cityname) {
//            Ion.with(this)
//                    .load("https://api.openweathermap.org/data/2.5/weather?q="
//                            + cityname +"&units=metric&lang=kr&appid="+key)   //api주소
//                    .asJsonObject()
//                    .setCallback(new FutureCallback<JsonObject>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonObject result) {
//                            Log.d("result" , result.toString());
//                            if(e != null){
//                                e.printStackTrace();
//                                Toast.makeText(MainActivity2.this, "server error", Toast.LENGTH_SHORT).show();
//                            }else{
//
//
//                                JsonObject main = result.get("main").getAsJsonObject(); //메인
//                                double temp1 = main.get("temp").getAsDouble();          //메인 안에 온도
//                                temp.setText(temp1+"C");
//
//                        /*
//                        main 안에 있는 정보들
//                        feels_like = 체감온도
//                        temp_min = 최저온도
//                        temp_max = 최고온도
//                        humidity = 습기
//
//                        weather 안의 정보
//                        main = 날씨 매개 변수 그룹(비 , 눈 , 맑음 , 번개등)
//
//                        wind안에 정보
//                        speed = 풍속
//
//                        cloud 안에 정보
//                        all = 흐림 %
//
//                        rain 안의 정보
//                        1h = 지난 1시간동안의 강수량
//                        3h = 지난 3시간동안의 강수량
//                         */
//
//
//                                JsonObject sys = result.get("sys").getAsJsonObject();   //sys
//                                String country = sys.get("country").getAsString();      //sys안에 도시이름
//                                city.setText(cityname+", ");
//                                JsonObject coord = result.get("coord").getAsJsonObject(); // 좌표
//                                double lon = coord.get("lon").getAsDouble();            //위도
//                                double lat = coord.get("lat").getAsDouble();            //경도
//                                loadDailyForecast(lon , lat);
//
//                                resbtn.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent(getApplicationContext(), resultActivity.class) ;
//                                        String feelweather = main.get("feels_like").getAsString() ;
//                                        String mintemp = main.get("temp_min").getAsString() ;
//                                        String maxtemp = main.get("temp_max").getAsString() ;
//                                        String hum = main.get("humidity").getAsString() ;
//
//                                        String realweather = main.get("main").getAsString() ;
//
//                                        String windspeed = main.get("speed").getAsString() ;
//                                        String cloudall = main.get("all").getAsString() ;
//
//                                        String rain1h = main.get("1h").getAsString() ;
//                                        String rain3h = main.get("3h").getAsString() ;
//
//                                        intent.putExtra("resultfweather", feelweather) ;
//                                        intent.putExtra("resultmintemp", mintemp) ;
//                                        intent.putExtra("resultmaxtemp", maxtemp) ;
//                                        intent.putExtra("resulthum", hum) ;
//                                        intent.putExtra("resultrealweather", realweather);
//                                        intent.putExtra("resultwindspeed",windspeed) ;
//                                        intent.putExtra("resultcloudall",cloudall) ;
//                                        intent.putExtra("resultrain1h", rain1h) ;
//                                        intent.putExtra("resultrain3h", rain3h) ;
//
//                                        startActivity(intent);
//                                    }
//                                });
//
//                            }
//                        }
//                    });
//
//
//        }

        private void loadWeatherByCityName(double lat , double lon){
            Ion.with(this)
                    .load("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=metric&lang=kr&appid="+key)   //api주소
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Log.d("result" , result.toString());
                            if(e != null){
                                e.printStackTrace();
                                Toast.makeText(MainActivity2.this, "server error", Toast.LENGTH_SHORT).show();
                            }else{

                                Calendar calendar;
                                SimpleDateFormat dateFormat;
                                String date;

                                JsonObject main = result.get("main").getAsJsonObject(); //메인
                                double temp1 = main.get("feels_like").getAsDouble();          //메인 안에 온도
                                temp.setText(temp1+"C");

                                double temp3 = main.get("temp_min").getAsDouble(); //temp_min = 최저온도
                                min.setText(temp3+"C");

                                double temp4 = main.get("temp_max").getAsDouble(); //temp_max = 최고온도
                                max.setText(temp4+"C");

                                double temp5 = main.get("humidity").getAsDouble(); //temp_max = 최고온도
                                hum.setText(temp5+"%");

                                JsonObject wind11 = result.get("wind").getAsJsonObject(); //메인
                                double wind1 = wind11.get("speed").getAsDouble();          //메인 안에 온도
                                wind.setText(wind1+"m/s");




                                //Weather Status
                                JsonArray weather1 = result.get("weather").getAsJsonArray(); //메인
                                String temp6 = weather1.get(0).getAsJsonObject().get("description").getAsString();          //메인 안에 온도
                                weather.setText(temp6);





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


                            }
                        }
                    });
        }


        // 리스트뷰에 출력되는 7일의 날씨 정보
        private void loadDailyForecast(double lat, double lon) {
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
