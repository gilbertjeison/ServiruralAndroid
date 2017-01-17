package Session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Space;

/**
 * Created by Desarrollo on 12/12/2015.
 */

public class ManejadorSession {

    public static final String KEY_PRINTER_MAC = "mac_drss_prnt" ;
    public static final String KEY_PRINTER_NAME = "nm_bltth_prnt" ;
    public static final String KEY_ENABLED= "chkImpresion" ;
    public static final String KEY_MENSAJERO_NAME = "mnsjr_nm";
    public static final String KEY_MENSAJERO_CODIGO = "mnsjr_cdg";
    //CONTEXTO
    Context _context;
    //PREFERENCIAS
    SharedPreferences sPre;
    //EDITOR
    SharedPreferences.Editor editor;

    public ManejadorSession(Context context) {
        this._context = context;

        sPre = _context.getSharedPreferences("com.meysi.desarrollo.servirural_android_preferences",Context.MODE_PRIVATE); //PreferenceManager.getDefaultSharedPreferences(_context);

        editor = sPre.edit();
    }

    //GUARDAR MAC ADRESS DE LA IMPRESORA
    public boolean printerMacSet(String mac, String name){
        editor.putString(KEY_PRINTER_MAC, mac);
        editor.putString(KEY_PRINTER_NAME, name);
        return editor.commit();
    }

    //GUARDAR DATOS DE USUARIO
    public boolean LoginSet(int codigo, String nombre)
    {
        editor.putInt(KEY_MENSAJERO_CODIGO, codigo);
        editor.putString(KEY_MENSAJERO_NAME,nombre);

        return editor.commit();
    }

    public boolean GetPrintEnabled()
    {
        return sPre.getBoolean(KEY_ENABLED,false);
    }

    public String GetUserName()
    {
        return sPre.getString(KEY_MENSAJERO_NAME,"Ninguno");
    }

    public int GetUserCode()
    {
        return sPre.getInt(KEY_MENSAJERO_CODIGO,0);
    }

    //Devuelve MAC
    public String GetPrinterMac() {
        return sPre.getString(KEY_PRINTER_MAC, null);
    }


    public String GetPrinterName() {
        return sPre.getString(KEY_PRINTER_NAME, null);
    }
}
