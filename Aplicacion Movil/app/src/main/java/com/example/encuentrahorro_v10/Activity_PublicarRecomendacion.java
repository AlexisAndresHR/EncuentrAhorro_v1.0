package com.example.encuentrahorro_v10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent; // Import de prueba para cambio de interfaz (temporal)...
import android.view.View; // Import de prueba para cambio de interfaz (temporal)...

// Imports para el envio de informacion mediante JSON
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.ArrayList;

import android.widget.EditText;
import android.widget.Spinner;

// Imports para las funciones de ubicación en la interfaz
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
// Imports para el mapa con marca (Google Maps)
// Imports para la obtencion de datos de ubicacion del usuario
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class Activity_PublicarRecomendacion extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    EditText et_id_categoria;
    EditText et_id_producto;

    Spinner sp_categoria;
    Spinner sp_tipoproducto;
    EditText et_precio;
    EditText et_descripcion;
    Spinner sp_duracion;
    ArrayList<String> categorias;
    ArrayList<String> productos;
    ArrayList<String> duraciones;

    EditText et_latitud;
    EditText et_longitud;

    private String webservice_url = "http://webapp-encuentrahorro.herokuapp.com" +
            "./api_recomendaciones?user_hash=dc243fdf1a24cbced74db81708b30788&action=put&";
    // Variable con dirección URL para consultar las Categorias.
    private String webservice_url_2 = "http://webapp-encuentrahorro.herokuapp.com" +
            "./api_categorias_productos?user_hash=dc243fdf1a24cbced74db81708b30788&action=get";
    // Variable con dirección URL para consultar los Tipos de Productos.
    private String webservice_url_3 = "http://webapp-encuentrahorro.herokuapp.com" +
            "./api_tipos_productos?user_hash=dc243fdf1a24cbced74db81708b30788&action=get";


    // Variables para las funciones de ubicacion del usuario
    private static final String TAG = Activity_PublicarRecomendacion.class.getName();

    private TextView mTvLatitud;
    private TextView mTvLongitud;

    private static final int RC_LOCATION_PERMISION= 100;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static int INTERVAL = 10000;
    private static int FAST_INTERVAL = 5000;

    private boolean mRequestingLocationUpdates = false;
    private Location mCurrentLocation;

    // Variables de prueba para almacenar latitud y longitud
    private double latitud_ubi = 0.0;
    private double longitud_ubi = 0.0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__publicar_recomendacion);
        // Inicialización de componentes de la vista
        sp_categoria = findViewById(R.id.sp_categoria);
        sp_tipoproducto = findViewById(R.id.sp_tipoproducto);
        et_precio = findViewById(R.id.et_precio);
        et_descripcion = findViewById(R.id.et_descripcion);
        sp_duracion = findViewById(R.id.sp_duracion);


        //Conectar el UI con la Actividad
        mTvLatitud = (TextView) findViewById(R.id.latitud);
        mTvLongitud= (TextView) findViewById(R.id.longitud);

        //Solicitar permisos si es necesario (Android 6.0+)
        requestPermissionIfNeedIt();
        //Inicializar el GoogleAPIClient y armar la Petición de Ubicación
        initGoogleAPIClient();

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_ubicacion);
        mapFragment.getMapAsync(this);


        // Llamada al método para obtener los registros de Categorias
        webServiceRest2Categorias(webservice_url_2);
        ArrayAdapter<CharSequence> adaptador_categoria = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias);
        if (categorias != null)
            sp_categoria.setAdapter(adaptador_categoria);

        // Llamada al método para obtener los registros de Tipos de Productos
        webServiceRest3TiposProductos(webservice_url_3);
        ArrayAdapter<CharSequence> adaptador_tipo = new ArrayAdapter(this, android.R.layout.simple_spinner_item, productos);
        if (productos != null)
            sp_tipoproducto.setAdapter(adaptador_tipo);

        // Llena el Spinner (Combo Box) con las opciones de duración de recomendación.
        duraciones = new ArrayList<String>();
        duraciones.add("-- Seleccionar --");
        duraciones.add("1 día");
        duraciones.add("3 días");
        duraciones.add("7 dias");
        duraciones.add("14 dias");
        ArrayAdapter<CharSequence> adaptador_duracion = new ArrayAdapter(this, android.R.layout.simple_spinner_item, duraciones);
        if (duraciones != null)
            sp_duracion.setAdapter(adaptador_duracion);
    }


    // Código para prueba de cambio de interfaz (temporal)...
