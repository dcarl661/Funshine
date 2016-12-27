package com.dcarl661.funshine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

//dcarl@earthlink.net 32209da56515c44506d4fffa54ac4ac1
public class WeatherActivity extends AppCompatActivity
{

    final String ss="http://seeicbrne.net/caphandler/Handler.ashx?un=supervisor&pw=txEv3VDYPTVOTfVfwPUcyA==&cap=none&type=lracebu&mmvd=3";
    //35.033594 -85.297851
    final String URL_BASE  = "http://api.openweathermap.org/data/2.5/forecast";
    final String URL_UNITS = "&units=imperial";
    final String URL_COORD = "?lat=35.033595&lon=-85.297851";
    final String URL_KEY   = "&APPID=32209da56515c44506d4fffa54ac4ac1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        final String url = URL_BASE+ URL_COORD + URL_UNITS +  URL_KEY;
        Log.v("Fun", url);

        //works sensor server request in XElement style
        StringRequest request = new StringRequest(Request.Method.GET, ss,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // convert the String response to XML
                        // if you use Simple, something like following should do it
                        //Serializer serializer = new Persister();
                        //serializer.read(ObjectType.class, response);
                        Log.v("SEN", "Res: "+ response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error response
                    }
                }
        );
        Volley.newRequestQueue(this).add(request);

        //https://developer.android.com/training/volley/request.html
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Fun", "Res: "+ response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.v("fun", "Err: " + error.getLocalizedMessage());
                    }
                });
        Volley.newRequestQueue(this).add(jsObjRequest);
    }




}
