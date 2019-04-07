package com.example.encuentrahorro_v10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.widget.TextView;

public class Activity_DetalleRecomendacion extends AppCompatActivity {

    TextView tv_producto;
    TextView tv_precio;
    TextView tv_descripcion;
    TextView tv_fecha;
    TextView tv_nombreusuario;
    TextView tv_nummegusta;
    TextView tv_numcomentarios;
    private String webservice_url = "http://webapp-encuentrahorro.herokuapp.com" +
            "./api_recomendaciones?user_hash=dc243fdf1a24cbced74db81708b30788&action=get&id_recomendacion=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__detalle_recomendacion);
        // Inicialización de los componentes de la vista (layout)
        tv_producto = findViewById(R.id.tv_producto);
        tv_precio = findViewById(R.id.tv_precio);
        tv_descripcion = findViewById(R.id.tv_descripcion);
        tv_fecha = findViewById(R.id.tv_fecha);
        tv_nombreusuario = findViewById(R.id.tv_nombreusuario);
        tv_nummegusta = findViewById(R.id.tv_nummegusta);
        tv_numcomentarios = findViewById(R.id.tv_numcomentarios);

        //Objeto tipo Intent para recuperar el parametro enviado
        Intent intent = getIntent();
        //Se almacena el id_cliente enviado
        String id_recomendacion = intent.getStringExtra(Activity_ResultadoBusqueda.ID_RECOMENDACION);
        //Se cocnatena la url con el id_cliente para obtener los datos el cliente
        webservice_url+=id_recomendacion;
        webServiceRest(webservice_url);
    }


    private void webServiceRest(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_recomendacion;
        String fecha;
        String descripcion;
        String precio;
        String latitud_ubi;
        String longitud_ubi;
        String duracion;
        String id_categoria;
        String id_producto;
        String nombre_usuario;
        String num_megusta;
        String num_comentarios;
        String promedio_evaluaciones;
        String recomendacion_activa;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                id_recomendacion = jsonObject.getString("id_recomendacion");
                fecha = jsonObject.getString("fecha");
                descripcion = jsonObject.getString("descripcion");
                precio = jsonObject.getString("precio");
                latitud_ubi = jsonObject.getString("latitud_ubi");
                longitud_ubi = jsonObject.getString("longitud_ubi");
                duracion = jsonObject.getString("duracion");
                id_categoria = jsonObject.getString("id_categoria");
                id_producto = jsonObject.getString("id_producto");
                nombre_usuario = jsonObject.getString("nombre_usuario");
                num_megusta = jsonObject.getString("num_megusta");
                num_comentarios = jsonObject.getString("num_comentarios");
                promedio_evaluaciones = jsonObject.getString("promedio_evaluaciones");
                recomendacion_activa = jsonObject.getString("recomendacion_activa");

                // Muestreo de los datos de Recomendacion en la vista correspondiente
                tv_producto.setText(id_producto);
                tv_precio.setText(precio);
                tv_descripcion.setText(descripcion);
                tv_fecha.setText(fecha);
                tv_nombreusuario.setText(nombre_usuario);
                tv_nummegusta.setText(num_megusta);
                tv_numcomentarios.setText(num_comentarios);
//                URL newurl = new URL(images_url+imagen);
//                Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
//                iv_imagen.setImageBitmap(mIcon_val);

            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
/*
            catch (MalformedURLException e) {
                Log.e("Error 103",e.getMessage());
            }
            catch (IOException e) {
                Log.e("Error 104",e.getMessage());
            }
*/
        }
    }


} // Cierre de la clase principal (Activity_DetalleRecomendacion).
