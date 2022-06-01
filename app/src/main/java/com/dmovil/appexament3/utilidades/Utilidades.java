package com.dmovil.appexament3.utilidades;

public class Utilidades {

    //Constantes de los campos de tabla Amigo
    public static final String TABLA_AMIGO = "Amigos";
    public static final String CAMPO_ID = "idAmigo";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_TELEFONO = "telefono";
    public static final String CAMPO_EMAIL = "email";
    public static final String CAMPO_DIRECCION = "direccion";

    //Se crea una constante con la sentencia para crear la tabla Amigos
    public static final String CREAR_TABLA_AMIGO = "CREATE TABLE " + TABLA_AMIGO + " (" +
            CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_NOMBRE +  " TEXT, " + CAMPO_TELEFONO + " TEXT, " +
            CAMPO_EMAIL + " TEXT, " + CAMPO_DIRECCION + " TEXT)";

    //Se crea una constante con la sentencia para eliminar la tabla Amigos
    public static final String ELIMINAR_TABLA_AMIGO = "DROP TABLE IF EXIST " + TABLA_AMIGO;
}
