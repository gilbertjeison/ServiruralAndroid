package Objetos;

import android.accounts.AbstractAccountAuthenticator;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractList;
import java.util.List;

/**
 * Created by Desarrollo on 02/09/2015.
 */
public class WebServiceHelper {

    //InputStream is = null;
    String result = "";



    public InputStream HttpConnect(AbstractList<NameValuePair> parametros,String UrlWebService) {
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httPost = new HttpPost(UrlWebService);
            httPost.setEntity(new UrlEncodedFormEntity(parametros));

            //EJECUTA LA PETICION ENVIADA CON DATOS A POST
            HttpResponse httpResponse = httpClient.execute(httPost);
            HttpEntity entity = httpResponse.getEntity();
            return entity.getContent();

        } catch (Exception e)
        {
            Log.i("Error conexión: ",""+e.toString());
            return null;
        }
    }

    public String ObtenerRespuestaPost(InputStream is)
    {
        //CONVIERTE UNA RESPUESTA A STRING
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String linea = null;
            while ((linea = reader.readLine()) != null)
            {
                sb.append(linea+"\n");
            }
            is.close();

            Log.e("ObtenerRespuestaPost", "resultado = " + sb.toString());
            return sb.toString();
        }
        catch (Exception e)
        {
            Log.i("ObtenerRespuestaPost","Error convirtiendo resultados:"+e.toString());
            return null;
        }
    }



}
