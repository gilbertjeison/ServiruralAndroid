package com.meysi.desarrollo.servirural_android;


import android.app.SearchManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import CustomAdapters.ListRecaudosAdapter;
import Objetos.Impresion;
import Objetos.RecuadoListItem;
import Objetos.Reimpresion;
import ObjetosAccesoDatos.DaoRecaudo;
import Session.ManejadorSession;

public class ListRecaudosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lista;
    List<RecuadoListItem> listRecaudos = new ArrayList<RecuadoListItem>();
    ListRecaudosAdapter listAdapter;
    int selected = 0;
    Context mContext;
    //LECTURA DE ARCHIVOS
    BluetoothAdapter mBluetoothAdapter;
    Impresion printer;
    ManejadorSession session;
    String msg;


    TextView txtNoHayClientes,txtSubNohayClientes, lblCantidadClientes,lblSaldoClientes,lblRecaudado;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recaudos);

        mContext = getApplicationContext();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        session = new ManejadorSession(mContext);

        Toolbar toolbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Spinner cmbToolBar = (Spinner)findViewById(R.id.CmbToolbar);

        txtNoHayClientes = (TextView)findViewById(R.id.txtNoHayClientes);
        txtSubNohayClientes = (TextView)findViewById(R.id.txtSubNoHayClientes);
        lblCantidadClientes = (TextView)findViewById(R.id.lblCantidadClientes);
        lblSaldoClientes = (TextView)findViewById(R.id.lblSaldoTotalClientes);
        lblRecaudado = (TextView)findViewById(R.id.lblSaldoRecaudado);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,
                new String[]{"Nombre", "Dirección", "Día corte"});

        adapter.setDropDownViewResource(R.layout.appbar_filter_list);
        cmbToolBar.setAdapter(adapter);

        cmbToolBar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //... Acciones al seleccionar una opción de la lista
                selected = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //... Acciones al no existir ningún elemento seleccionado
            }


        });
        lista = (ListView)findViewById(R.id.lvRecaudos);

