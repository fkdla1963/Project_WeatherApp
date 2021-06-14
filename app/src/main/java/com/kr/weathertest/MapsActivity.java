package com.kr.weathertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.kr.weathertest.databinding.ActivityMapsBinding;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 1000;
    private FusedLocationProviderClient mfusedLocation;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mfusedLocation = LocationServices.getFusedLocationProviderClient(this);

        StrictMode.enableDefaults();

        //TextView test1 = (TextView) findViewById(R.id.result); // 결과확인

        boolean start_lat = false;
        boolean start_lon = false;
        boolean end_lat = false;
        boolean end_lon = false;

        boolean name = false;
        boolean initem = false;
        String s_Start_lat = null;
        String s_Start_lon = null;
        String s_End_lat = null;
        String s_End_lon = null;
        String s_name = null;
        final String serviceKey = "duntrTnfNet3iwoXspPk3dQSisRgbZCIBlRV78lFspge9gcdrA2vLp19L6sC25lbEe9UaAXql58CURTHThlI1Q%3D%3D";
        try {

            URL url = new URL("http://api.data.go.kr/openapi/tn_pubr_public_sttree_stret_api?serviceKey=duntrTnfNet3iwoXspPk3dQSisRgbZCIBlRV78lFspge9gcdrA2vLp19L6sC25lbEe9UaAXql58CURTHThlI1Q%3D%3D&pageNo=0&numOfRows=7693&type=xml"); //총 개수 7693

            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱 시작");

            while (parserEvent != XmlPullParser.END_DOCUMENT){

                switch (parserEvent){

                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("sttreeStretNm")){  // 가로수길 이름
                            name = true;
                        }
                        if (parser.getName().equals("startLatitude")){  // 시작 경도
                            start_lat = true;
                        }
                        if (parser.getName().equals("startLongitude")){ // 시작 위도
                            start_lon = true;
                        }
                        if (parser.getName().equals("endLatitude")){    // 끝 경도
                            end_lat = true;
                        }
                        if (parser.getName().equals("endLongitude")){   // 끝 위도
                            end_lon = true;
                        }
                        break;

                    case XmlPullParser.TEXT:    //parser가 내용에 접근했을 때
                        if(name){
                            s_name = parser.getText();          //가로수길 이름
                            name = false;
                        }
                        if(start_lat){
                            s_Start_lat = parser.getText();     //가로수길 시작 위도
                            start_lat = false;
                        }
                        if(start_lon){
                            s_Start_lon = parser.getText();     //가로수길 시작 경도
                            start_lon = false;
                        }
                        if(end_lat){
                            s_End_lat= parser.getText();        //가로수길 끝 위도
                            end_lat = false;
                        }
                        if(end_lon){
                            s_End_lon = parser.getText();       //가로수길 끝 경도
                            end_lon = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){    // item을 만나면 이 함수 시작

                            ItemList data = new ItemList();
                            data.name = s_name;
                            data.s_lat = s_Start_lat;
                            data.s_lon = s_Start_lon;
                            data.e_lat = s_End_lat;
                            data.e_lon = s_End_lon;
                            DataList.apilist.add(data);

                            //test1.setText(test1.getText() + "이름 : " + s_name +"시작경도 : " + s_Start_lat +"시작위도 : " + s_Start_lon + "\n\n");
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();

            }

        }catch (Exception e){

        }
        Log.d("dd" , DataList.apilist.get(1).name);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {//처음 위치
        double a = getIntent().getDoubleExtra("위도" , 1);

        Log.i("위도" , String.valueOf(a));

        double b = getIntent().getDoubleExtra("경도" , 1);

        Log.i("경도" , String.valueOf(b));

        mMap = googleMap;
        LatLng location = new LatLng(a, b);//위치표시
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("현재 내위치");//주 이름
        //markerOptions.snippet("");//부제
        markerOptions.position(location);
        mMap.addMarker(markerOptions);
        int i=0;
        while (i<DataList.apilist.size()) {
            Double lat = Double.parseDouble(DataList.apilist.get(i).e_lat);
            Double lon = Double.parseDouble(DataList.apilist.get(i).e_lon);
            LatLng loc = new LatLng(lat, lon);
            markerOptions.title(DataList.apilist.get(i).name);
            markerOptions.position(loc);
            mMap.addMarker(markerOptions);
            i++;
            if(i==2000)
                break;
        }
        Log.d("d" , DataList.apilist.get(0).e_lat);
//        Double lat = Double.parseDouble(DataList.apilist.get(1).e_lat);
//        Double lon = Double.parseDouble(DataList.apilist.get(1).e_lon);
//        LatLng loc = new LatLng(lat, lon);//위치표시
//        markerOptions.position(loc);
//        mMap.addMarker(markerOptions);
        mMap.moveCamera((CameraUpdateFactory.newLatLngZoom(location, 13)));

    }


    public void onLastLocation(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE);

            return;
        }
        mfusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    double a = location.getLatitude();
                    double b = location.getLongitude();
                    System.out.println("위도값" + a);
                    System.out.println("경도값" + b);
                    mMap.addMarker(new MarkerOptions().position(myLocation).title("현재위치"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한체크 거부", Toast.LENGTH_SHORT).show();
                }
        }
    }
}