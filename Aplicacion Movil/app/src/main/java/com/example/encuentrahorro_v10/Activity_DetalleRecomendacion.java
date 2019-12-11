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
import android.widget.EditText;
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
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Activity_DetalleRecomendacion extends AppCompatActivity implements OnMapReadyCallback {

    TextView tv_producto;
    TextView tv_precio;
    TextView tv_descripcion;
    TextView tv_fecha;
    TextView tv_nombreusuario;
    TextView tv_nummegusta;
    TextView tv_numcomentarios;
    EditText edt_comentario;
    Button btn_vertienda;

    private String webservice_url = "http://webapp-encuentrahorro.herokuapp.com" +
            "./api_recomendaciones?user_hash=dc243fdf1a24cbced74db81708b30788&action=get&id_recomendacion=";
    // Variables para consultar el nombre del producto de la Recomendación.
    private String consulta_nombreproducto = "http://webapp-encuentrahorro.herokuapp.com" +
            "./api_tipos_productos?user_hash=dc243fdf1a24cbced74db81708b30788&action=get&id_producto=";
    private String nom_prod_obtenido;

    // Variables de prueba para almacenar latitud y longitud
    private double latitud = 0.0;
    private double longitud = 0.0;

    // Variables para las funciones de comentarios de la Recomendación
    private ListView lv_comentarios;
    private ArrayAdapter adapter_com;
    private String url_comentarios = "http://webapp-encuentrahorro.herokuapp.com" +
            "/api_comentarios?user_hash=dc243fdf1a24cbced74db81708b30788&action=get&id_recomendacion=";

    String nom_tienda_2 = ""; // Variable para almacenar el nombre del comercio (después de la consulta de información)
    //String id_relacionado_tienda = ""; // Variable para transferir de una interfaz a otra el ID del comercio


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
        edt_comentario = findViewById(R.id.edt_comentario);
        btn_vertienda = findViewById(R.id.btn_vertienda);

        //Objeto tipo Intent para recuperar el parametro enviado
        Intent intent = getIntent();
        //Se almacena el id_cliente enviado
        String id_recomendacion = intent.getStringExtra(Activity_ResultadoBusqueda.ID_RECOMENDACION);
        //Se cocnatena la url con el id_cliente para obtener los datos el cliente
        webservice_url+=id_recomendacion;
        webServiceRest(webservice_url);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_ubicacion);
        mapFragment.getMapAsync(this);

        // Código para llamada a métodos de comentarios
        lv_comentarios = findViewById(R.id.lv_comentarios);
        adapter_com = new ArrayAdapter(this, R.layout.comentario_item); // Modificar posteriormente...
        lv_comentarios.setAdapter(adapter_com);
        url_comentarios+=id_recomendacion;
        consultaComentariosRest(url_comentarios);
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
                id_tienda = jsonObject.getString("id_tienda");
                rec_confiable = jsonObject.getString("rec_confiable");
                rec_falsa = jsonObject.getString("rec_falsa");
                num_comentarios = jsonObject.getString("num_comentarios");
                promedio_evaluaciones = jsonObject.getString("promedio_evaluaciones");
                recomendacion_activa = jsonObject.getString("recomendacion_activa");

                // Muestreo de los datos de Recomendacion en la vista correspondiente
                consulta_nombreproducto+=id_producto; // Se completa la url de consulta al webservice con el id obtenido.
                consultaNombreProducto(consulta_nombreproducto); // Llamada al método para consultar el nombre del producto.
                tv_producto.setText(nom_prod_obtenido);
                tv_precio.setText("$"+precio);
                tv_descripcion.setText(descripcion);
                tv_fecha.setText(fecha.substring(0,10));
                tv_nummegusta.setText(rec_confiable); // Modificar posteriormente ...
                tv_numcomentarios.setText(num_comentarios);
                if (nombre_usuario != "null")
                    tv_nombreusuario.setText(nombre_usuario);
                else {
                    obtenerNombreTienda(Integer.parseInt(id_tienda));
                    tv_nombreusuario.setText(nom_tienda_2);
                    // Código para habilitar botón y crear enlace a interfaz-catalogo de comercios
                    btn_vertienda.setEnabled(true);
                    btn_vertienda.setTextColor(R.color.colorAccent);
                    //btn_vertienda.setBackgroundColor(R.color.colorAccent);
                }

//                URL newurl = new URL(images_url+imagen);
//                Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
//                iv_imagen.setImageBitmap(mIcon_val);

                // Actualiza las variables de latitud y longitud para mostrar en el mapa.
                latitud = Double.parseDouble(latitud_ubi);
                longitud = Double.parseDouble(longitud_ubi);

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

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(latitud, longitud);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Lugar de la oferta"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


