package com.shreelole.earthquakereporter;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */
    private static final String my_jason = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6";


    private QueryUtils() {
    }
    public  static List<My_Data> fetchEarthquake() throws Exception {
     URL url = Url_Creator(my_jason);
     String Jason = "";
     if(url!=null)
     {
       Jason = MakeHttpRequest(url);
     }
    List<My_Data> temp =null ;
     temp = extractEarthquakes(Jason);
     return temp ;
    }
    public static URL Url_Creator(String stringurl)  {
        URL url = null ;
        try {
            url = new URL(stringurl);
        } catch (MalformedURLException e) {
            Log.e("MY_LOG", "Error with creating URL ", e);
        }
        return url ;
    }
    private static String MakeHttpRequest(URL url) throws IOException {
        String jasonresponse ="";
        if(url==null)
        {
            return jasonresponse;
        }
        HttpURLConnection makehttp = null;
        InputStream response = null;
        try
        {
         makehttp = (HttpURLConnection) url.openConnection();
            makehttp.setReadTimeout(10000);
            makehttp.setConnectTimeout(15000 /* milliseconds */);
            makehttp.setRequestMethod("GET");
            makehttp.connect();
            if(makehttp.getResponseCode()==200)
            {
                response = makehttp.getInputStream();
                jasonresponse = readFromInputStream(response);
            }else {
                Log.e("MY_LOG", "Error response code: " + makehttp.getResponseCode());
            }
        }
        catch (Exception e)
        {
            Log.e("MY_LOG", "Problem retrieving the earthquake JSON results.", e);
        }
        finally {
            if (makehttp != null) {
                makehttp.disconnect();
            }
            if (response != null) {
                response.close();
            }
        }
        Log.i("LOG" , jasonresponse);
       return jasonresponse;
    }
    static String readFromInputStream(InputStream response) throws IOException {
        StringBuilder str = new StringBuilder();
        if (response != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(response, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                str.append(line);
                line = reader.readLine();
            }
        }
        return str.toString();
    }
    public static ArrayList<My_Data> extractEarthquakes(String my_jason) {
     ArrayList<My_Data> earthquakes = new ArrayList<>();
        try {
            JSONObject reader = new JSONObject(my_jason);
            JSONArray jason_array = reader.optJSONArray("features");
            for(int i=0 ; i<jason_array.length() ; i++)
            {
                JSONObject temp = jason_array.getJSONObject(i);
                JSONObject properties = temp.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String loca = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                long  timeInMilliseconds = time ;
                earthquakes.add(new My_Data(mag , loca , time , url));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return earthquakes;
    }

}