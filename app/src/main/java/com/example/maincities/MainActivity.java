package com.example.maincities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity {

    private TextView tiempo;//Aqui se almacena el tiempo
    private TextView t_descrip;//Aqui se almacena descripccion del tiempo
    private TextView temperatura;
    private RequestQueue mQueue;//
    DecimalFormat df = new DecimalFormat("#.00");
    Button GoLon,GoNew,GoMan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tiempo = findViewById(R.id.tiempo);//encontrando el id del ViewText
        t_descrip =findViewById(R.id.t_descrip);//encontrando id del ViewText
        temperatura =findViewById(R.id.temperatura);//encontrando id del ViewText
        Button GoLon = findViewById(R.id.button_parse);//encontrando id del Button

        mQueue = Volley.newRequestQueue(this);//instancia de requestQueue
        GoLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();///Metodo encargado de consultar el json
            }


        });//aqui definimos lo que pasa cuando se le da click al botton


        /*
        GoLon=(Button) findViewById(R.id.GoLon);
        GoLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(MainActivity.this,London.class);
            startActivity(i);
            }
        });

        GoNew=(Button) findViewById(R.id.GoNew);
        GoNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,NewYork.class);
                startActivity(i);

            }
        });

        GoMan=(Button) findViewById(R.id.GoMan);
        GoMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Managua.class);
                startActivity(i);

            }

        });
            */


    }

    private void jsonParse(){//metodo jasonParse es donde se hace la consulta a la api de open wheather

        String url ="https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22";//url del api de open wheather
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                /*------------------RESPUESTA JSON A LOS ARRAYS------------------*/
                    JSONArray jsonWea = response.getJSONArray("weather");//Respuesta a arreglo json "weather"
                    JSONObject weather = jsonWea.getJSONObject(0);
                    String main_tiempo =  weather.getString("main");//main es el tiempo del lugar
                    String description =  weather.getString("description");//description es la descrip del tiempo
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

        final JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    /*------------------RESPUESTA JSON A LOS ARRAYS------------------*/
                    JSONObject main = response.optJSONObject("main");//Respuesta a arreglo json "main" donde esta la temperatura
                    double temp_kelvin = main.getDouble("temp");
                    double temp_celsius=temp_kelvin-273;
                    String temp_c = df.format(temp_celsius);
                    temperatura.append(temp_c+"°C");
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
