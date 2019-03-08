package com.shreelole.earthquakereporter;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView earthquakeListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        earthquakeListView = (ListView) findViewById(R.id.list);
        My_Asynk my_asynk =new My_Asynk();
        my_asynk.execute();

    }
    private class My_Asynk extends AsyncTask<String , Void , List<My_Data>>
    {
        @Override
        protected List<My_Data> doInBackground(String... strings) {
            List<My_Data> temp = null;
            try {
                temp = QueryUtils.fetchEarthquake();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return temp ;
        }

        @Override
        protected void onPostExecute(List<My_Data> my_data) {
            final MyAdapter adapter = new MyAdapter(MainActivity.this,  my_data);
            earthquakeListView.setAdapter(adapter);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    My_Data temp = (My_Data) adapter.getItem(i);
                    Uri webpage = Uri.parse(temp.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
                }
            });

        }
    }
}
