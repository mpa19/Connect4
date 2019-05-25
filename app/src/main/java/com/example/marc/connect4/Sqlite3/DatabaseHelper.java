package com.example.marc.connect4.Sqlite3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BDDConnect4";
    private static final String TABLE_NAME = "Logs";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_JUGADOR1 = "jugador1";
    private static final String COLUMN_JUGADOR2 = "jugador2";
    private static final String COLUMN_DATA = "data";
    private static final String COLUMN_GRAELLA = "graella";
    private static final String COLUMN_TIEMPO = "tiempo";
    private static final String COLUMN_RESULTADO = "resultado";
    private static final String COLUMN_MOVIMIENTOS = "movimientos";


    private static final int DB_VERSION = 2;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "CREATE TABLE "+TABLE_NAME+" ("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+COLUMN_JUGADOR1+" TEXT NOT NULL, " +
                ""+COLUMN_JUGADOR2+" TEXT, "+COLUMN_DATA+" TEXT NOT NULL, "+COLUMN_GRAELLA+" INTEGER NOT NULL, "+COLUMN_TIEMPO+" INTEGER, "+COLUMN_RESULTADO+" TEXT NOT NULL," +
                ""+COLUMN_MOVIMIENTOS+" TEXT NOT NULL)";
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int prevVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME+"");

        onCreate(db);
    }

    public void addResult(String jugador1, String jugador2, String data, String graella, String tiempo, String resultado, String movimientos){

        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_JUGADOR1, jugador1);
        cv.put(COLUMN_JUGADOR2, jugador2);
        cv.put(COLUMN_DATA, data);
        cv.put(COLUMN_GRAELLA, graella);
        cv.put(COLUMN_TIEMPO, tiempo);
        cv.put(COLUMN_RESULTADO, resultado);
        cv.put(COLUMN_MOVIMIENTOS, movimientos);

        bd.insert(TABLE_NAME, null, cv);

    }

    public Cursor getResultat(){
        SQLiteDatabase bd = this.getReadableDatabase();
        String query = "SELECT _id,jugador1, jugador2, data, resultado FROM Logs;";
        return bd.rawQuery(query, null);
    }

    public Cursor getMoviments(String where){
        SQLiteDatabase bd = this.getReadableDatabase();
        String query = "SELECT * FROM Logs WHERE _id = "+where+";";
        return bd.rawQuery(query, null);
    }


}
