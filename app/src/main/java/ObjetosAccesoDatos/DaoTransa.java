package ObjetosAccesoDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.meysi.desarrollo.servirural_android.TransaccionesActivity;

import java.text.DateFormat;
import java.util.Calendar;

import DatabaseManejador.BaseManjeador;
import Objetos.Transa;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Desarrollo on 23/12/2015.
 */
public class DaoTransa {

    Context con;

    public DaoTransa(Context con) {
        super();
        this.con = con;
    }

    public void Delete()
    {
        BaseManjeador oCon = new BaseManjeador(con);
        SQLiteDatabase db = oCon.getWritableDatabase();


        db.delete(BaseManjeador.TABLA_TRANSACCIONES, null,null);
        db.close();
    }

    public void AgregarTransaccion(Transa transa)
    {
        BaseManjeador oCon = new BaseManjeador(con);
        SQLiteDatabase db = oCon.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(BaseManjeador.KEY_TIPO_TRANSACCION, transa.getTipo_transaccion());
        values.put(BaseManjeador.KEY_LINEA, transa.getLinea());
        values.put(BaseManjeador.KEY_PRECIO, transa.getPrecio());
        values.put(BaseManjeador.KEY_FECHA, DateFormat.getDateTimeInstance().format(new Date()));
        values.put(BaseManjeador.KEY_OBSERVACION, transa.getObservacion());

        db.insert(BaseManjeador.TABLA_TRANSACCIONES, null, values);
        db.close();
    }



    //Datos para EXPORTACION
    public ArrayList<Transa> GetTrasa(){

        ArrayList<Transa> list = new ArrayList<Transa>();
        BaseManjeador oCon = new BaseManjeador(con);
        SQLiteDatabase db = oCon.getWritableDatabase();

        String query = "SELECT * FROM "+ BaseManjeador.TABLA_TRANSACCIONES;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                list.add(new Transa(c.getInt(0),c.getString(1),c.getString(2),c.getString(3), c.getString(4),c.getString(5)));
            }
            while (c.moveToNext());
        }

        db.close();
        c.close();

        return list;
    }
}

