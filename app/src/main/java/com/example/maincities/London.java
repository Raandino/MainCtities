package com.example.maincities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class London extends AppCompatActivity {
    private TextView tiempo;//Aqui se almacena el tiempo
    private TextView t_descrip;//Aqui se almacena descripccion del tiempo
    private TextView temperatura;
    private TextView cities;
    String icon,main_tiempo,description,temp_c,d1,ciudades;
    private RequestQueue mQueue;
    DecimalFormat df = new DecimalFormat("#.00");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_london);

        tiempo = findViewById(R.id.tiempo);//encontrando el id del ViewText
        t_descrip =findViewById(R.id.t_descrip);//encontrando id del ViewText
        temperatura =findViewById(R.id.temperatura);//encontrando id del ViewText
        cities =findViewById(R.id.cities);
        mQueue = Volley.newRequestQueue(this);//instancia de requestQueue
        recibirDatos();
        jsonParse();

    }



    public void recibirDatos(){
        Bundle extra = getIntent().getExtras();
         d1 =extra.getString("dato01");
    }


    public void jsonParse() {//metodo jasonParse es donde se hace la consulta a la api de open wheather

        String url = "http://api.openweathermap.org/data/2.5/weather?q="+d1+"&APPID=35089672db223f72240e37f9e0753f51";//url del api de open wheather
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    /*------------------RESPUESTA JSON A LOS ARRAYS------------------*/
                    //JSONObject error = response.getJSONObject("cod");
                    //String Error = error.getString("cod");
                    String ciudad =  response.getString("name");
                    cities.setText(ciudad);
                    JSONArray jsonWea = response.getJSONArray("weather");//Respuesta a arreglo json "weather"
                    JSONObject weather = jsonWea.getJSONObject(0);
                    main_tiempo = weather.getString("main");//main es el tiempo del lugar
                    description = weather.getString("description");//description es la descrip del tiempo
                    icon = weather.getString("icon");
                    tiempo.setText(main_tiempo);///cambia el valor del TextView
                    t_descrip.append(description);///cambia el valor del TextView

                    JSONObject main = response.optJSONObject("main");//Respuesta a arreglo json "main" donde esta la temperatura
                    double temp_kelvin = main.getDouble("temp");
                    double temp_celsius = temp_kelvin - 273;
                    temp_c = df.format(temp_celsius);
                    temperatura.append(temp_c + "°C");
                } catch (JSONException error) {
                   // tiempo.setText("error");
                    error.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(London.this,"City Not Found",Toast.LENGTH_LONG).show();
                //tiempo.setText("error");
                error.printStackTrace();
            }
        });

       /* JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    /*------------------RESPUESTA JSON A LOS ARRAYS------------------*/
                    /*JSONObject main = response.optJSONObject("main");//Respuesta a arreglo json "main" donde esta la temperatura
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
        });*/
       // mQueue.add(request2);
        mQueue.add(request);

    }
}
