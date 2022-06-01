package com.dmovil.appexament3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmovil.appexament3.entidades.Amigo;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    //Se crea dos atributos para la clase Adaptador
    Context context;
    ArrayList<Amigo> listItem;

    //Se crea constructor parametrizado
    public Adaptador(Context context, ArrayList<Amigo> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    //Se extiende los metodos de la clase padre BaseAdapter
    @Override
    public int getCount() {
        return listItem.size();
    }

    //retorna un elemento del ArrayList con el objeto en la posicion que se le indica como parametro
    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //Crea un item por cada elemento que contiene el ArrayList
    //Y establece su datos en el item.xml
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Amigo item = (Amigo) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item, null);

        ImageView iconPerfil = convertView.findViewById(R.id.icon_perfil);
        TextView txtNombre = convertView.findViewById(R.id.txt_nombre);

        iconPerfil.setImageResource(item.getIcono());
        txtNombre.setText(item.getNombre());

        return convertView;
    }
}
