package com.example.encuentrahorro_v10;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adaptador_ResultadoBusqueda extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context contexto;
    String [][] info_recomendaciones;


    public Adaptador_ResultadoBusqueda(Context contexto, String [][] info_recomendaciones) {
        this.contexto = contexto;
        this.info_recomendaciones = info_recomendaciones;

        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View vista = null;
        try {
            vista = inflater.inflate(R.layout.recomendacion_item, null);

            TextView nombre_usuario = (TextView) vista.findViewById(R.id.tv_nombreusuario2);
            TextView fecha = (TextView) vista.findViewById(R.id.tv_fecha2);
            TextView nombre_producto = (TextView) vista.findViewById(R.id.tv_nombreproducto2);
            TextView precio = (TextView) vista.findViewById(R.id.tv_precio2);
            TextView descripcion = (TextView) vista.findViewById(R.id.tv_descripcion2);

            nombre_usuario.setText(info_recomendaciones[i][0]);
            fecha.setText(info_recomendaciones[i][1]);
            nombre_producto.setText(info_recomendaciones[i][2]);
            precio.setText(info_recomendaciones[i][3]);
            descripcion.setText(info_recomendaciones[i][4]);


            Log.e("NOMBRE_USUARIO: ",info_recomendaciones[i][0]);
            Log.e("FECHA: ",info_recomendaciones[i][1]);
            Log.e("NOMBRE_PRODUCTO: ",info_recomendaciones[i][2]);
            Log.e("PRECIO: ",info_recomendaciones[i][3]);
            Log.e("DESCRIPCION: ",info_recomendaciones[i][4]);

        } catch (Exception e) {
            Log.e("Error 300", e.getMessage());
        }
        return vista;
    }

    @Override
    public int getCount() {
        return info_recomendaciones.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
