package ObjetosAccesoDatos;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.LinkedList;

import Objetos.Conexion;
import Objetos.JSONparser;
import Objetos.RecuadoListItem;
import Objetos.Transaccion;

/**
 * Created by Desarrollo on 16/12/2015.
 */
public class DaoTransacciones {

    JSONparser jParser = new JSONparser();
    private static String url_transaccion_crear= Conexion.SERVIDOR + "Transacciones/CreateTransaction.php";
    private static String url_transaccion_crear_rec= Conexion.SERVIDOR + "Transacciones/CreateTransactionRecarga.php";

    JSONArray a_recaudos;

    public boolean CreateTransaction(Transaccion transaccion)
    {
        boolean res = false;

        //PARAMETROS
        AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
        parametros.add(new BasicNameValuePair("codigo_venta",String.valueOf(transaccion.getCodigo_venta())));
        parametros.add(new BasicNameValuePair("tipo_transaccion", String.valueOf(transaccion.getTipo_transaccion())));
        parametros.add(new BasicNameValuePair("precio",String.valueOf(transaccion.getPrecio())));
        parametros.add(new BasicNameValuePair("observacion",transaccion.getObservacion()));

        try {

            JSONObject json = jParser.makeHttpRequest(url_transaccion_crear, "POST", parametros);

            if (json != null) {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {
                    //ACTUALIZADO CORRECTAMENTE
                    res = true;
                    Log.d("Resultado: ", json.toString());
                }
                else
                {
                    res = false;
                    Log.d("Resultado: ", json.toString());
                }
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }

        return res;
    }

    public boolean CreateTransactionRecarga(Transaccion transaccion)
    {
        boolean res = false;

        //PARAMETROS
        AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
        parametros.add(new BasicNameValuePair("codigo_venta",String.valueOf(transaccion.getCodigo_venta())));
        parametros.add(new BasicNameValuePair("tipo_transaccion", String.valueOf(transaccion.getTipo_transaccion())));
        parametros.add(new BasicNameValuePair("precio",String.valueOf(transaccion.getPrecio())));
        parametros.add(new BasicNameValuePair("observacion",transaccion.getObservacion()));

        try {

            JSONObject json = jParser.makeHttpRequest(url_transaccion_crear_rec, "POST", parametros);

            if (json != null) {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {
                    //ACTUALIZADO CORRECTAMENTE
                    res = true;
                    Log.d("Resultado: ", json.toString());
                }
                else
                {
                    res = false;
                    Log.d("Resultado: ", json.toString());
                }
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }

        return res;
    }
}
