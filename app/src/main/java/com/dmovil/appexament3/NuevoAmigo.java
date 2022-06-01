package com.dmovil.appexament3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dmovil.appexament3.entidades.Amigo;
import com.dmovil.appexament3.utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NuevoAmigo extends AppCompatActivity {

    //Declaracion de variables, para los EditText, el boton flotante, la base de datos
    // y el objeto amigo que se envía desde la actividad PerfilAmigo
    Amigo amigo;
    BDAmigo conn;
    EditText nombre, telefono, email, direccion;
    FloatingActionButton fab;
    private int tipoOperacion;

    //Crea la actividad he inicializa las variables de intancia, determina cuando obtener
    // el objeto Bundle de la actividad PerfilAmigo y el metodo de escucha del boton flotante.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_amigo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        conn = new BDAmigo(this, "BDAmigos", null, 1);

        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        direccion = findViewById(R.id.direccion);
        fab = findViewById(R.id.fab);

        final int REGISTRAR = 0;
        final int ACTUALIZAR = 1;

        tipoOperacion = REGISTRAR;

        //amigo = (Amigo)getIntent().getSerializableExtra("amigo");
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            amigo = (Amigo) parametros.getSerializable("amigo");
            nombre.setText(amigo.getNombre());
            telefono.setText(amigo.getTelefono());
            email.setText(amigo.getEmail());
            direccion.setText(amigo.getDireccion());

            tipoOperacion = ACTUALIZAR;
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emptyCampos()){
                    if (tipoOperacion == REGISTRAR){
                        registrar();
                    } else {
                        actualizar();
                    }
                }else {
                    //Si faltan campos por llenar, se envía un mensaje indicandolo.
                    Toast.makeText(NuevoAmigo.this, "Hay información por rellenar",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Regresa a la actividad anterior
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    //Registra el Amigo en la base de datos
    public void registrar(){
        SQLiteDatabase db = conn.getReadableDatabase();

        if (!existNombre(nombre.getText().toString())){

            ContentValues altas = new ContentValues();

            altas.put(Utilidades.CAMPO_NOMBRE, nombre.getText().toString());
            altas.put(Utilidades.CAMPO_TELEFONO, telefono.getText().toString());
            altas.put(Utilidades.CAMPO_EMAIL, email.getText().toString());
            altas.put(Utilidades.CAMPO_DIRECCION, direccion.getText().toString());

            db.insert(Utilidades.TABLA_AMIGO, null, altas);
            Toast.makeText(this, "Registro exitoso...!", Toast.LENGTH_SHORT).show();
            db.close();
            regresarALista();
        } else {
            //Si el amigo ya existe se envía un mensaje indicandolo
            Toast.makeText(this, "El amigo ya existe!", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    //Actualiza el registro si proviene de la activdad PerfilAmigo
    public void actualizar(){
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parametro = {amigo.getIdAmigo()};

        ContentValues altas = new ContentValues();

        altas.put(Utilidades.CAMPO_NOMBRE, nombre.getText().toString());
        altas.put(Utilidades.CAMPO_TELEFONO, telefono.getText().toString());
        altas.put(Utilidades.CAMPO_EMAIL, email.getText().toString());
        altas.put(Utilidades.CAMPO_DIRECCION, direccion.getText().toString());

        db.update(Utilidades.TABLA_AMIGO, altas, Utilidades.CAMPO_ID + " =?", parametro);

        Toast.makeText(this, "Registro actualizado...!", Toast.LENGTH_SHORT).show();
        db.close();
        regresarALista();
    }

    //Regresa a la actividad principal
    public void regresarALista(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //Valida que no existan campos vacíon
    public boolean emptyCampos(){
        //Evalúa si todos los campos están vacíos
        //Si los campos no están vacíos devuelve false
        //Si los campos están vacíos devuelve true
        return nombre.getText().toString().trim().equalsIgnoreCase("") ||
                telefono.getText().toString().trim().equalsIgnoreCase("") ||
                email.getText().toString().trim().equalsIgnoreCase("") ||
                direccion.getText().toString().trim().equalsIgnoreCase("");
    }
    //Determina si el nombre existe en la base de datos
    public boolean existNombre(String nombre){
        //Este método verifica si el nombre que se le pasa como parámetro existe en la base de datos
        //Se crea un objeto de SQLiteDatabase para leer la base de datos.
        SQLiteDatabase dbDatos = conn.getReadableDatabase();

        //Se crea un objeto de Cursor que obtiene la consulta de si existe el nombre ingresado como parámetro
        Cursor cursor = dbDatos.query(Utilidades.TABLA_AMIGO, new String[]{Utilidades.CAMPO_NOMBRE},
                Utilidades.CAMPO_NOMBRE + "= ?", new String[]{nombre}, null, null, null);

        //Si el número de registros devueltos es mayor a cero indica que el registro ya existe en la base de datos,
        // así que el método devuelve true, en caso de que la consulta no arroje ningun registro el metodo devolvera false.
        return (cursor.getCount() > 0);
    }
}