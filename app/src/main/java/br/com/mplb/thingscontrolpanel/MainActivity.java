package br.com.mplb.thingscontrolpanel;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import br.com.mplb.thingscontrolpanel.weather.WeatherTask;

import static br.com.mplb.thingscontrolpanel.R.id.txtDate;
import static br.com.mplb.thingscontrolpanel.R.id.txtTemperature;

/**
 * Created by Marcos Bueno on 24/03/2017.
 */
public class MainActivity extends AppCompatActivity {

    private static int MORNING_BACKGROUND = 0;
    private static int AFTERNOON_BACKGROUND = 1;
    private static int NIGHT_BACKGROUND = 2;

    //I'll make this settings configurable, but for now...
    private static String TIMEZONE = "America/Sao_Paulo";//You may change it for tests
    private static String WEATHER_LOCATION = "curitiba, br";//Mean to be used by Yahoo Weather API
    private static String WEATHER_LOCATION_STRING = "Curitiba - PR";

    TextView txtTime;
    TextView txtDate;
    TextView txtIpAddress;
    private TextView txtTemperature;
    private TextView txtWeather;
    private TextView txtLocation;
    private ImageView imgWeather;

    ConstraintLayout layoutBackground;
    BroadcastReceiver broadcastReceiver;
    WeatherTask weatherTask;

    Handler handler = new Handler();

    private final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
    private final SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd MMMM yyyy");

    int imgBackground = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sdfTime.setTimeZone(TimeZone.getTimeZone(TIMEZONE));

        txtTime = (TextView) findViewById(R.id.txtTime);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtIpAddress = (TextView) findViewById(R.id.txtIpAddress);
        txtTemperature = (TextView) findViewById(R.id.txtTemperature);
        txtWeather= (TextView) findViewById(R.id.txtWeather);
        txtLocation= (TextView) findViewById(R.id.txtLocation);
        imgWeather= (ImageView) findViewById(R.id.imgWeather);

        layoutBackground = (ConstraintLayout) findViewById(R.id.layoutBackground);

        txtTime.setText(sdfTime.format(new Date()));
        txtDate.setText(sdfDate.format(new Date()));
        setBackground();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    txtTime.setText(sdfTime.format(new Date()));
                    setBackground();
                }else if(intent.getAction().compareTo(ConnectivityManager.CONNECTIVITY_ACTION) == 0) {
                    showIpAdress();
                }
            }
        };



        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        showIpAdress();

        registerWeatherTask();
    }

    private void registerWeatherTask() {
        handler.postDelayed(new Runnable() {
            public void run() {
                //A Task can be executed only once, so we create a new one
                weatherTask = new WeatherTask(MainActivity.this, txtTemperature, txtWeather,txtLocation, imgWeather);
                weatherTask.execute();

                handler.postDelayed(this, 30*60*1000);//1.800.000 ms = 1.800 s = 30 minutes Just FYI
            }
        }, 1000);
    }


    private void showIpAdress() {
        //TODO: Check for other types of connection

        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();


        int ip = wifiInfo.getIpAddress();
        txtIpAddress.setText(wifiInfo.getSSID() + " - " + Formatter.formatIpAddress(ip));//TODO: Make a listener for network changes
    }

    private void setBackground() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE));
        int hourOfTheDay = c.get(Calendar.HOUR_OF_DAY);

        if(hourOfTheDay >= 6 && hourOfTheDay < 12){
            if(imgBackground != MORNING_BACKGROUND) { //prevent unnecessary draw
                layoutBackground.setBackground(getDrawable(R.drawable.morning));
                imgBackground = MORNING_BACKGROUND;
            }
        }else if(hourOfTheDay >=12 && hourOfTheDay < 18){
            if(imgBackground != AFTERNOON_BACKGROUND) { //prevent unnecessary draw
                layoutBackground.setBackground(getDrawable(R.drawable.afternoon));
                imgBackground = AFTERNOON_BACKGROUND;
            }
        }else{
            if(imgBackground != NIGHT_BACKGROUND) { //prevent unnecessary draw
                layoutBackground.setBackground(getDrawable(R.drawable.night));
                imgBackground = NIGHT_BACKGROUND;
            }
        }
    }

    @Override
    public void onStop() { //Not sure if it is necessary on Android Things, but.....
        super.onStop();
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
    }
}
