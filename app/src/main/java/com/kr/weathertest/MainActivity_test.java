package com.kr.weathertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class MainActivity_test extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        StrictMode.enableDefaults();

        TextView test1 = (TextView) findViewById(R.id.result); // 결과확인

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

            URL url = new URL("http://api.data.go.kr/openapi/tn_pubr_public_sttree_stret_api?serviceKey=duntrTnfNet3iwoXspPk3dQSisRgbZCIBlRV78lFspge9gcdrA2vLp19L6sC25lbEe9UaAXql58CURTHThlI1Q%3D%3D&pageNo=0&numOfRows=7693&type=xml");

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

                            test1.setText(test1.getText() + "이름 : " + s_name +"시작경도 : " + s_Start_lat +"시작위도 : " + s_Start_lon + "\n\n");
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();

            }

        }catch (Exception e){

        }
    }
}