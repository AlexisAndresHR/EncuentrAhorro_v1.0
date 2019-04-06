package com.example.encuentrahorro_v10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent; // Import de prueba para cambio de interfaz (temporal)...
import android.view.View; // Import de prueba para cambio de interfaz (temporal)...
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class Activity_Buscar extends AppCompatActivity {

    // Variables de inicialización de componentes
    EditText et_palabras_clave;
    Spinner sp_estado;
    Spinner sp_ciudad;
    RadioButton rb_recientes;
    RadioButton rb_populares;

    String palabra_clave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__buscar);
        // Inicialización de componentes de el layout 'buscar'.
        et_palabras_clave = findViewById(R.id.et_palabraclave);
        sp_estado = findViewById(R.id.sp_estado);
        sp_ciudad = findViewById(R.id.sp_ciudad);
        rb_recientes = findViewById(R.id.rb_recientes);
        rb_populares = findViewById(R.id.rb_populares);
    }

    /**
     * Método para la acción-botón de Búsqueda.
     * @param view
     */
    public void buscarRecomendaciones1(View view) {
        Intent busqueda = new Intent(this, Activity_ResultadoBusqueda.class);
        palabra_clave = String.valueOf(et_palabras_clave.getText());
        busqueda.putExtra("parametro1p", palabra_clave);
        startActivity(busqueda);
    }


} // Cierre de la clase principal.
