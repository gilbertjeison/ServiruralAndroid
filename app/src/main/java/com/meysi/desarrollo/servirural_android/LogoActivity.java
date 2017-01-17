package com.meysi.desarrollo.servirural_android;

import com.meysi.desarrollo.servirural_android.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class LogoActivity extends Activity {

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final boolean TOGGLE_ON_CLICK = true;
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        Thread timerThread = new Thread()        {
            public void run()
            {
                try
                {
                    sleep(3000);

                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }finally {
                    //createToDo();
                    Intent intent = new Intent(LogoActivity.this,LoginActivity.class);
                    startActivity( intent);
                    finish();
                }
            }
        };

        timerThread.start();

    }



//    public void createToDo() {
//
//        SqliteManejador oCon = new SqliteManejador(this);
//        SQLiteDatabase db = oCon.getWritableDatabase();
//
//        ContentValues values1 = new ContentValues();
//
//        values1.put(SqliteManejador.KEY_CODIGO, 4);
//        values1.put(SqliteManejador.KEY_CODIGO_CLIENTE, 1);
//        values1.put(SqliteManejador.KEY_NOMBRE, "Juan Guillermo Salas");
//        values1.put(SqliteManejador.KEY_BARRIO, "Alfonso Lopez");
//        values1.put(SqliteManejador.KEY_DIRECCION, "Calle 114 # 45-43");
//        values1.put(SqliteManejador.KEY_LINEA, "3178882309");
//        values1.put(SqliteManejador.KEY_RECIBO_VENTA, 4);
//        values1.put(SqliteManejador.KEY_PRECIO_VENTA, 90000);
//
//        ContentValues values2 = new ContentValues();
//
//        values2.put(SqliteManejador.KEY_CODIGO, 2);
//        values2.put(SqliteManejador.KEY_CODIGO_CLIENTE, 2);
//        values2.put(SqliteManejador.KEY_NOMBRE, "Ernesto Piedrahita");
//        values2.put(SqliteManejador.KEY_BARRIO, "Nueva floresta");
//        values2.put(SqliteManejador.KEY_DIRECCION, "Calle 44 # 34-87");
//        values2.put(SqliteManejador.KEY_LINEA, "3129888999");
//        values2.put(SqliteManejador.KEY_RECIBO_VENTA, 2);
//        values2.put(SqliteManejador.KEY_PRECIO_VENTA, 95000);
//
//
//        ContentValues values3 = new ContentValues();
//
//        values3.put(SqliteManejador.KEY_CODIGO, 1);
//        values3.put(SqliteManejador.KEY_CODIGO_CLIENTE, 3);
//        values3.put(SqliteManejador.KEY_NOMBRE, "Amparo Fernandez");
//        values3.put(SqliteManejador.KEY_BARRIO, "Nueva floresta");
//        values3.put(SqliteManejador.KEY_DIRECCION, "Cra 41 # 21-00");
//        values3.put(SqliteManejador.KEY_LINEA, "3123229865");
//        values3.put(SqliteManejador.KEY_RECIBO_VENTA, 1);
//        values3.put(SqliteManejador.KEY_PRECIO_VENTA, 110000);
//
//
//        ContentValues values4 = new ContentValues();
//
//        values4.put(SqliteManejador.KEY_CODIGO, 6);
//        values4.put(SqliteManejador.KEY_CODIGO_CLIENTE, 3);
//        values4.put(SqliteManejador.KEY_NOMBRE, "Amparo Fernandez");
//        values4.put(SqliteManejador.KEY_BARRIO, "Nueva floresta");
//        values4.put(SqliteManejador.KEY_DIRECCION, "Cra 41 # 21-00");
//        values4.put(SqliteManejador.KEY_LINEA, "3017841298");
//        values4.put(SqliteManejador.KEY_RECIBO_VENTA, 6);
//        values4.put(SqliteManejador.KEY_PRECIO_VENTA, 110000);
//
//
//        ContentValues values5 = new ContentValues();
//
//        values5.put(SqliteManejador.KEY_CODIGO, 3);
//        values5.put(SqliteManejador.KEY_CODIGO_CLIENTE, 4);
//        values5.put(SqliteManejador.KEY_NOMBRE, "Juan Carlos Quesada");
//        values5.put(SqliteManejador.KEY_BARRIO, "Petecuy");
//        values5.put(SqliteManejador.KEY_DIRECCION, "Diag 21 # 90-123");
//        values5.put(SqliteManejador.KEY_LINEA, "3156759032");
//        values5.put(SqliteManejador.KEY_RECIBO_VENTA, 3);
//        values5.put(SqliteManejador.KEY_PRECIO_VENTA, 95000);
//
//
//        ContentValues values6 = new ContentValues();
//
//        values6.put(SqliteManejador.KEY_CODIGO, 5);
//        values6.put(SqliteManejador.KEY_CODIGO_CLIENTE, 5);
//        values6.put(SqliteManejador.KEY_NOMBRE, "Natalia Venavidez");
//        values6.put(SqliteManejador.KEY_BARRIO, "Petecuy");
//        values6.put(SqliteManejador.KEY_DIRECCION, "Calle 31 # 34-34");
//        values6.put(SqliteManejador.KEY_LINEA, "3110003323");
//        values6.put(SqliteManejador.KEY_RECIBO_VENTA, 5);
//        values6.put(SqliteManejador.KEY_PRECIO_VENTA, 85000);
//
//
//        ContentValues values7 = new ContentValues();
//
//        values7.put(SqliteManejador.KEY_CODIGO, 7);
//        values7.put(SqliteManejador.KEY_CODIGO_CLIENTE, 5);
//        values7.put(SqliteManejador.KEY_NOMBRE, "Natalia Venavidez");
//        values7.put(SqliteManejador.KEY_BARRIO, "Sol de oriente");
//        values7.put(SqliteManejador.KEY_DIRECCION, "Calle 31 # 34-34");
//        values7.put(SqliteManejador.KEY_LINEA, "3211212232");
//        values7.put(SqliteManejador.KEY_RECIBO_VENTA, 7);
//        values7.put(SqliteManejador.KEY_PRECIO_VENTA, 85000);
//
//
//        // insert row
//        db.insert(SqliteManejador.TABLA_RECAUDOS, null, values1);
//        db.insert(SqliteManejador.TABLA_RECAUDOS, null, values2);
//        db.insert(SqliteManejador.TABLA_RECAUDOS, null, values3);
//        db.insert(SqliteManejador.TABLA_RECAUDOS, null, values4);
//        db.insert(SqliteManejador.TABLA_RECAUDOS, null, values5);
//        db.insert(SqliteManejador.TABLA_RECAUDOS, null, values6);
//        db.insert(SqliteManejador.TABLA_RECAUDOS, null, values7);
//    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }




}
