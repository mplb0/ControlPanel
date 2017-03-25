package br.com.mplb.thingscontrolpanel.weather;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.mplb.thingscontrolpanel.R;

/**
 * Created by Marcos Bueno on 24/03/2017.
 */

public class Util {

    //Based on this https://gist.github.com/ericoporto/c867f7d67c7ced22c7e1
    static Map<Integer, String> weatherCodes = Collections.unmodifiableMap(new HashMap<Integer, String>(){{
        put(0,  "Tornado");                       // tornado
        put(1,  "Tempestade Tropical");           // tropical storm
        put(2,  "Furacão");                       // hurricane
        put(3,  "Tempestade Severa");             // severe thunderstorms
        put(4,  "Trovoadas");                     // thunderstorms
        put(5,  "Chuva e Neve");                  // mixed rain and snow
        put(6,  "Chuva e Granizo Fino");          // mixed rain and sleet
        put(7,  "Neve e granizo fino");           // mixed snow and sleet
        put(8,  "Garoa gélida");                  // freezing drizzle
        put(9,  "Garoa");                         // drizzle
        put(10, "Chuva gélida");                  // freezing rain
        put(11, "Chuvisco");                      // showers
        put(12, "Chuva");                         // showers
        put(13, "Neve em flocos finos");          // snow flurries
        put(14, "Leve precipitação de neve");     // light snow showers
        put(15, "Ventos com neve");               // blowing snow
        put(16, "Neve");                          // snow
        put(17, "Chuva de granizo");              // hail
        put(18, "Pouco granizo");                 // sleet
        put(19, "Pó em suspensão");               // dust
        put(20, "Neblina");                       // foggy
        put(21, "Névoa seca");                    // haze
        put(22, "Enfumaçado");                    // smoky
        put(23, "Vendaval");                      // blustery
        put(24, "Ventando");                      // windy
        put(25, "Frio");                          // cold
        put(26, "Nublado");                       // cloudy
        put(27, "Muitas nuvens (noite)");         // mostly cloudy (night)
        put(28, "Muitas nuvens (dia)");           // mostly cloudy (day)
        put(29, "Parcialmente nublado (noite)");  // partly cloudy (night)
        put(30, "Parcialmente nublado (dia)");    // partly cloudy (day)
        put(31, "Céu limpo (noite)");             // clear (night)
        put(32, "Ensolarado");                    // sunny
        put(33, "Tempo bom (noite)");             // fair (night)
        put(34, "Tempo bom (dia)");               // fair (day)
        put(35, "Chuva e granizo");               // mixed rain and hail
        put(36, "Quente");                        // hot
        put(37, "Tempestades isoladas");          // isolated thunderstorms
        put(38, "Tempestades esparsas");          // scattered thunderstorms
        put(39, "Tempestades esparsas");          // scattered thunderstorms
        put(40, "Chuvas esparsas");               // scattered showers
        put(41, "Nevasca");                       // heavy snow
        put(42, "Tempestades de neve esparsas");  // scattered snow showers
        put(43, "Nevasca");                       // heavy snow
        put(44, "Parcialmente nublado");          // partly cloudy
        put(45, "Chuva com trovoadas");           // thundershowers
        put(46, "Tempestade de neve");            // snow showers
        put(47, "Relâmpagos e chuvas isoladas");  // isolated thundershowers
        put(3200, "Não disponível");               // not available
    }});



    public static String getWeatherText(Integer weatherCode){
        return weatherCodes.get(weatherCode);
    }

    public static int getWeatherImage(Integer weatherCode) {
        switch(weatherCode){
            case 31:
            case 32:
            case 33:
            case 34:
            case 36:
                return R.drawable.art_clear;
            case 26:
            case 27:
            case 28:
                return R.drawable.art_clouds;
            case 19:
            case 20:
            case 21:
            case 22:
                return R.drawable.art_fog;
            case 29:
            case 30:
            case 44:
                return R.drawable.art_light_clouds;
            case 6:
            case 8:
            case 9:
            case 11:
            case 18:
                return R.drawable.art_light_rain;
            case 1:
            case 10:
            case 12:
            case 17:
            case 23:
            case 24:
            case 35:
            case 40:
            case 47:
                return R.drawable.art_rain;
            case 5:
            case 7:
            case 13:
            case 14:
            case 15:
            case 16:
            case 25:
            case 43:
            case 46:
                return R.drawable.art_snow;
            case 3:
            case 4:
            case 37:
            case 38:
            case 39:
            case 45:
                return R.drawable.art_storm;
            default:
                return -1;
        }
    }
}
