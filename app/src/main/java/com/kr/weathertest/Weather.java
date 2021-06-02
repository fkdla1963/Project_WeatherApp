package com.kr.weathertest;

public class Weather {

    private Long date;
    private String timeZone;
    private Double temp;

    public Weather(Long date, String timeZone, Double temp) {
        this.date = date;
        this.timeZone = timeZone;
        this.temp = temp;
    }

    public Weather(Double temp) {
        this.temp = temp;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }
}
