package com.example.maincities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class NewYork extends AppCompatActivity {


    private TextView tiempo;//Aqui se almacena el tiempo
    private TextView t_descrip;//Aqui se almacena descripccion del tiempo
    private TextView temperatura;
    String icon,main_tiempo,description,temp_c;
    private RequestQueue mQueue;
    DecimalFormat df = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_york);
        tiempo = findViewById(R.id.tiempo);//encontrando el id del ViewText
        t_descrip =findViewById(R.id.t_descrip);//encontrando id del ViewText
        temperatura =findViewById(R.id.temperatura);//encontrando id del ViewText
        mQueue = Volley.newRequestQueue(this);//instancia de requestQueue
        jsonParse();
    }

    public void jsonParse() {//metodo jasonParse es donde se hace la consulta a la api de open wheather

        String url = "http://api.openweathermap.org/data/2.5/weather?q=New York&APPID=35089672db223f72240e37f9e0753f51";//url del api de open wheather
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    /*------------------RESPUESTA JSON A LOS ARRAYS------------------*/
                    JSONArray jsonWea = response.getJSONArray("weather");//Respuesta a arreglo json "weather"
                    JSONObject weather = jsonWea.getJSONObject(0);
                    main_tiempo = weather.getString("main");//main es el tiempo del lugar
                    description = weather.getString("description");//description es la descrip del tiempo
                    icon = weather.getString("icon");
                    tiempo.append(main_tiempo);///cambia el valor del TextView
                    t_descrip.append(description);///cambia el valor del TextView
                } catch (JSONException error) {
                    error.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    /*------------------RESPUESTA JSON A LOS ARRAYS------------------*/
                    JSONObject main = response.optJSONObject("main");//Respuesta a arreglo json "main" donde esta la temperatura
                    double temp_kelvin = main.getDouble("temp");
                    double temp_celsius = temp_kelvin - 273;
                    temp_c = df.format(temp_celsius);
                    temperatura.append(temp_c + "°C");
                } catch (JSONException error) {
                    error.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request2);
        mQueue.add(request);

    }

}