// Métodos para funciones de Comentarios en cada Recomendación.
    private void consultaComentariosRest(String requestURL){
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
            parseInformationComentarios(webServiceResult);
        }catch(Exception e){
            Log.e("Error 200",e.getMessage());
        }
    }

    private void parseInformationComentarios(String jsonResult){
        JSONArray jsonArray = null;
        String id_comentario;
        String id_recomendacion;
        String nombre_usuario;
        String contenido;
        String fecha_comentario;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 201",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_comentario = jsonObject.getString("id_comentario");
                id_recomendacion = jsonObject.getString("id_recomendacion");
                contenido = jsonObject.getString("contenido");
                nombre_usuario = jsonObject.getString("nombre_usuario");
                fecha_comentario = jsonObject.getString("fecha_comentario");

                adapter_com.add(nombre_usuario + " -> " + contenido + "  " + fecha_comentario);
            }catch (JSONException e){
                Log.e("Error 202",e.getMessage());
            }
        }
    }

    /**
     * Método para insertar un nuevo comentario en la recomendación.
     */
    public void insertarComentario(View view) {
        String url_insert_com = "http://webapp-encuentrahorro.herokuapp.com" +
                "/api_comentarios?user_hash=dc243fdf1a24cbced74db81708b30788&action=put&";
        StringBuilder sb2 = new StringBuilder();
        Intent intent2 = getIntent();

        sb2.append(url_insert_com);
        sb2.append("id_recomendacion="+intent2.getStringExtra(Activity_ResultadoBusqueda.ID_RECOMENDACION));
        sb2.append("&");
        sb2.append("nombre_usuario="+tv_nombreusuario.getText());
        sb2.append("&");
        sb2.append("contenido="+edt_comentario.getText());

        webServicePut(sb2.toString());
        Log.e("URL Comentario: ",sb2.toString());
    }

    private void webServicePut(String requestURL){
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
            parseInformationPut(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }

    private void parseInformationPut(String jsonResult){
        JSONArray jsonArray = null;
        String id_comentario;
        String id_recomendacion;
        String nombre_usuario;
        String contenido;
        String fecha_comentario;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                id_comentario = jsonObject.getString("id_comentario");
                id_recomendacion = jsonObject.getString("id_recomendacion");
                contenido = jsonObject.getString("contenido");
                nombre_usuario = jsonObject.getString("nombre_usuario");
                fecha_comentario = jsonObject.getString("fecha_comentario");
                Log.e("ID_COMENTARIO: ",id_comentario);
                Log.e("ID_RECOMENDACION: ",id_recomendacion);
                Log.e("CONTENIDO: ",contenido);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }

// Métodos para realizar una consulta al web service sobre el nombre de un producto, en base a su id.
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
            parseInfoNombreProducto(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }

    private void parseInfoNombreProducto(String jsonResult){
        JSONArray jsonArray = null;
        String id_producto;
        String imagen_producto;
        String nombre_producto;
        String id_categoria;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // Se obtienen los datos del producto, del webservice.
                id_categoria = jsonObject.getString("id_categoria");
                imagen_producto = jsonObject.getString("imagen_producto");
                nombre_producto = jsonObject.getString("nombre_producto");
                id_producto = jsonObject.getString("id_producto");
                nom_prod_obtenido = nombre_producto; // Agrega el nombre de producto_obtenido a la variable inicial.
            }
            catch (JSONException e){
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



    /**
     * Método para consultar información del comercio (tienda) y colocar su nombre en los resultados de búsqueda.
     * @param id
     */
    private void obtenerNombreTienda (int id) {
        String url_consulta01 = "http://webapp-encuentrahorro.herokuapp.com" +
                "/api_tiendas?user_hash=dc243fdf1a24cbced74db81708b30788&action=get&";
        StringBuilder sb_3 = new StringBuilder();
        sb_3.append(url_consulta01);
        sb_3.append("id_tienda="+id);
        Log.e("  URL 69 obtenida: ",sb_3.toString());
        consultaInfoTienda(sb_3.toString());
    }


    private void consultaInfoTienda(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult_2="";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult_2 += line;
            }
            bufferedReader.close();
            parseInformation3(webServiceResult_2);
        }catch(Exception e){
            Log.e("Error 131",e.getMessage());
        }
    }

    private void parseInformation3(String jsonResult){
        JSONArray jsonArray = null;
        String id_tienda;
        String nombre_tienda;
        String nom_acceso_tienda;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 132",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_tienda = jsonObject.getString("id_tienda");
                nombre_tienda = jsonObject.getString("nombre_tienda");
                nom_acceso_tienda = jsonObject.getString("nom_acceso_tienda");

                //adapter.add(id_producto + " " + nombre_producto);
                nom_tienda_2 = nombre_tienda;
                //id_relacionado_tienda = id_tienda;

            }catch (JSONException e){
                Log.e("Error 133",e.getMessage());
            }
        }
    }


    public void verTienda(View view) {
        try {
            Intent ir_a_lista = new Intent(this, Activity_ProductosTienda.class);
            //palabra_clave = String.valueOf(et_palabras_clave.getText());
            ir_a_lista.putExtra("parametro2tienda", nom_tienda_2);
            startActivity(ir_a_lista);
        }
        catch (Exception e) {
            Log.e("Error 437",e.getMessage());
        }
    }


} // Cierre de la clase principal (Activity_DetalleRecomendacion).
