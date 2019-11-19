
package com.example.encuentrahorro_v10;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A login screen that offers login via email/password.
 */
public class Activity_LoginTienda extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private CircleImageView circleImageView;
    private TextView txtName,txtEmail;

    private String url_insertar_usuario = "http://webapp-encuentrahorro.herokuapp.com/api_usuarios?user_hash=dc243fdf1a24cbced74db81708b30788&action=put&";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tienda);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        //componentes para mostrar los datos del perfil
        txtName = findViewById(R.id.profile_name);
        txtEmail = findViewById(R.id.profile_email);
        circleImageView = findViewById(R.id.profile_pic);

        //el boton de login lee los permisos para obtener los datos del usuario
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        checkLoginStatus();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),R.string.cancelarFacebook, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),R.string.errorFacebook, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainScreen() {
        Intent vista = new Intent(this, Activity_Inicio.class);
        getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(vista);
        Activity_LoginTienda.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Metodo que permite la devoluvion de llamada, para leer los permisos otorgados
     *
     */

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken==null)
            {
                txtName.setText("");
                txtEmail.setText("");
                circleImageView.setImageResource(0);
                Toast.makeText(Activity_LoginTienda.this,"Sesión cerrada",Toast.LENGTH_LONG).show();
            }
            else
                loadUserProfile(currentAccessToken);
        }
    };

    /**
     * Obetner los datos del usuario, obtiene el nobre del usuario, id del usuario correo y su foto de perfil y los almacena en las variables
     * primer nombre, segundo nombre, email, id, e image_url
     * @param newAccessToken
     */
    private void loadUserProfile(AccessToken newAccessToken){
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try{
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    String image_url = "https://graph.facebook.com/"+id+"/picture?type=normal";
                    String nombre = (first_name+last_name.substring(0,3)+id.substring(0,4));
                    String name = (first_name+" "+last_name);

                    /**
                     * se llama al metodo que mostrara los datos obtenidos del usuario
                     */
                    recibir_datos(name,email,image_url);

                    //ver que las variables almacenan esos datos0
                    Log.d("Nombre",nombre);
                    Log.d("email",email);
                    Log.d("Id",id);
                    Log.d("Imagen",image_url);

                    //Guardar los datos en la BD

                    StringBuilder sb = new StringBuilder();
                    sb.append(url_insertar_usuario);
                    sb.append("nombre_usuario="+nombre);
                    sb.append("&");
                    sb.append("email_usuario="+email);
                    sb.append("&");
                    sb.append("nombre="+first_name);
                    sb.append("&");
                    sb.append("apellido_pat="+last_name);
                    sb.append("&promedio_evaluaciones=0&nivel_usuario=Promedio");

                    webServicePut(sb.toString());
                    Log.e("URL",sb.toString());

                    /**
                    //los datos son agregados a las cajas de texto
                    txtEmail.setText(email);
                    txtName.setText(first_name +" "+last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();
                     **/

                    //Glide.with(LoginActivity.this).load(image_url).into(circleImageView);


                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    /**
     * Recibe datos de Facebook para mostrarlos en diferentes Activitys
     * Perfil y Barra de Menu
     * @param name nombre del usuario
     * @param email email del usuario
     * @param image foto del perfil
     */
    private void recibir_datos (String name,String email,String image){
        Intent inicio = new Intent(this,Activity_Inicio.class);
        inicio.putExtra("nombre_usuario",name);
        inicio.putExtra("email_usuario",email);
        inicio.putExtra("image_usuario",image);
        startActivity(inicio);
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
    private void checkLoginStatus()
    {
        if(AccessToken.getCurrentAccessToken()!=null)
        {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    String nombre = "Alexis Andrés";
    String email = "Alexis@gmail.com";
    String image = "https://moodle.org/pluginfile.php/2678856/user/icon/moodleorgcleaned_moodleorg/f1?rev=1838857";
    // Código para prueba de cambio de interfaz (temporal)...
    public void inicio(View view) {
        Intent inicio = new Intent(this,Activity_Inicio.class);
        inicio.putExtra("nombre_usuario",nombre);
        inicio.putExtra("email_usuario",email);
        inicio.putExtra("image_usuario",image);
        startActivity(inicio);
    }
}

