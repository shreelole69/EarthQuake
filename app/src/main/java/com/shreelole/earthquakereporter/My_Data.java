package com.shreelole.earthquakereporter;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class My_Data {
    String magnitude ;
    private String location_one , location_two ;
    private long mtime ;
    private String url ;
    My_Data(double magni , String loca , long time , String url)
    {
        mtime = time ;
        int dec=0 ;
        if(loca.contains("of"))
        {
            dec = loca.indexOf("of");
            location_one = loca.substring(0 , dec+3);
            location_two = loca.substring(dec+3 , loca.length());
        }
        else{
            location_two = loca ;
            location_one ="Near the";
        }
        DecimalFormat formatter = new DecimalFormat("0.0");
        magnitude = formatter.format(magni);
        this.url = url ;
    }

    public String getMag() {
        return magnitude;
    }

    public String getLocation_one() {
        return  location_one;
    }
    public String getLocation_two(){
        return location_two;
    }
    public  String getUrl(){return  url ;}
    public String getDate() {

        Date dateObject = new Date(mtime);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd,YYYY");

        String dateToDisplay = dateFormatter.format(dateObject);
        return  dateToDisplay;
    }

    public String getTime()
    {
        Date dateObject = new Date(mtime);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String time_s = timeFormat.format(dateObject);
        return  time_s ;
    }
    public int Color()
    {
        if(Double.parseDouble(magnitude)>10)
        {
            return R.color.magnitude10plus;
        }
        else if(Double.parseDouble(magnitude)>=9)
        {
            return  R.color.magnitude9;
        }
        else if(Double.parseDouble(magnitude)>=8)
        {
            return  R.color.magnitude8;
        }
        else if(Double.parseDouble(magnitude)>=7)
        {
            return  R.color.magnitude7;
        }
        else if(Double.parseDouble(magnitude)>=6)
        {
            return  R.color.magnitude6;
        }
        else if(Double.parseDouble(magnitude)>=5)
        {
            return  R.color.magnitude5;
        }
        else if(Double.parseDouble(magnitude)>=4)
        {
            return  R.color.magnitude4;
        }
        else if(Double.parseDouble(magnitude)>=3)
        {
            return  R.color.magnitude3;
        }
        else
        {
            return  R.color.magnitude2;
        }
    }
}
