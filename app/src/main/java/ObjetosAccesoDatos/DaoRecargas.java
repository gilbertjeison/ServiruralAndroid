package ObjetosAccesoDatos;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Objetos.Conexion;
import Objetos.JSONparser;
import Objetos.RecargaItem;

/**
 * Created by Desarrollo on 22/12/2015.
 */
public class DaoRecargas {

    JSONparser jParser = new JSONparser();
    JSONArray a_recaudos;
    private static String url_all_clientes_rec = Conexion.SERVIDOR + "RecaudoRec/GetRecaudoRec.php";
    private static String url_cliente_LIC = Conexion.SERVIDOR + "RecaudoRec/getClientRecargaData.php";
    private static String url_act_recaudo = Conexion.SERVIDOR + "Recaudo/SaveRecaudo.php";
    private static String url_act_recaudo_rec = Conexion.SERVIDOR + "Recaudo/SaveRecaudoRecarga.php";

    public List<RecargaItem> GetClientesRecaudoLinea(int usuario)
    {
        List<RecargaItem> listRecaudos = new ArrayList<RecargaItem>();

        AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
        parametros.add(new BasicNameValuePair("usuario",String.valueOf(usuario)));

        try {

            JSONObject json = jParser.makeHttpRequest(url_all_clientes_rec, "POST", parametros);

            if (json != null) {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    a_recaudos = json.getJSONArray("recaudo");
                    Log.d("Todos los clientes: ", json.toString());

                    // looping through All Products
                    //Log.i("ramiro", "produtos.length" + products.length());
                    for (int i = 0; i < a_recaudos.length(); i++) {

                        JSONObject c = a_recaudos.getJSONObject(i);

                        // LLENAR OBJETO CON VALORES
                        RecargaItem recuadoListItem =
                                new RecargaItem();

                        recuadoListItem.setCodigo(c.getLong("codigo"));
                        recuadoListItem.setNombre(c.getString("nombre"));
                        recuadoListItem.setDireccion(c.getString("direccion"));
                        recuadoListItem.setCodigo_cliente(c.getLong("codigo_cliente"));
                        recuadoListItem.setCodigo_venta(c.getLong("codigo_venta"));
                        recuadoListItem.setNum_rec(c.getInt("num_lic"));
                        recuadoListItem.setValor_pagado(c.getLong("_valor_pagado"));
                        recuadoListItem.setLinea(c.getString("linea"));
                        recuadoListItem.setSaldo_rec(c.getLong("saldo_lic"));

                        listRecaudos.add(recuadoListItem);

                    }
                }
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }

        return listRecaudos;
    }

    public List<RecargaItem> GetClientRecaudo(String cliente)    {
        List<RecargaItem> listRecaudos = new ArrayList<RecargaItem>();
        try {

//            PARAMETROS

            AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
            parametros.add(new BasicNameValuePair("cliente",cliente));

            JSONObject json = jParser.makeHttpRequest(url_cliente_LIC, "POST", parametros);
            Log.d("cliente: ", json.toString());
            if (json != null) {
                // Checking for SUCCESS TAG
                int success = json.getInt("success");

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    a_recaudos = json.getJSONArray("cliente");


                    // looping through All Products
                    //Log.i("ramiro", "produtos.length" + products.length());
                    for (int i = 0; i < a_recaudos.length(); i++) {

                        JSONObject c = a_recaudos.getJSONObject(i);

                        // LLENAR OBJETO CON VALORES
                        RecargaItem rLI =
                                new RecargaItem();
                        rLI.setCodigo(c.getLong("codigo"));
                        rLI.setCodigo_cliente(c.getLong("codigo_cliente"));
                        rLI.setNombre(c.getString("nombre"));
                        rLI.setDireccion(c.getString("direccion"));
                        rLI.setBarrio(c.getString("barrio"));
                        rLI.setSaldo_rec(c.getLong("saldo_linea"));
                        rLI.setRecibo_venta(c.getLong("recibo"));
                        rLI.setFecha_venta(c.getString("fecha"));
                        rLI.setCodigo_vendedor(c.getLong("codigo_vendedor"));
                        rLI.setPrecio_venta(c.getLong("precio"));
                        rLI.setFecha_export(c.getString("fecha_export"));
                        rLI.setNombre_vendedor(c.getString("nombre_vendedor"));
                        rLI.setNombre_cobrador(c.getString("nombre_cobrador"));
                        rLI.setCodigo_venta(c.getLong("codigo_venta"));
                        rLI.setValor_pagado(c.getLong("_valor_pagado"));
                        rLI.setLinea(c.getString("linea"));
                        listRecaudos.add(rLI);
                    }
                }
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }

        return listRecaudos;
    }

    public boolean UpdateRecaudo(RecargaItem recaudo_upd)
    {
        boolean res = false;

        //PARAMETROS
        AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
        parametros.add(new BasicNameValuePair("codigo",String.valueOf(recaudo_upd.getCodigo())));
        parametros.add(new BasicNameValuePair("valor_pagado", String.valueOf(recaudo_upd.getValor_pagado())));
        parametros.add(new BasicNameValuePair("observacion",recaudo_upd.getObservacion_recaudo()));

        try {

            JSONObject json = jParser.makeHttpRequest(url_act_recaudo, "POST", parametros);

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

    public boolean UpdateRecaudoRecarga(RecargaItem recaudo_upd)
    {
        boolean res = false;

        //PARAMETROS
        AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
        parametros.add(new BasicNameValuePair("codigo",String.valueOf(recaudo_upd.getCodigo())));
        parametros.add(new BasicNameValuePair("valor_pagado", String.valueOf(recaudo_upd.getValor_pagado())));
        parametros.add(new BasicNameValuePair("observacion",recaudo_upd.getObservacion_recaudo()));

        try {

            JSONObject json = jParser.makeHttpRequest(url_act_recaudo_rec, "POST", parametros);

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
