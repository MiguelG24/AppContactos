package com.dmovil.appexament3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dmovil.appexament3.entidades.Amigo;
import com.dmovil.appexament3.utilidades.Utilidades;

public class PerfilAmigo extends AppCompatActivity {

    //Crea las variables de instancia para la base de datos y los componentes del XML
    Amigo amigo;
    BDAmigo conn;
    EditText nombre, telefono, email, direccion;

    //Inicializa las variables de instancia y settea los datos del amigo enviado
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_amigo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        conn = new BDAmigo(this, "BDAmigos", null, 1);
        amigo = (Amigo)getIntent().getSerializableExtra("amigo");

        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        direccion = findViewById(R.id.direccion);

        nombre.setText(amigo.getNombre());
        telefono.setText(amigo.getTelefono());
        email.setText(amigo.getEmail());
        direccion.setText(amigo.getDireccion());
    }

    //Regresa a la actividad anterior
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    //Envia a la actividad NuevoAmigo el objeto Amigo para editarlo
    public void editar(){
        Intent intent = new Intent(this, NuevoAmigo.class);
        intent.putExtra("amigo", amigo);
        startActivity(intent);
    }

    //Elimina el registro del Amigo que se ha selecionado
    public void eliminar(){
        SQLiteDatabase db = conn.getWritableDatabase();

        String idAmigo = amigo.getIdAmigo();
        String[] parametro = {idAmigo};

        db.delete(Utilidades.TABLA_AMIGO, Utilidades.CAMPO_ID + " = ?", parametro);
        Toast.makeText(this, "Registro eliminado...!", Toast.LENGTH_SHORT).show();

        regresarALista();
    }

    //Regresa a la lista principal
    public void regresarALista(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //Modifica el Action Bar para añadir los botondes de editar y eliminar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perfil_amigo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Metodo de escucha de los botones en el Action Bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                editar();
                return true;
            case R.id.delete:
                eliminar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}