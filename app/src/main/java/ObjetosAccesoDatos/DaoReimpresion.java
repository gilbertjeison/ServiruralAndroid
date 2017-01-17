package ObjetosAccesoDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.text.DateFormat;
import java.util.Date;

import DatabaseManejador.BaseManjeador;
import Objetos.Reimpresion;
import Objetos.Transa;

/**
 * Created by Desarrollo on 03/02/2016.
 */
public class DaoReimpresion
{
        Context con;

        public DaoReimpresion(Context con) {
        super();
        this.con = con;
        }

    public void Delete()
            {
            BaseManjeador oCon = new BaseManjeador(con);
            SQLiteDatabase db = oCon.getWritableDatabase();


            db.delete(BaseManjeador.TABLA_REIMPRESION, null, null);
            db.close();
            }

    public void AgregarReimpresion(Reimpresion reimpresion)
            {
            BaseManjeador oCon = new BaseManjeador(con);
            SQLiteDatabase db = oCon.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(BaseManjeador.KEY_CLIENTE, reimpresion.getCliente());
            values.put(BaseManjeador.KEY_FACTURA, reimpresion.getFactura());
            values.put(BaseManjeador.KEY_FECHA, DateFormat.getDateTimeInstance().format(new Date()));

            db.insert(BaseManjeador.TABLA_REIMPRESION, null, values);
            db.close();
            }



    //Datos para EXPORTACION
    public Reimpresion GetRempresion(String cliente){

            ArrayList<Reimpresion> list = new ArrayList<Reimpresion>();
            BaseManjeador oCon = new BaseManjeador(con);
            SQLiteDatabase db = oCon.getWritableDatabase();

            String query = "SELECT * FROM "+ BaseManjeador.TABLA_REIMPRESION
                    + " where " + BaseManjeador.KEY_CLIENTE +" = "+ cliente
                        +" order by " +  BaseManjeador.KEY_ID + " desc limit 1;";

            Reimpresion r = new Reimpresion();
            Cursor c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
            do {
                r.setId(c.getInt(0));
                r.setCliente(c.getString(1));
                r.setFactura(c.getString(2));
                r.setFecha(c.getString(3));

                list.add(r);
            }
            while (c.moveToNext());
            }

            db.close();
            c.close();

            return r;
            }
}
