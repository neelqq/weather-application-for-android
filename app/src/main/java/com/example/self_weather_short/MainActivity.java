package com.example.self_weather_short;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText ed;
    TextView tv_city,temp_av,sky,h_L_temp;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed=findViewById(R.id.editTextTextPersonName);
        tv_city=findViewById(R.id.city_name);
        temp_av=findViewById(R.id.tv_temp);
        sky=findViewById(R.id.sky);
        h_L_temp=findViewById(R.id.h_L_temp);
        imageView=findViewById(R.id.iv);


        String url="http://api.openweathermap.org/data/2.5/weather?q=bardhaman&appid=5bb5df0a41d24d65cd8db11d7591912b";

    }
    public void get(View v){

        String city_name=ed.getText().toString();
        String url="https://api.openweathermap.org/data/2.5/weather?q="+city_name+"&appid=5bb5df0a41d24d65cd8db11d7591912b";

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String city = response.getString("name");
                    tv_city.setText(city);

                    JSONArray array1 = response.getJSONArray("weather");
                    JSONObject object_arr = array1.getJSONObject(0);
                    String sky_w = object_arr.getString("main");

                    sky.setText(sky_w);



                        JSONObject object1 = response.getJSONObject("main");
                        String temp = object1.getString("temp");
                        String temp_h = object1.getString("temp_max");
                        String temp_l = object1.getString("temp_min");

                        Double d_temp = Double.parseDouble(temp) - 273.15;
                        float f_temp = d_temp.floatValue();

                        Double d_temp_h = Double.parseDouble(temp_h) - 273.15;
                        float f_temp_h = d_temp_h.floatValue();

                        Double d_temp_l = Double.parseDouble(temp_l) - 273.15;
                        float f_temp_l = d_temp_l.floatValue();

                        temp_av.setText(f_temp + "C");
                        h_L_temp.setText(f_temp_h + "    " + f_temp_l);
                        if(f_temp<=20){
                            imageView.setImageResource(R.drawable.cold_cloude);
                        }
                        else if (sky_w.equals("Clouds")) {
                            imageView.setImageResource(R.drawable.clude);
                        }
                        else if (sky_w.equals("Haze")) {
                        imageView.setImageResource(R.drawable.haze);
                        }
                        else if (sky_w.equals("Clear")) {
                        imageView.setImageResource(R.drawable.sun);

                    }
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "error 1", Toast.LENGTH_SHORT).show();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error 2", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(objectRequest);
    }
}