//    public void cambioInterfaz2(View view) {
//        Intent cambio2 = new Intent(this, Activity_Buscar.class);
//        startActivity(cambio2);
//    }


// Métodos para la consulta de valores y llenado de Spiners (combobox's)
    private void webServiceRest2Categorias(String requestURL){
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
        String id_categoria;
        String nombre_categoria;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        categorias = new ArrayList<String>();
        categorias.add("-- Seleccionar --");
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos de categorias del webservice
                id_categoria = jsonObject.getString("id_categoria");
                nombre_categoria = jsonObject.getString("nombre_categoria");
                categorias.add(id_categoria + " " + nombre_categoria); // Agrega la categoria a la lista
            }
            catch (JSONException e){
                Log.e("Error 103",e.getMessage());
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
    private void webServiceRest3TiposProductos(String requestURL){
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
            Log.e("Error 104",e.getMessage());
        }
    }

    private void parseInformation3(String jsonResult){
        JSONArray jsonArray = null;
        String id_producto;
        String nombre_producto;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        productos = new ArrayList<String>();
        productos.add("-- Seleccionar --");
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos de tipos de productos del webservice
                id_producto = jsonObject.getString("id_producto");
                nombre_producto = jsonObject.getString("nombre_producto");
                productos.add(id_producto + " " + nombre_producto); // Agrega el tipo de producto a la lista
            }
            catch (JSONException e){
                Log.e("Error 104",e.getMessage());
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



// Métodos para el envío de información (inserción de registro) al webservice (Recomendacion en JSON).
    public void insertarRecomendacion(View view){

        if (sp_categoria.getSelectedItem().toString() != "-- Seleccionar --") {
            if (sp_tipoproducto.getSelectedItem().toString() != "-- Seleccionar --") {
                if (!et_precio.getText().toString().isEmpty()) {
                    if (!et_descripcion.getText().toString().isEmpty()) {
                        if (sp_duracion.getSelectedItem().toString() != "-- Seleccionar --") {
                            StringBuilder sb = new StringBuilder();
                            sb.append(webservice_url);
                            sb.append("descripcion="+et_descripcion.getText());
                            sb.append("&");
                            sb.append("precio="+et_precio.getText());
                            sb.append("&");
                            sb.append("latitud_ubi=" + latitud_ubi);
                            sb.append("&");
                            sb.append("longitud_ubi=" + longitud_ubi);
                            sb.append("&");
                            String duracion_seleccionada = sp_duracion.getSelectedItem().toString();
                            String[] elementos_duracion = duracion_seleccionada.split(" ");
                            String dur = elementos_duracion[0];
                            sb.append("duracion="+ dur);
                            Log.v("  Duración: ", dur);
                            sb.append("&");
                            String categoria_seleccionada = sp_categoria.getSelectedItem().toString();
                            String[] elementos_categoria = categoria_seleccionada.split(" ");
                            Log.v("  Valor separado: ", elementos_categoria[0]);
                            Log.v("  Valor separado: ", elementos_categoria[1]);
                            String cat = elementos_categoria[0];
                            Log.v("  ID Categoría: ", cat);
                            sb.append("id_categoria=" + cat);
                            sb.append("&");
                            String producto_seleccionado = sp_tipoproducto.getSelectedItem().toString();
                            String[] elementos_producto = producto_seleccionado.split(" ");
                            String prd = elementos_producto[0];
                            Log.v("  ID Producto: ", prd);
                            sb.append("id_producto=" + prd);
                            sb.append("&");
                            sb.append("nombre_usuario=AlexisHR");
                            sb.append("&");
                            sb.append("num_megusta=0");
                            sb.append("&");
                            sb.append("num_comentarios=0");
                            sb.append("&");
                            sb.append("promedio_evaluaciones=0.0");
                            sb.append("&");
                            sb.append("recomendacion_activa=1");
                            webServicePut(sb.toString());
                            Log.e("URL",sb.toString());

                            // Envía a la interfaz de inicio una vez insertada la Recomendación en la BD
                            Intent cambio_inicio = new Intent(this, Activity_Inicio.class);
                            startActivity(cambio_inicio);
                        }
                        else {
                            Toast toast = Toast.makeText(this, R.string.toast_duracion, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    else {
                        Toast toast = Toast.makeText(this, R.string.toast_descripcion, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else {
                    Toast toast = Toast.makeText(this, R.string.toast_precio, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                Toast toast = Toast.makeText(this, R.string.toast_tipoproducto, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else {
            Toast toast = Toast.makeText(this, R.string.toast_categoria, Toast.LENGTH_SHORT);
            toast.show();
        }
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
            parseInformation(webServiceResult);
        }catch(Exception e){
            Log.e("Error 100",e.getMessage());
        }
    }

    private void parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String status;
        String description;
        try{
            jsonArray = new JSONArray(jsonResult);
        }catch (JSONException e){
            Log.e("Error 101",e.getMessage());
        }
        for(int i=0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Se obtiene cada uno de los datos cliente del webservice
                status = jsonObject.getString("status");
                description = jsonObject.getString("description");
                Log.e("STATUS",status);
                Log.e("DESCRIPTION",description);
            }catch (JSONException e){
                Log.e("Error 102",e.getMessage());
            }
        }
    }



// Métodos para la obtención de ubicación real del usuario (con Google Maps).
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
        LatLng sydney = new LatLng(latitud_ubi, longitud_ubi);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Lugar de la oferta"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    // Métodos para  las funciones de obtencion de ubicacion del usuario
    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected())
                startLocationUpdates();
            else
                mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initGoogleAPIClient(){
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            //Creamos una peticion de ubicacion con el objeto LocationRequest
            createLocationRequest();
        }
    }
    protected void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FAST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    private void startLocationUpdates(){
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                mRequestingLocationUpdates = true;
            }
        }
    }
    private void stopLocationUpdates() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected() && mRequestingLocationUpdates){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }
    private void requestPermissionIfNeedIt() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RC_LOCATION_PERMISION);
        }
    }
    public void refreshUI(){
        if (mCurrentLocation != null) {
            mTvLatitud.setText(String.valueOf(mCurrentLocation.getLatitude()));
            mTvLongitud.setText(String.valueOf(mCurrentLocation.getLongitude()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_LOCATION_PERMISION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                requestPermissionIfNeedIt();
            }
        }
    }

    /*
     * Implementación del GoogleApiClient.ConnectionCallbacks
     * */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");
        if (!mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "onConnectionSuspended");
        Toast.makeText(this, getString(R.string.app_name), Toast.LENGTH_LONG).show();
    }

    /*
     * Implementación del GoogleApiClient.OnConnectionFailedListener
     * */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");
    }

    /*
     * Implementación del LocationListener
     * */
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        refreshUI();
    }

    /**
     * Método para obtener y reflejar en el mapa la ubicacion del usuario (al hacer click en el boton).
     * @param view
     */
    public void agregaUbicacion (View view) {

        latitud_ubi = mCurrentLocation.getLatitude();
        longitud_ubi = mCurrentLocation.getLongitude();

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_ubicacion);
        mapFragment.getMapAsync(this);
    }



} // Cierre de la clase principal del Activity.
