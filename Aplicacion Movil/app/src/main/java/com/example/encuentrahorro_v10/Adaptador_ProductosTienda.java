package com.example.encuentrahorro_v10;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adaptador_ProductosTienda extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context contexto;
    String [][] info_recomendaciones;


    public Adaptador_ProductosTienda(Context contexto, String [][] info_recomendaciones) {
        this.contexto = contexto;
        this.info_recomendaciones = info_recomendaciones;

        inflater = (LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View vista = null;
        try {
            vista = inflater.inflate(R.layout.producto_tienda_item, null);

            TextView nombre_producto = (TextView) vista.findViewById(R.id.tv_nombreproducto3);
            TextView precio = (TextView) vista.findViewById(R.id.tv_precio3);
            TextView descripcion = (TextView) vista.findViewById(R.id.tv_descripcion3);

            nombre_producto.setText(info_recomendaciones[i][4]);
            precio.setText(info_recomendaciones[i][1]);
            descripcion.setText(info_recomendaciones[i][2]);


            Log.e("NOMBRE_PRODUCTO: ",info_recomendaciones[i][4]);
            Log.e("PRECIO: ",info_recomendaciones[i][1]);
            Log.e("DESCRIPCION: ",info_recomendaciones[i][2]);

        } catch (Exception e) {
            Log.e("Error 321", e.getMessage());
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
