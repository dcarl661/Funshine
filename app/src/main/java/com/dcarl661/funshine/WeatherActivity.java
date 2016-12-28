package com.dcarl661.funshine;

import android.*;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;


//dcarl@earthlink.net 32209da56515c44506d4fffa54ac4ac1
public class WeatherActivity extends AppCompatActivity
implements  GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
            LocationListener
{

    final int PERMISSION_FINE_LOCATION = 1;

    final String ss        = "http://seeicbrne.net/caphandler/Handler.ashx?un=supervisor&pw=txEv3VDYPTVOTfVfwPUcyA==&cap=none&type=lracebu&mmvd=3";
    //35.033594 -85.297851
    final String URL_BASE  = "http://api.openweathermap.org/data/2.5/forecast";
    final String URL_UNITS = "&units=imperial";
    final String URL_COORD = "?lat=35.033595&lon=-85.297851";
    final String URL_KEY   = "&APPID=32209da56515c44506d4fffa54ac4ac1";

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        downloadWeatherData(35.033595, -85.297851, 10);

    }

    public void downloadWeatherData(double lat, double lon, double alt)
    {
        final String fullCoords="?lat="+lat+"&lon="+lon;
        Log.v("Fun", fullCoords);

        final String url = URL_BASE+ fullCoords + URL_UNITS +  URL_KEY;
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

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //AccessFineLocation is found in the manfest
        // if the user hasn't given permission then this will ask
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_FINE_LOCATION);
            Log.v("Fun", "Requesting permissions");
        }
        else //permission already given
        {
            Log.v("Fun", "Starting location services");
            startLocationServices();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        downloadWeatherData(location.getLatitude(),location.getLongitude(),location.getAltitude());
    }

    //need a function to do this because we have to check and ask for permission
    public void startLocationServices()
    {
        //once connected onCreate....onStart....
        Log.v("Fun","Starting Location Service called.");
        try
        {
            LocationRequest req=LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,req,this);
            Log.v("Fun", "Requesting location updates");
        }
        catch(SecurityException sex)
        {
            //user rejected permission
            //inform user we need permission
            Log.v("Fun", sex.toString());
        }
    }
    //here we popup the dialog to ask for permission and process the result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode)
        {
            case PERMISSION_FINE_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.v("Fun", "onRequestPerms Granted");
                    startLocationServices();
                }
                else //did not grant request
                {
                    //show dialog warning user that with out permission we can't do anything
                    Log.v("Fun", "onRequestPerms NOT Granted");
                    Toast.makeText(this,"I can't run your location you denied permission", Toast.LENGTH_LONG);
                }
                break;
        }
    }

}
