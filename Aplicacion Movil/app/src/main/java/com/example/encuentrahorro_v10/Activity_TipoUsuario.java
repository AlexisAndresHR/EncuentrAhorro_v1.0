package com.example.encuentrahorro_v10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity_TipoUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tipo_usuario);

    }
    public void interfazPerfil(View view) {
        Intent cambio = new Intent(this, LoginActivity.class);
        startActivity(cambio);
    }
}
