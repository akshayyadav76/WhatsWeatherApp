package com.example.akshay.whatsweatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
// api key   272e2cf91f2be45773876e2ea9c263fb
    EditText editText;
    TextView  textView;


    public void getweather(View view)
    {
        Weatherinfo weatherinfo =new Weatherinfo();

        try{
            if(editText.getText().toString().isEmpty())
            {
                Toast.makeText(this, "enter the city name", Toast.LENGTH_SHORT).show();
            }else {
                String weatherdetails = weatherinfo.execute("https://api.openweathermap.org/data/2.5/weather?q=" +
                        editText.getText().toString() + "&APPID=272e2cf91f2be45773876e2ea9c263fb").get();
                // Log.i("get",weatherdetails);
                JSONObject jsonObject = new JSONObject(weatherdetails);
                String weather = jsonObject.getString("weather");

                JSONArray jsonArray = new JSONArray(weather);

                String main = "";
                String description = "";

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject arrrobject = jsonArray.getJSONObject(i);
                    main = arrrobject.getString("main");
                    description = arrrobject.getString("description");
                }
                textView.setText("main :" + main + "\n" + "description :" + description);

            }
        }catch (Exception e)
        {e.printStackTrace();

        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.CityEditText);
        textView=findViewById(R.id.textView);

    }



    public class Weatherinfo extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            try {
                URL url = new URL(strings[0]);
               HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
               httpURLConnection.connect();

                InputStream in =httpURLConnection.getInputStream();
                InputStreamReader reader =new InputStreamReader(in);

                int data = reader.read();
                String apidetils = "";
                char current;
                while (data != -1)
                {
                    current =(char) data;
                    apidetils +=current;
                    data=reader.read();

                }
                return apidetils;

            }catch (Exception e)
            {e.printStackTrace();
            }


            return null;
        }


    }
}


