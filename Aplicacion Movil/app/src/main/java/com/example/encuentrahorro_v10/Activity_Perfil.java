package com.example.encuentrahorro_v10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_Perfil extends AppCompatActivity {
    private CircleImageView circleImageView;
    private TextView txtName,txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__perfil);

        recibirdatos();
    }

    private void recibirdatos() {
        Bundle extras = getIntent().getExtras();
        String nombre = extras.getString("nombre_usuario");
        String correo = extras.getString("email_usuario");
        String imagen = extras.getString("image_usuario");

        txtName = findViewById(R.id.profile_name);
        txtEmail = findViewById(R.id.profile_email);
        circleImageView = findViewById(R.id.profile_pic);
         //los datos son agregados a las cajas de texto
         txtEmail.setText(correo);
         txtName.setText(nombre);
         Glide.with(Activity_Perfil.this).load(imagen).into(circleImageView);
    }

}
