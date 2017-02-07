package com.juanancon.a2daw.mislibros;

// Llamadas a las librerías
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBlibros {

    // Las constantes de libro
    public static final String FILA_ID = "_id";
    public static final String TITULO = "titulo";
    public static final String AUTOR = "autor";
    public static final String EDITORIAL = "editorial";
    public static final String ISBN = "isbn";
    public static final String ANIO = "anio";
    public static final String PAGINAS = "paginas";
    public static final String EBOOK = "ebook";
    public static final String LEIDO = "leido";
    public static final String ESTRELLAS = "estrellas";
    public static final String RESUMEN = "resumen";

    static final String TAG = "DBlibros";
    static final String DATABASE_NAME = "mislibros";
    static final String DATABASE_TABLE = "libros";
    static final int DATABASE_VERSION = 1;

    //Setencia para crear la tabla libros
    private static final String
            DATABASE_CREATE = "create table libros (_id integer primary key autoincrement, "
            + TITULO + " text, "
            + AUTOR + " text, "
            + EDITORIAL + " text, "
            + ISBN + " text, "
            + ANIO + " text, "
            + PAGINAS + " text, "
            + EBOOK + " integer, "
            + LEIDO + " integer, "
            + ESTRELLAS + " float, "
            + RESUMEN + " text "
            + ");";

    DatabaseHelper mDbHelper;
    SQLiteDatabase myBD;
    final Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);//Ejecutamos la setencia de crear la tabla libros
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " //$NON-NLS-1$//$NON-NLS-2$
                    + newVersion + ", which will destroy all old data"); //$NON-NLS-1$
            //db.execSQL("DROP TABLE IF EXISTS usersinfo"); //$NON-NLS-1$
            onCreate(db);
        }
    }


    public DBlibros(Context ctx)
    {
        this.mCtx = ctx;
        mDbHelper = new DatabaseHelper(mCtx);
    }


    //Inserta un libro en la BD
    public long insertLibro(String titulo, String autor, String editorial, String isbn, String anio,
                            String paginas, Integer ebook, Integer leido, Float estrellas, String resumen)
    {
        ContentValues campos = new ContentValues();

        campos.put(TITULO, titulo);
        campos.put(AUTOR, autor);
        campos.put(EDITORIAL, editorial);
        campos.put(ISBN, isbn);
        campos.put(ANIO, anio);
        campos.put(PAGINAS, paginas);
        campos.put(EBOOK, ebook);
        campos.put(LEIDO, leido);
        campos.put(ESTRELLAS, estrellas);
        campos.put(RESUMEN, resumen);

        return this.myBD.insert(DATABASE_TABLE, null, campos);
    }

    // Devuelve los libros guardados
    public Cursor getLibros()
    {

        return this.myBD.rawQuery("SELECT * FROM libros", null);
    }

    // Devuelve un libro según su ID
    public Cursor getUnLibro(long rowId)
    {

        Cursor cursor = myBD.rawQuery("SELECT * FROM libros" + " WHERE _id = " + rowId, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    // Devuelve el numero de entradas de la tabla libro
    public int getCount() throws SQLException
    {
        Cursor cursor = myBD.rawQuery("select count(*) from libros ", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    // Abre la BD para su lectura
    public DBlibros openR() throws SQLException
    {
        myBD = mDbHelper.getReadableDatabase();
        return this;
    }

    // Abre la BD para su escritura
    public DBlibros openW() throws SQLException
    {
        myBD = this.mDbHelper.getWritableDatabase();
        return this;
    }



    // Cierra la BD
    public void close()
    {
        this.mDbHelper.close();
    }


    public boolean deleteLibro(long rowId)
    {

        return this.myBD.delete(DATABASE_TABLE, FILA_ID  + "=" + rowId, null) > 0; //$NON-NLS-1$
    }

    // Actualiza un libro
    public boolean updateLibro(long rowId, String titulo, String autor, String editorial, String isbn, String anio,
                               String paginas, Integer ebook, Integer leido, Float estrellas, String resumen) {
        ContentValues campos = new ContentValues();

        campos.put(TITULO, titulo);
        campos.put(AUTOR, autor);
        campos.put(EDITORIAL, editorial);
        campos.put(ISBN, isbn);
        campos.put(ANIO, anio);
        campos.put(PAGINAS, paginas);
        campos.put(EBOOK, ebook);
        campos.put(LEIDO, leido);
        campos.put(ESTRELLAS, estrellas);
        campos.put(RESUMEN, resumen);

        return this.myBD.update(DATABASE_TABLE, campos, FILA_ID + "=" + rowId, null) > 0; //$NON-NLS-1$
    }


}