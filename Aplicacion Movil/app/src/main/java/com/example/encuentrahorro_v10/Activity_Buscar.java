package com.example.encuentrahorro_v10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent; // Import de prueba para cambio de interfaz (temporal)...
import android.view.View; // Import de prueba para cambio de interfaz (temporal)...

public class Activity_Buscar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__buscar);
    }

    // Código para prueba de cambio de interfaz (temporal)...
    public void cambioInterfaz2(View view) {
        Intent cambio2 = new Intent(this, LoginActivity.class);
        startActivity(cambio2);
    }

}
