package ObjetosAccesoDatos;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.AbstractList;
import java.util.LinkedList;

import Objetos.Conexion;
import Objetos.JSONparser;
import Objetos.RecuadoListItem;
import Objetos.WebServiceHelper;

/**
 * Created by Equipo on 02/09/2015.
 */
public class DaoUsuario {

    String result;
    WebServiceHelper wsh= new WebServiceHelper();
    JSONparser jParser = new JSONparser();

    public static String UrlServer = Conexion.SERVIDOR+"/Usuarios/get_usuarios.php";
    public static String UrlSession = Conexion.SERVIDOR+"/Usuarios/CreateSession.php";
    public static String UrlCheckSession = Conexion.SERVIDOR+"/Usuarios/CheckSession.php";
    public static String UrlCloseSession = Conexion.SERVIDOR+"/Usuarios/CloseSession.php";


    public JSONObject ObtenerDatosServidor(AbstractList<NameValuePair> parametros,String UrlWebService)
    {
        InputStream is = wsh.HttpConnect(parametros, UrlWebService);

        if (is != null)
        {
            try {

                result = wsh.ObtenerRespuestaPost(is);
                JSONObject jo = new JSONObject(result);

                return jo;

            } catch (JSONException e) {
                Log.e("Error ObtenerArrayJson", " = " + e.toString());
                return null;
            }
        }
        else
        {
            return null;
        }
    }


    public boolean CreateSession(int usuario)
    {
        boolean res = false;

        //PARAMETROS
        AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
        parametros.add(new BasicNameValuePair("usuario",String.valueOf(usuario)));


        try {

        JSONObject json = jParser.makeHttpRequest(UrlSession, "POST", parametros);

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


    public boolean CheckSession(int usuario)
    {
        boolean res = false;

        //PARAMETROS
        AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
        parametros.add(new BasicNameValuePair("usuario",String.valueOf(usuario)));


        try {

            JSONObject json = jParser.makeHttpRequest(UrlCheckSession, "POST", parametros);

            if (json != null) {
                // Checking for SUCCESS TAG
                int success = json.getInt("correcto");

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

    public boolean CloseSession(int usuario)
    {
        boolean res = false;

        //PARAMETROS
        AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
        parametros.add(new BasicNameValuePair("usuario",String.valueOf(usuario)));


        try {

            JSONObject json = jParser.makeHttpRequest(UrlCloseSession, "POST", parametros);

            if (json != null) {
                // Checking for SUCCESS TAG
                int success = json.getInt("correcto");

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
