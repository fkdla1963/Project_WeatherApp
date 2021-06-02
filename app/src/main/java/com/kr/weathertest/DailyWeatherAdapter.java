package com.kr.weathertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DailyWeatherAdapter extends ArrayAdapter<Weather> {

    private Context context;
    private List<Weather> weatherList;

    public DailyWeatherAdapter(@NonNull Context context, @NonNull List<Weather> weatherList) {
        super(context, 0, weatherList);
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_weather, parent ,false);

        // 아이템 날짜
        TextView tvdate = convertView.findViewById(R.id.tvDate);
        // 아이템 온도
        TextView tvTemp = convertView.findViewById(R.id.tvTemp);
        // 리스트 모델 불러오기
        Weather weather = weatherList.get(position);
        tvTemp.setText(weather.getTemp()+"C");

        // 날짜 불러오기
        Date date = new Date(weather.getDate()*1000);
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM yy", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone(weather.getTimeZone()));
        tvdate.setText(dateFormat.format(date));

        return convertView;
    }
}
