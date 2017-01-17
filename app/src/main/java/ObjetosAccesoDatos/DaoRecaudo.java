package ObjetosAccesoDatos;

import android.util.Log;

import com.meysi.desarrollo.servirural_android.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Objetos.Conexion;
import Objetos.JSONparser;
import Objetos.RecuadoListItem;

/**
 * Created by mike on 20/10/2015.
 */
public class DaoRecaudo {

    JSONparser jParser = new JSONparser();
    private static String url_all_clientes = Conexion.SERVIDOR + "Recaudo/DaoRecaudo.php";
    private static String url_cliente = Conexion.SERVIDOR + "Recaudo/GetClientData.php";
    private static String url_act_recaudo = Conexion.SERVIDOR + "Recaudo/SaveRecaudo.php";

    JSONArray a_recaudos;

    public List<RecuadoListItem> GetClientRecaudo(String cliente)    {
        List<RecuadoListItem> listRecaudos = new ArrayList<RecuadoListItem>();
        try {

//            PARAMETROS

            AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
            parametros.add(new BasicNameValuePair("cliente",cliente));

            JSONObject json = jParser.makeHttpRequest(url_cliente, "POST", parametros);
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
                        RecuadoListItem recuadoListItem =
                                new RecuadoListItem(R.drawable.user,
                                                    c.getLong("codigo"),
                                                    c.getLong("codigo_cliente"),
                                                    c.getString("nombre"),
                                                    c.getString("direccion"),
                                                    c.getString("barrio"),
                                                    c.getLong("linea"),
                                                    c.getLong("saldo_linea"),
                                                    c.getLong("recibo_venta"),
                                                    c.getString("fecha_venta"),
                                                    c.getLong("codigo_vendedor"),
                                                    c.getLong("codigo_cobrador"),
                                                    c.getLong("precio_venta"),
                                                    c.getString("fecha_export"),
                                                    c.getString("nombre_vendedor"),
                                                    c.getString("nombre_cobrador"),
                                                    c.getInt("dia_corte"),
                                                    0,
                                                    c.getLong("codigo_venta"),
                                                    c.getLong("_valor_pagado"),
                                                    c.getLong("minutos"));


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

    public List<RecuadoListItem> GetClientesRecaudo(int usuario)
    {
        List<RecuadoListItem> listRecaudos = new ArrayList<RecuadoListItem>();

        AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
        parametros.add(new BasicNameValuePair("usuario",String.valueOf(usuario)));

        try {

            JSONObject json = jParser.makeHttpRequest(url_all_clientes, "POST", parametros);

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
                        RecuadoListItem recuadoListItem =
                                new RecuadoListItem(R.drawable.user,
                                        c.getLong("codigo"),
                                        c.getLong("codigo_cliente"),
                                        c.getString("nombre"),
                                        c.getString("direccion"),
                                        c.getString("barrio"),
                                        c.getLong("serial"),
                                        c.getLong("sum(v.saldo_linea)"),
                                        c.getLong("recibo_venta"),
                                        c.getString("fecha_venta"),
                                        c.getLong("codigo_vendedor"),
                                        c.getLong("codigo_cobrador"),
                                        c.getLong("precio_venta"),
                                        c.getString("_observacion_recaudo"),
                                        c.getLong("_valor_pagado"),
                                        c.getString("_recibo_generado") == "" ? Long.parseLong(c.getString("_recibo_generado")):0,
                                        c.getString("fecha_cobro"),
                                        c.getString("fecha_export"),
                                        c.getString("nombre_vendedor"),
                                        c.getString("nombre_cobrador"),
                                        c.getInt("dia_corte"),
                                        c.getInt("num_lineas"),
                                        c.getLong("codigo_venta"),
                                        c.getString("dias_cobro"));

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

    public boolean UpdateRecaudo(RecuadoListItem recaudo_upd)
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





}
