package DatabaseManejador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Desarrollo on 23/12/2015.
 */
public class BaseManjeador extends SQLiteOpenHelper {

    //VERSION DE LA BASE DE DATOS
    public static final int DATABASE_VERSION = 1;

    //NOMBRE DE LA BASE DE DATOS
    public static final String DATABASE_NAME = "servirural";

    //TABLA ENCUESTAS
    public static final String TABLA_TRANSACCIONES = "transacciones";

    //TABLA REIMPRESION
    public static final String TABLA_REIMPRESION = "reimpresiones";

    //CAMPOS TABLA ENCUESTA
    public static final String KEY_ID = "id";
    public static final String KEY_TIPO_TRANSACCION = "tipo";
    public static final String KEY_LINEA = "linea";
    public static final String KEY_PRECIO = "precio";
    public static final String KEY_FECHA = "fecha_transa";
    public static final String KEY_OBSERVACION = "observacion";


    //CAMPOS TABLA REIMPRESION 4 CAMPOS
    public static final String KEY_CLIENTE = "cliente";
    public static final String KEY_FACTURA = "factura";


    //CONTEXTO
    private Context con;
    public BaseManjeador(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String  CREAR_TABLA_TRANSACCIONES = "CREATE TABLE "+TABLA_TRANSACCIONES+" (\n" +
                "    "+KEY_ID+"          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    "+KEY_TIPO_TRANSACCION+"       TEXT,\n" +
                "    "+KEY_LINEA+"    TEXT,\n" +
                "    "+KEY_PRECIO+"    TEXT,\n" +
                "    "+KEY_FECHA+"       TEXT,\n" +
                "    "+KEY_OBSERVACION+" TEXT\n" +
                ");";

        String  CREAR_TABLA_REIMPRESION = "CREATE TABLE "+TABLA_REIMPRESION+" (\n" +
                "    "+KEY_ID+"          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    "+KEY_CLIENTE+"       TEXT,\n" +
                "    "+KEY_FACTURA+"    TEXT,\n" +
                "    "+KEY_FECHA+"       TEXT\n" +
                ");";

        db.execSQL(CREAR_TABLA_TRANSACCIONES);
        db.execSQL(CREAR_TABLA_REIMPRESION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_TRANSACCIONES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_REIMPRESION);
        onCreate(db);
    }
}
