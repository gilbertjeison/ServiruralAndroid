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
import java.util.LinkedList;
import java.util.List;

import Objetos.Conexion;
import Objetos.JSONparser;
import Objetos.RecaudoSoatListItem;
import Objetos.RecuadoListItem;

/**
 * Created by Desarrollo on 21/12/2015.
 */
public class DaoRecaudoSoat {

    JSONparser jParser = new JSONparser();
    JSONArray a_recaudos;
    private static String url_all_clientes_soat = Conexion.SERVIDOR + "RecaudoSoat/getRecaudoSoat.php";
    private static String url_cliente_soat = Conexion.SERVIDOR + "RecaudoSoat/getClientSoatData.php";
    private static String url_act_recaudo = Conexion.SERVIDOR + "Recaudo/SaveRecaudo.php";

    public List<RecaudoSoatListItem> GetClientesRecaudoSoat(int usuario)
    {
        List<RecaudoSoatListItem> listRecaudos = new ArrayList<RecaudoSoatListItem>();

        AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
        parametros.add(new BasicNameValuePair("usuario",String.valueOf(usuario)));

        try {

            JSONObject json = jParser.makeHttpRequest(url_all_clientes_soat, "POST", parametros);

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
                        RecaudoSoatListItem recuadoListItem =
                                new RecaudoSoatListItem();

                        recuadoListItem.setCodigo(c.getLong("codigo"));
                        recuadoListItem.setNombre(c.getString("nombre"));
                        recuadoListItem.setDireccion(c.getString("direccion"));
                        recuadoListItem.setCodigo_cliente(c.getLong("codigo_cliente"));
                        recuadoListItem.setCodigo_venta(c.getLong("codigo_venta"));
                        recuadoListItem.setNum_soats(c.getInt("num_soats"));
                        recuadoListItem.setDias_cobro(c.getString("dias_cobro"));
                        recuadoListItem.setSaldo_soat(c.getLong("saldo_soat"));
                        recuadoListItem.setValor_pagado(c.getLong("_valor_pagado"));

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

    public List<RecaudoSoatListItem> GetClientRecaudo(String cliente)    {
        List<RecaudoSoatListItem> listRecaudos = new ArrayList<RecaudoSoatListItem>();
        try {

//            PARAMETROS

            AbstractList<NameValuePair> parametros = new LinkedList<NameValuePair>();
            parametros.add(new BasicNameValuePair("cliente",cliente));

            JSONObject json = jParser.makeHttpRequest(url_cliente_soat, "POST", parametros);
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
                        RecaudoSoatListItem rLI =
                                new RecaudoSoatListItem();
                        rLI.setCodigo(c.getLong("codigo"));
                        rLI.setCodigo_cliente(c.getLong("codigo_cliente"));
                        rLI.setNombre(c.getString("nombre"));
                        rLI.setDireccion(c.getString("direccion"));
                        rLI.setBarrio(c.getString("barrio"));
                        rLI.setSaldo_soat(c.getLong("saldo_linea"));
                        rLI.setRecibo_venta(c.getLong("recibo_venta"));
                        rLI.setFecha_venta(c.getString("fecha_venta"));
                        rLI.setCodigo_vendedor(c.getLong("codigo_vendedor"));
                        rLI.setPrecio_venta(c.getLong("precio_venta"));
                        rLI.setFecha_export(c.getString("fecha_export"));
                        rLI.setNombre_vendedor(c.getString("nombre_vendedor"));
                        rLI.setNombre_cobrador(c.getString("nombre_cobrador"));
                        rLI.setCodigo_venta(c.getLong("codigo_venta"));
                        rLI.setValor_pagado(c.getLong("_valor_pagado"));
                        rLI.setPoliza(c.getString("poliza"));
                        rLI.setPlaca(c.getString("placa"));
                        rLI.setTipo_vehiculo(c.getString("tipo_vehiculo"));
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

    public boolean UpdateRecaudo(RecaudoSoatListItem recaudo_upd)
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
