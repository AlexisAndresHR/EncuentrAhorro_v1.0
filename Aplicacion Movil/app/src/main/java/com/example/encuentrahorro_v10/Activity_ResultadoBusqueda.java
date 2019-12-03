package com.example.encuentrahorro_v10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class Activity_ResultadoBusqueda extends AppCompatActivity {

    TextView tv_parametros;

    private ListView lv_resultados_rec;
    private ArrayAdapter adapter;
    private String url_consulta = "http://webapp-encuentrahorro.herokuapp.com" +
            "/api_recomendaciones?user_hash=dc243fdf1a24cbced74db81708b30788&action=get&";
    public static final String ID_RECOMENDACION = "1"; // Variable para identificar la Recomendación seleccionada.

    private ArrayAdapter id_producto_cons;
    private String url_cons_id = "http://webapp-encuentrahorro.herokuapp.com" +
            "/api_tipos_productos?user_hash=dc243fdf1a24cbced74db81708b30788&action=get&";
    private String id_relacionado;

    // Variables probando ...
    String info_recomendaciones [][];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__resultado_busqueda);
        tv_parametros = findViewById(R.id.tv_parametros);// Línea para textview de prueba

        recibirParametros();

        StringBuilder sb = new StringBuilder();
        sb.append(url_consulta);
        sb.append("id_producto="+id_relacionado);
        Log.e("URL",sb.toString());
        webServiceRest(sb.toString());

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        lv_resultados_rec = findViewById(R.id.lv_resultados_rec);
        //adapter = new ArrayAdapter(this, R.layout.recomendacion_item);
        //lv_resultados_rec.setAdapter(adapter);
        lv_resultados_rec.setAdapter(new Adaptador_ResultadoBusqueda(this, info_recomendaciones)); // Probando...
/*
        StringBuilder sb = new StringBuilder();
        sb.append(url_consulta);
        sb.append("id_producto="+id_relacionado);
        Log.e("URL",sb.toString());
        webServiceRest(sb.toString());
*/
        lv_resultados_rec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.e("ITEM", lv_resultados_rec.getItemAtPosition(position).toString());
                //String datos_recomendacion[] =
                //        lv_resultados_rec.getItemAtPosition(position).toString().split(">");
                //String id_rec = datos_recomendacion[0];
                String id_rec = info_recomendaciones[position][0];
                Log.e("ID_RECOMENDACION",id_rec);
                Intent i = new Intent(Activity_ResultadoBusqueda.this, Activity_DetalleRecomendacion.class);
                i.putExtra(ID_RECOMENDACION,id_rec);
                startActivity(i);
            }
        });
    }

    /**
     * Método para recibir los criterios o parámetros de búsqueda, provenientes del Activity_Buscar.
     */
    private void recibirParametros() {
        Bundle parametros = getIntent().getExtras();
        String palabra_clave = parametros.getString("parametro1p");
        Log.v("  Valor como parámetro", palabra_clave);
        tv_parametros.setText("Resultados para '" + palabra_clave + "'");

        StringBuilder sb2 = new StringBuilder();
        sb2.append(url_cons_id);
        sb2.append("nombre_producto="+palabra_clave);
        Log.e("  URL 1 obtenida: ",sb2.toString());
        consultaTipoProducto(sb2.toString());
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
        String id_tienda;
        String rec_confiable;
        String rec_falsa;
        String num_comentarios;
        String promedio_evaluaciones;
        String recomendacion_activa;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        info_recomendaciones = new String[(int)jsonArray.length()][4]; // Inicializa un arreglo dinámico bidimensional para los resultados de la consulta.
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
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
                id_tienda = jsonObject.getString("id_tienda");
                rec_confiable = jsonObject.getString("rec_confiable");
                rec_falsa = jsonObject.getString("rec_falsa");
                num_comentarios = jsonObject.getString("num_comentarios");
                promedio_evaluaciones = jsonObject.getString("promedio_evaluaciones");
                recomendacion_activa = jsonObject.getString("recomendacion_activa");

                //adapter.add(id_recomendacion + " > " + fecha + "    $" + precio + " \n " + descripcion);

                info_recomendaciones [i][0] = id_recomendacion;
                info_recomendaciones [i][1] = fecha;
                info_recomendaciones [i][2] = "$"+precio;
                info_recomendaciones [i][3] = descripcion;
/*
                Log.e("ID_RECOMENDACION: ",info_recomendaciones[i][0]);
                Log.e("FECHA: ",info_recomendaciones[i][1]);
                Log.e("PRECIO: ",info_recomendaciones[i][2]);
                Log.e("DESCRIPCION: ",info_recomendaciones[i][3]);
*/
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }


    private void consultaTipoProducto(String requestURL){
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
            parseInformation2(webServiceResult);
        }catch(Exception e){
            Log.e("Error 103",e.getMessage());
        }
    }

    private void parseInformation2(String jsonResult){
        JSONArray jsonArray = null;
        String id_producto;
        String id_categoria;
        String nombre_producto;
        String imagen_producto;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 104",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_producto = jsonObject.getString("id_producto");
                id_categoria= jsonObject.getString("id_categoria");
                nombre_producto = jsonObject.getString("nombre_producto");
                imagen_producto = jsonObject.getString("imagen_producto");

                //adapter.add(id_producto + " " + nombre_producto);
                id_relacionado = id_producto;
            }catch (JSONException e){
                Log.e("Error 105",e.getMessage());
            }
        }
    }


// Fragmento de código para la configuración estética de la lista de recomendaciones (Resultado de Búsqueda).



} // Cierre de la clase principal.
