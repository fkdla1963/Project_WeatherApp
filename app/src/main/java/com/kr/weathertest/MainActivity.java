package com.kr.weathertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Object View;
    Button btn , btn2;
    TextView city , temp , weather , temp_max, temp_min, status, address1, time;
    final String key = "e62e6a9e08b2d9e9924fe1b6c229eb0b";  //api키

    GpsTracker gpsTracker;
    //gps 선언 시작

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    // gps 선언 끝
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.temp);
        temp_min = findViewById(R.id.temp_min);
        temp_max = findViewById(R.id.temp_max);
        status = findViewById(R.id.status);
        time = findViewById(R.id.updated_at);
        address1 = findViewById(R.id.address);

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this,Locale.getDefault());


        if (checkLocationServicesStatus()) {
            checkRunTimePermission();
        } else {
            showDialogForLocationServiceSetting();
        }

        gpsTracker = new GpsTracker(MainActivity.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();

        btn = findViewById(R.id.Button1);
        btn2 = findViewById(R.id.Button2);
        loadWeatherByCityName(latitude , longitude);
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

       // String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
       // String state = addresses.get(0).getAdminArea();
        address1.setText(city);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Toast.makeText(MainActivity.this, "버튼 수정입니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this , MainActivity2.class);
                intent.putExtra("위도" , latitude);
                intent.putExtra("경도" , longitude);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(MainActivity.this , MapsActivity.class);
                intent.putExtra("위도" , latitude);
                intent.putExtra("경도" , longitude);
                startActivity(intent);
            }
        });



       
    }
    //최상단 현재 날씨 정보

//        private void loadWeatherByCityName(double lat, double lon){
//            Ion.with(this)
//                    .load("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat +
//                            "&lon=" + lon + "&exclude=hourly,minutely&units=metric&lang=kr&appid=" + key)   //api주소
//                    .asJsonObject()
//                    .setCallback(new FutureCallback<JsonObject>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonObject result) {
//                            Log.d("rrr", result.toString());
//                            if (e != null) {
//                                e.printStackTrace();
//                                Toast.makeText(MainActivity.this, "server error", Toast.LENGTH_SHORT).show();
//                            } else {
//
//                                JsonObject current = result.get("current").getAsJsonObject(); //메인
//                                double temp1 = current.get("temp").getAsDouble();          //메인 안에 온도
//                                temp.setText(temp1 + "C");
//                                /*
//                                double temp2 = result.get("timezone").getAsDouble(); ????
//                                address.setText();??????
//                                */
//                                JsonObject daily = result.get("daily").getAsJsonObject();
//                                double temp3 = daily.get("min").getAsDouble(); //temp_min = 최저온도
//                                temp_min.setText((int) temp3);
//
//                                double temp4 = daily.get("max").getAsDouble(); //temp_max = 최고온도
//                                temp_max.setText((int) temp4);
//
//                                JsonObject weather = result.get("weather").getAsJsonObject();
//                                double temp5 = weather.get("main").getAsDouble(); //main = 날씨 매개 변수 그룹(비 , 눈 , 맑음 , 번개등)
//                                status.setText((int) temp5);
//
//
//
//                            }
//                        }
//                    });
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
                            Toast.makeText(MainActivity.this, "server error", Toast.LENGTH_SHORT).show();
                        }else{

                            Calendar calendar;
                            SimpleDateFormat dateFormat;
                            String date;

                            JsonObject main = result.get("main").getAsJsonObject(); //메인
                            double temp1 = main.get("temp").getAsDouble();          //메인 안에 온도
                            temp.setText(temp1+"C");


                            double temp3 = main.get("temp_min").getAsDouble(); //temp_min = 최저온도
                            temp_min.setText(temp3+"C");

                            double temp4 = main.get("temp_max").getAsDouble(); //temp_max = 최고온도
                            temp_max.setText(temp4+"C");

                            //Weather Status
                            JsonArray weather1 = result.get("weather").getAsJsonArray(); //메인
                            String temp5 = weather1.get(0).getAsJsonObject().get("description").getAsString();          //메인 안에 온도
                            status.setText(temp5);



                            //Current Time
                            calendar = Calendar.getInstance();
                            dateFormat = new SimpleDateFormat("h:mm a");
                            date = dateFormat.format(calendar.getTime());
                            time.setText(date);







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





    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if (check_result) {

                //위치 값을 가져올 수 있음
                ;
            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                } else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }





    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


}