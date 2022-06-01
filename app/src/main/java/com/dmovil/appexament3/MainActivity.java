package com.dmovil.appexament3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.dmovil.appexament3.entidades.Amigo;
import com.dmovil.appexament3.utilidades.Utilidades;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Se declaran las variables de ListView, Adaptador, y el ArrayList que contendrá los objetos
    //de la clase Amigo
    ListView listViewAmigos;
    Adaptador adaptador;
    ArrayList<Amigo> listaAmigos;

    //Se declara la variable para la conexion a la base de datos
    BDAmigo conn;

    //Se declara el boton flotante
    FloatingActionButton nuevoAm, salir;

    //Declaracion de variables para controlar el tipo de consulta que se hará
    private final int LISTA_COMPLETA = 0;
    private final int LISTA_BUSQUEDA = 1;
    private String nombreBusqueda = "";
    private int tipoLista = LISTA_COMPLETA;


    //Crea la actividad he inicializa las variables de instancia
    // y los metodos de escucha de el listView y el boton flotante.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleIntent(getIntent());

        conn = new BDAmigo(this, "BDAmigos", null, 1);
        listViewAmigos = findViewById(R.id.lista1);
        adaptador = new Adaptador(this, getArrayItems());
        listViewAmigos.setAdapter(adaptador);

        listViewAmigos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Amigo amigo =(Amigo)listViewAmigos.getItemAtPosition(position);
                verPerfil(amigo);
            }
        });

        nuevoAm = findViewById(R.id.nuevo);
        nuevoAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoAmigo();
            }
        });

        salir = findViewById(R.id.salir);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    //Metodo para crear un Intent a partir del boton de busqueda en el Action Bar
    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        handleIntent(intent);
    }

    //Metodo de ejecucion del intent cuando se ejecuta la consulta con el boton de buscar
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow

            tipoLista = LISTA_BUSQUEDA;
            nombreBusqueda = query;

            adaptador = new Adaptador(this, getArrayItems());
            listViewAmigos.setAdapter(adaptador);

            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        }
    }

    //Pasa a la actividad de ver el perfil y le pasa como extra un objeto de la clase Amigo
    private void verPerfil(Amigo amigo) {
        Intent intent = new Intent(this, PerfilAmigo.class);
        intent.putExtra("amigo", amigo);
        startActivity(intent);
    }

    //Se carga el ArrayList con los registros de amigos en la tabla y devuelve un objeto ArrayList
    public ArrayList<Amigo> getArrayItems(){

        listaAmigos = new ArrayList<Amigo>();
        Amigo amigo = null;
        int iconAmigo = R.drawable.ic_account;

        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor;

        String[] campos = {Utilidades.CAMPO_NOMBRE, Utilidades.CAMPO_TELEFONO, Utilidades.CAMPO_EMAIL,
                Utilidades.CAMPO_DIRECCION};

        if (tipoLista == LISTA_BUSQUEDA){
            cursor = db.rawQuery(
                    "SELECT * FROM " + Utilidades.TABLA_AMIGO + " WHERE " + Utilidades.CAMPO_NOMBRE + " LIKE '%" + nombreBusqueda + "%'", null );
            //cursor = db.query(Utilidades.TABLA_AMIGO, campos, Utilidades.CAMPO_NOMBRE + " LIKE '%?%'", new String[]{nombreBusqueda}, null, null, null);
        } else {
            cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_AMIGO, null);
        }

        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                amigo = new Amigo();
                amigo.setIcono(iconAmigo);
                amigo.setIdAmigo(String.valueOf(cursor.getInt(0)));
                amigo.setNombre(cursor.getString(1));
                amigo.setTelefono(cursor.getString(2));
                amigo.setEmail(cursor.getString(3));
                amigo.setDireccion(cursor.getString(4));

                listaAmigos.add(amigo);
            }
        }else{
            Toast.makeText(this, "No hay amigos para mostrar\nRegistre un nuevo amigo", Toast.LENGTH_LONG).show();
        }
        db.close();
        return listaAmigos;
    }

    //Envia a la actividad NuevoAmigo
    public void nuevoAmigo(){
        Intent intent = new Intent(this, NuevoAmigo.class);
        startActivity(intent);
    }

    //Se añade el menu_list_amigos para agregar el boton de buscar al Action Bar y sus eventos de escucha
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_amigos, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        //Se crea clase de escucha de eventos cuando el texto de busqueda cambia
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //Se actualiza el tipo de busqueda y se crea de nuevo el adaptador
                tipoLista = LISTA_BUSQUEDA;
                nombreBusqueda = newText;
                adaptador = new Adaptador(MainActivity.this, getArrayItems());
                listViewAmigos.setAdapter(adaptador);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}