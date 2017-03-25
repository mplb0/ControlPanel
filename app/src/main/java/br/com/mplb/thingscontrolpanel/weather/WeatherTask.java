package br.com.mplb.thingscontrolpanel.weather;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.mplb.thingscontrolpanel.weather.json.model.Query;
import br.com.mplb.thingscontrolpanel.weather.json.model.WeatherData;

/**
 * Created by Marcos Bueno on 24/03/2017.
 */

public class WeatherTask extends AsyncTask<String, Void, Query> {

    public final String LOG_TAG = WeatherTask.class.getSimpleName();

    //TODO: Make the location user customizable and probably use yql4j
    //I know this url here is ugly, hard coded and everything, but for now it works
    private static String YAHOO_WEATHER_API_LINK = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22curitiba%2C%20br%22)%20and%20u=%27c%27&format=json&diagnostics=true";
    private Context context;
    private TextView txtTemperature;
    private TextView txtWeather;
    private TextView txtLocation;
    private ImageView imgWeather;

    public WeatherTask(Context context, TextView txtTemperature, TextView txtWeather, TextView txtLocation, ImageView imgWeather){
        this.context = context;
        this.txtTemperature = txtTemperature;
        this.txtWeather = txtWeather;
        this.txtLocation = txtLocation;
        this.imgWeather = imgWeather;
    }

    @Override
    protected Query doInBackground(String... params) {
        Log.d(LOG_TAG, "Start weather update");

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJsonStr = null;

        try {

            Uri builtUri = Uri.parse(YAHOO_WEATHER_API_LINK).buildUpon()
                    .build();

            URL url = new URL(builtUri.toString());


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.
                return null;
            }
            forecastJsonStr = buffer.toString();

            Gson gson = new Gson();

            WeatherData data = gson.fromJson(forecastJsonStr, WeatherData.class);

            Query query = data.getQuery();
            
            Log.d(LOG_TAG,"Temp = " + query.getResults().getChannel().getItem().getCondition().getTemp());
            Log.d(LOG_TAG,"City = " + query.getResults().getChannel().getLocation().getCity());

            Log.d(LOG_TAG, "We get the weather, yaaaaaaaaay! \\o/ ");

            return query;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error "+ e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    /**
     * Runs on UI Thread, so we can update all the UI texts and images direct here
     */
    @Override
    protected void onPostExecute(Query query) {
        super.onPostExecute(query);

        //TODO: Make some use with all the information returned.

        String tempUnit = query.getResults().getChannel().getUnits().getTemperature();

        String weatherTemp = query.getResults().getChannel().getItem().getCondition().getTemp();

        StringBuilder location = new StringBuilder();
        location.append(query.getResults().getChannel().getLocation().getCity());
        location.append(", ");
        location.append(query.getResults().getChannel().getLocation().getRegion());
        location.append(" - ");
        location.append(query.getResults().getChannel().getLocation().getCountry());

        //Wanna know what this code is? Look here: https://developer.yahoo.com/weather/documentation.html#codes
        String weatherCode = query.getResults().getChannel().getItem().getCondition().getCode();

        txtTemperature.setText(weatherTemp + "º " + tempUnit);
        txtLocation.setText(location.toString());
        txtWeather.setText(Util.getWeatherText(Integer.valueOf(weatherCode)));
        imgWeather.setImageResource(Util.getWeatherImage(Integer.valueOf(weatherCode)));
    }
}
