package com.kr.weathertest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class resultActivity extends AppCompatActivity {

    TextView likeweather, tempminimum, tempmaximum, humtv, weatherall, windall, cloudper, ohrain, thrain ;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState) ;
        setContentView(R.layout.result_activity);

        likeweather = (TextView)findViewById(R.id.likeweathertv) ;
        tempminimum = (TextView)findViewById(R.id.mintemp) ;
        tempmaximum = (TextView)findViewById(R.id.maxtemp) ;
        humtv = (TextView)findViewById(R.id.humdata) ;
        weatherall = (TextView)findViewById(R.id.weathermain) ;
        windall = (TextView)findViewById(R.id.resultwind) ;
        cloudper = (TextView)findViewById(R.id.cloudinfo) ;
        ohrain = (TextView)findViewById(R.id.onehourrain) ;
        thrain = (TextView)findViewById(R.id.thrhourrain) ;

        Intent intent = getIntent() ;
        likeweather.setText("체감 온도 "+intent.getExtras().getString("resultfweather"));
        tempminimum.setText("최저 온도 "+intent.getExtras().getString("resultmintemp"));
        tempmaximum.setText("최고 온도 "+intent.getExtras().getString("resultmaxtemp"));
        humtv.setText("습도 "+intent.getExtras().getString("resulthum"));
        weatherall.setText("날씨 정보 "+intent.getExtras().getString("resultrealweather"));
        windall.setText("풍속 "+intent.getExtras().getString("resultwindspeed"));
        cloudper.setText("흐림 정보 "+intent.getExtras().getString("resultcloudall"));
        ohrain.setText("1시간동안 강수량 "+intent.getExtras().getString("resultrain1h"));
        thrain.setText("3시간동안 강수량 "+intent.getExtras().getString("resultrain3h"));
    }
}
