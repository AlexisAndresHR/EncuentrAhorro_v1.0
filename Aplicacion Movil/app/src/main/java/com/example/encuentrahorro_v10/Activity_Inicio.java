package com.example.encuentrahorro_v10;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

public class Activity_Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/**
        // verificacion de session iniciada con facebook
        if (AccessToken.getCurrentAccessToken() == null){
            goLoginScreen(); // si es nulo significa que no hay una session iniciada
        }
**/

/**
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });**/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    /**
     * Metodo para verificar si se tiene una sesion iniciada con Facebook
     * si no regresara al login Activity
     */
    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_buscar) {
            // Envía a la interfaz de BUSQUEDA
            Intent cambio = new Intent(getApplicationContext(),Activity_Buscar.class);
            startActivity(cambio);
        }
        else if (id == R.id.nav_favoritos) {
            // Envía a la interfaz de FAVORTIOS
            //Intent cambio = new Intent(getApplicationContext(),Activity_Buscar.class);
            //startActivity(cambio);
        }
        else if (id == R.id.nav_recomendacion) {
            // Envía a la interfaz de RECOMENDACION
            Intent cambio = new Intent(getApplicationContext(),Activity_PublicarRecomendacion.class);
            startActivity(cambio);
        }
        else if (id == R.id.nav_cambiocontrasena) {
            // Envía a la interfaz de RECOMENDACION
            Intent cambio = new Intent(getApplicationContext(),Activity_CambiarContrasenia.class);
            startActivity(cambio);
        }
        else if (id == R.id.nav_send) {
            LoginManager.getInstance().logOut();
            goLoginScreen();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Código para prueba de cambio de interfaz  a perfil de usuario...
    public void interfazPerfil(View view) {
        Intent cambio = new Intent(this, Activity_Perfil.class);
        startActivity(cambio);
    }
}
