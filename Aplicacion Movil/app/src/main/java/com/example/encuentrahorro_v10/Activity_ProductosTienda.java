package com.example.encuentrahorro_v10;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_ProductosTienda extends AppCompatActivity {

    TextView tv_nombre_tienda;

    private ListView lv_lista_tienda;
    private ArrayAdapter adapter;
    private String url_consulta = "http://webapp-encuentrahorro.herokuapp.com" +
            "/api_recomendaciones?user_hash=dc243fdf1a24cbced74db81708b30788&action=get&";
    public static final String ID_RECOMENDACION = "1"; // Variable para identificar la Recomendación seleccionada.

    private ArrayAdapter id_producto_cons;
    private String url_productosxtienda = "http://webapp-encuentrahorro.herokuapp.com" +
            "/api_tiendas?user_hash=dc243fdf1a24cbced74db81708b30788&action=get&";
    private String id_relacionado;

    private String url_cons_tipoprod = "http://webapp-encuentrahorro.herokuapp.com" +
            "/api_tipos_productos?user_hash=dc243fdf1a24cbced74db81708b30788&action=get&";

    // Variables probando ...
    String info_recomendaciones [][];
    String nom_prod2 = ""; // Variable para almacenar el nombre de un tipo de producto


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Asignación de vista (layout) xml y componentes
        setContentView(R.layout.activity__productos_tienda);
        tv_nombre_tienda = findViewById(R.id.tv_nombre_tienda);// Línea para textview de prueba

        recibirNombreTienda();

        StringBuilder sb = new StringBuilder();
        sb.append(url_consulta);
        sb.append("id_tienda="+id_relacionado);
        Log.e("URL 2: ",sb.toString());
        webServiceRest(sb.toString());

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        lv_lista_tienda = findViewById(R.id.lv_lista_tienda);
        //adapter = new ArrayAdapter(this, R.layout.recomendacion_item);
        //lv_lista_tienda.setAdapter(adapter);
        lv_lista_tienda.setAdapter(new Adaptador_ProductosTienda(this, info_recomendaciones)); // Probando...


        lv_lista_tienda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.e("ITEM", lv_lista_tienda.getItemAtPosition(position).toString());
                //String datos_recomendacion[] =
                //        lv_lista_tienda.getItemAtPosition(position).toString().split(">");
                //String id_rec = datos_recomendacion[0];
                String id_rec = info_recomendaciones[position][3];
                Log.e("ID_RECOMENDACION",id_rec);
                Intent i = new Intent(Activity_ProductosTienda.this, Activity_DetalleRecomendacion.class);
                i.putExtra(ID_RECOMENDACION,id_rec);
                startActivity(i);
            }
        });
    }


    /**
     * Método para recibir los criterios o parámetros de búsqueda, provenientes del Activity_Buscar.
     */
    private void recibirNombreTienda() {
        Bundle link_tienda = getIntent().getExtras();
        String nombre_recibido = link_tienda.getString("parametro2tienda");
        Log.v("  Nombre de Tienda", nombre_recibido);
        tv_nombre_tienda.setText("Comercio '" + nombre_recibido + "'");

        StringBuilder sb2 = new StringBuilder();
        sb2.append(url_productosxtienda);
        sb2.append("nombre_tienda="+nombre_recibido.replaceAll(" ","%20"));
        Log.e("  URL 1 obtenida: ",sb2.toString());
        consultaIdTienda(sb2.toString());
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
            Log.e("Error 124",e.getMessage());
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
            Log.e("Error 125",e.getMessage());
        }
        info_recomendaciones = new String[(int)jsonArray.length()][5]; // Inicializa un arreglo dinámico bidimensional para los resultados de la consulta.
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

                //info_recomendaciones [i][2] = nom_prod; // Asigna la variable obtenida de la consulta anterior (parseInformation2).
                info_recomendaciones [i][0] = id_producto;
                info_recomendaciones [i][1] = "$"+precio;
                info_recomendaciones [i][2] = descripcion;
                info_recomendaciones [i][3] = id_recomendacion;

                StringBuilder sb_3 = new StringBuilder();
                sb_3.append(url_cons_tipoprod);
                sb_3.append("id_producto="+id_producto);
                Log.e("  URL 03 obtenida: ",sb_3.toString());
                consultaNombreProducto(sb_3.toString());

                info_recomendaciones [i][4] = nom_prod2;

            }catch (JSONException e){
                Log.e("Error 126",e.getMessage());
            }
        }
    }


    /**
     * Método para obtener el id de l comercio, en base a su nombre.
     * @param requestURL
     */
    private void consultaIdTienda(String requestURL){
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
            Log.e("Error 121",e.getMessage());
        }
    }

    private void parseInformation2(String jsonResult){
        JSONArray jsonArray = null;
        String id_tienda;
        String nombre_tienda;
        String nom_acceso_tienda;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 122",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_tienda = jsonObject.getString("id_tienda");
                nombre_tienda = jsonObject.getString("nombre_tienda");
                nom_acceso_tienda = jsonObject.getString("nom_acceso_tienda");

                //adapter.add(id_producto + " " + nombre_producto);
                id_relacionado = id_tienda;

            }catch (JSONException e){
                Log.e("Error 123",e.getMessage());
            }
        }
    }


    /**
     * Método para obtener el nombre de un producto en base a su id (para mostrar en  interfaz)
     * @param requestURL
     */
    private void consultaNombreProducto(String requestURL){
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
            parseInformation3(webServiceResult);
        }catch(Exception e){
            Log.e("Error 103",e.getMessage());
        }
    }

    private void parseInformation3(String jsonResult){
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

                nom_prod2 = nombre_producto;

            }catch (JSONException e){
                Log.e("Error 105",e.getMessage());
            }
        }
    }



} // Cierre de la clase principal.