//     / Cargar los productos en el Background Thread
        new LoadAllCientes().execute();

        lista.setOnItemClickListener(ListRecaudosActivity.this);
    }

    private ArrayList<RecuadoListItem> Busqueda(int sizetext,String newText, int sel)
    {
        ArrayList<RecuadoListItem> tempcliList = new ArrayList<RecuadoListItem>();

        //BUSQUEDA POR NOMBRE
        if (sel == 0)
        {
            for (RecuadoListItem cli : listRecaudos)
            {
                if (sizetext <= cli.getNombre().length())
                {
                    if (cli.getNombre().toLowerCase().
                            contains(newText.toLowerCase())) {
                        tempcliList.add(new RecuadoListItem(R.drawable.user,
                                cli.getCodigo(),
                                cli.getCodigo_cliente(),
                                cli.getNombre(),
                                cli.getDireccion(),
                                cli.getBarrio(),
                                cli.getLinea(),
                                cli.getSaldo_linea(),
                                cli.getRecibo_venta(),
                                cli.getFecha_venta(),
                                cli.getCodigo_vendedor(),
                                cli.getCodigo_cobrador(),
                                cli.getPrecio_venta(),
                                cli.getObservacion_recaudo(),
                                cli.getValor_pagado(),
                                cli.getRecibo_generado(),
                                cli.getFecha_cobro(),
                                cli.getFecha_export(),
                                cli.getNombre_vendedor(),
                                cli.getNombre_cobrador(),
                                cli.getDia_corte(),
                                cli.getNum_lineas(),
                                cli.getCodigo_venta(),
                                cli.getDias_cobro()
                        ));
                    }
                }
            }
        }

        //BUSQUEDA POR DIRECCIÓN
        else if (sel == 1)
        {
            for (RecuadoListItem cli : listRecaudos)
            {
                if (sizetext <= cli.getDireccion().length())
                {
                    if (cli.getDireccion().toLowerCase().
                            contains(newText.toLowerCase())) {
                        tempcliList.add(new RecuadoListItem(R.drawable.user,
                                cli.getCodigo(),
                                cli.getCodigo_cliente(),
                                cli.getNombre(),
                                cli.getDireccion(),
                                cli.getBarrio(),
                                cli.getLinea(),
                                cli.getSaldo_linea(),
                                cli.getRecibo_venta(),
                                cli.getFecha_venta(),
                                cli.getCodigo_vendedor(),
                                cli.getCodigo_cobrador(),
                                cli.getPrecio_venta(),
                                cli.getObservacion_recaudo(),
                                cli.getValor_pagado(),
                                cli.getRecibo_generado(),
                                cli.getFecha_cobro(),
                                cli.getFecha_export(),
                                cli.getNombre_vendedor(),
                                cli.getNombre_cobrador(),
                                cli.getDia_corte(),
                                cli.getNum_lineas(),
                                cli.getCodigo_venta(),
                                cli.getDias_cobro()));
                    }
                }
            }
        }

        //BUSQUEDA POR CORTE
        else if (sel == 2)
        {
            for (RecuadoListItem cli : listRecaudos)
            {
                if (sizetext <= cli.getNombre().length())
                {
                    if (cli.getDia_corte() == Integer.parseInt(newText)) {
                        tempcliList.add(new RecuadoListItem(R.drawable.user,
                                cli.getCodigo(),
                                cli.getCodigo_cliente(),
                                cli.getNombre(),
                                cli.getDireccion(),
                                cli.getBarrio(),
                                cli.getLinea(),
                                cli.getSaldo_linea(),
                                cli.getRecibo_venta(),
                                cli.getFecha_venta(),
                                cli.getCodigo_vendedor(),
                                cli.getCodigo_cobrador(),
                                cli.getPrecio_venta(),
                                cli.getObservacion_recaudo(),
                                cli.getValor_pagado(),
                                cli.getRecibo_generado(),
                                cli.getFecha_cobro(),
                                cli.getFecha_export(),
                                cli.getNombre_vendedor(),
                                cli.getNombre_cobrador(),
                                cli.getDia_corte(),
                                cli.getNum_lineas(),
                                cli.getCodigo_venta(),
                                cli.getDias_cobro()));
                    }
                }
            }
        }
        return tempcliList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == R.id.action_print_report)
        {
            //IMPRIMIR
            if (session.GetPrintEnabled()) {
                if (session.GetPrinterMac() != null) {

                    if (mBluetoothAdapter.isEnabled()) {
                        Toast.makeText(mContext, "Imprimiendo reporte...!", Toast.LENGTH_LONG).show();
                        try {
                            SendData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Toast.makeText(mContext, "El bluetooth esta desactivado...!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(mContext, "No hay impresora asociada....!", Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(mContext, "La opción para imprimir no esta habilitada..!", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //FORMATO DE IMPRESION
    public void SendData() throws IOException
    {
        try {
            //CALCULO DE PRECIOS
//            if (!txtValorPagar.getText().equals("")) {

            //CUADRANDO TAMAÑO DE CARACTERES


            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            int clientes_visitados = 0;
            for (RecuadoListItem rli: listRecaudos)
            {
                if (rli.getValor_pagado()>0)
                {
                    clientes_visitados++;
                }
            }

            msg = "********** SERVIRURAL **********";
            msg += "\n";
            msg += "================================";
            msg += "\n";
            msg += "------------------------------";
            msg += "\n";
            msg += "          EN ALIANZA";
            msg += "\n";
            msg +=   "       ESTRATEGICA CON";
            msg += "\n";
            msg += "  COMUNICACIONES JULIAN PEREZ";
            msg += "\n";
            msg += "------------------------------";
            msg += "\n";
            msg += "       Atencion al cliente";
            msg += "\n";
            msg += "     375 4814 - 310 839 3917 ";
            msg += "\n";
            msg += "   315-514-8448 - 318-836-9784   ";
            msg += "\n";
            msg += "     Suspension y Reposicion";
            msg += "\n";
            msg += "          320-609-5912";
            msg += "\n";
            msg += "===============================";
            msg += "\n";
            msg += "===============================";
            msg += "\n";
            msg +=
                    "Fecha: "+currentDateTimeString;
            msg += "\n";
            msg += "-------------------------------";
            msg += "\n";
            msg += "    REPORTE DE RECAUDO";
            msg += "\n";
            msg += "-------------------------------";
            msg += "\n";
            msg += "Clientes asignados: "+lblCantidadClientes.getText().toString()+"";
            msg += "\n";
            msg += "Clientes visitados.: "+clientes_visitados;
            msg += "\n";
            msg += "Recaudo total:  $ " + String.format("%,d", Integer.parseInt(lblSaldoClientes.getText().toString()));
            msg += "\n";
            msg += "Recaudo actual: $ " + String.format("%,d", Integer.parseInt(lblRecaudado.getText().toString()));
            msg += "\n";
            msg += "-";
            msg += "\n";
            msg += "\n";
            msg += "\n";
            msg += "\n";
            msg += "Firma: " + "________________________";
            msg += "\n";
            msg += "\n";
            msg += "Mensajero responsable: "+session.GetUserName();
            msg += "\n";
            msg += "\n";
            msg += "** Gracias por su tiempo ! **";
            msg += "\n";
            msg += "\n";
            msg += "\n";

            printer = new Impresion(mContext,ListRecaudosActivity.this);
            printer.Imprimir(msg);

        }
        catch (NullPointerException np)
        {
            np.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_recaudos, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager sManager = (SearchManager)
                ListRecaudosActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView sView = null;
        if(searchItem != null)
        {
            sView = (SearchView) searchItem.getActionView();
        }
        if(sView != null)
        {
            sView.setSearchableInfo(sManager.getSearchableInfo
                    (ListRecaudosActivity.this.getComponentName()));
        }

        assert sView != null;

        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    int sizetext = newText.length();
                    ArrayList<RecuadoListItem> tempcliList = new ArrayList<RecuadoListItem>();
                    switch (selected) {
                        case 0:
                            tempcliList = Busqueda(sizetext, newText, 0);
                            break;
                        case 1:
                            tempcliList = Busqueda(sizetext, newText, 1);
                            break;
                        case 2:
                            tempcliList = Busqueda(sizetext, newText, 2);
                            break;
                    }


                    listAdapter = new ListRecaudosAdapter(ListRecaudosActivity.this, tempcliList);
                    lista.setAdapter(listAdapter);
                    lista.setOnItemClickListener(ListRecaudosActivity.this);
                    listAdapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        RecuadoListItem selectedCliente = listRecaudos.get(position);

        Intent recuados = new Intent(ListRecaudosActivity.this,RecaudosActivity.class);
        recuados.putExtra("cliente", String.valueOf(id));
//        Toast.makeText(this,"El usuario seleccionado es : "+id,Toast.LENGTH_LONG).show();
        startActivity(recuados);
    }

    class LoadAllCientes extends AsyncTask<String, String, String>
    {
        DaoRecaudo daoRec = new DaoRecaudo();
        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListRecaudosActivity.this);
            pDialog.setMessage("Cargando clientes de cobro lineas, Por favor espere...");
            pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

        protected String doInBackground(String... args) {

            ManejadorSession session = new ManejadorSession(mContext);
            listRecaudos = daoRec.GetClientesRecaudo(session.GetUserCode());
            return null;
        }

        protected void onPostExecute(String file_url) {

                // updating UI from Background Thread
                runOnUiThread(new Runnable() {
                    public void run() {
                        /**
                         * Updating parsed JSON data into ListView
                         * */
                        ListRecaudosAdapter listAdapter = new
                                ListRecaudosAdapter(ListRecaudosActivity.this, listRecaudos);
                        lista.setAdapter(listAdapter);

                        if (listRecaudos.size()==0)
                        {
                            txtNoHayClientes.setVisibility(View.VISIBLE);
                            txtSubNohayClientes.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            txtNoHayClientes.setVisibility(View.INVISIBLE);
                            txtSubNohayClientes.setVisibility(View.INVISIBLE);

                            //MOSTRAR CANTIDAD DE CLIENTES POR COBRAR
                            lblCantidadClientes.setText(String.valueOf(listRecaudos.size()));

                            //MOSTRAR SALDO POR COBRAR
                            long total = 0;
                            for (int i = 0; i < listRecaudos.size(); i++)
                            {
                                   total += listRecaudos.get(i).getSaldo_linea();
                            }
                            lblSaldoClientes.setText(String.valueOf(total));

                            //MOSTRAR SALDO RECAUDADO
                            long recaudado = 0;
                            for (int i = 0; i < listRecaudos.size(); i++)
                            {
                                recaudado += listRecaudos.get(i).getValor_pagado();
                            }
                            lblRecaudado.setText(String.valueOf(recaudado));


                        }
                        pDialog.dismiss();

                    }
                });
        }
    }
}

