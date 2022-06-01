package com.dmovil.appexament3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.dmovil.appexament3.utilidades.Utilidades;

public class BDAmigo extends SQLiteOpenHelper {

    //Se crea el constructor heredado de la clase SQLiteOpenHelper
    public BDAmigo(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Se crea la tabla Amigos utilizando la constante de la clase Utilidades
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_AMIGO);
    }

    //Se elimina y vuelve a crear la tabla Amigos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Utilidades.ELIMINAR_TABLA_AMIGO);
        onCreate(db);
    }
}
