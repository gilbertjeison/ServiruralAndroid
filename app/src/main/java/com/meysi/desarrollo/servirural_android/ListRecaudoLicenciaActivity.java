package com.meysi.desarrollo.servirural_android;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import CustomAdapters.ListRecaudoLicenciaAdapter;
import Objetos.RecaudoLicenciaItem;
import ObjetosAccesoDatos.DaoRecaudoLic;
import Session.ManejadorSession;

public class ListRecaudoLicenciaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Context mContext;
    TextView txtNoHayClientes,txtSubNohayClientes;
    int selected = 0;
    ListView lista;
    List<RecaudoLicenciaItem> listRecaudos = new ArrayList<>();
    DaoRecaudoLic daoRec = new DaoRecaudoLic();
    ListRecaudoLicenciaAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recaudo_licencia);

        mContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Spinner cmbToolbar = (Spinner)findViewById(R.id.CmbToolbar);


        txtNoHayClientes = (TextView)findViewById(R.id.txtNoHayClientesLic);
        txtSubNohayClientes = (TextView)findViewById(R.id.txtSubNoHayClientesLic);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                R.layout.appbar_filter_title,
                new String[]{"Nombre", "Dirección", "Día cobro"});

        adapter.setDropDownViewResource(R.layout.appbar_filter_list);
        cmbToolbar.setAdapter(adapter);

        cmbToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        new LoadAllCientesSoat().execute();
        lista = (ListView)findViewById(R.id.lvRecaudosLic);
        lista.setOnItemClickListener(ListRecaudoLicenciaActivity.this);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        RecaudoLicenciaItem selectedCliente = listRecaudos.get(position);

        Intent recuados = new Intent(ListRecaudoLicenciaActivity.this,RecaudoLicenciaActivity.class);
        recuados.putExtra("cliente", String.valueOf(id));
//        Toast.makeText(this, "El usuario seleccionado es : " + id, Toast.LENGTH_LONG).show();
        startActivity(recuados);
    }

    class LoadAllCientesSoat extends AsyncTask<String, String, String>
    {
        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListRecaudoLicenciaActivity.this);
            pDialog.setMessage("Cargando clientes de cobro licencias, Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            ManejadorSession session = new ManejadorSession(mContext);
            listRecaudos = daoRec.GetClientesRecaudoSoat(session.GetUserCode());
            return null;
        }

        protected void onPostExecute(String file_url) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListRecaudoLicenciaAdapter listAdapter = new
                            ListRecaudoLicenciaAdapter(ListRecaudoLicenciaActivity.this, listRecaudos);

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
                    }
                    pDialog.dismiss();

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_recaudos, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager)ListRecaudoLicenciaActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;

        if (searchItem != null)
        {
            searchView = (SearchView)searchItem.getActionView();
        }

        if (searchView != null)
        {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(ListRecaudoLicenciaActivity.this.getComponentName()));
        }

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                int sizetext = newText.length();
                ArrayList<RecaudoLicenciaItem> tempcliList = new ArrayList<>();
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

                listAdapter = new ListRecaudoLicenciaAdapter(ListRecaudoLicenciaActivity.this, tempcliList);
                lista.setAdapter(listAdapter);
                lista.setOnItemClickListener(ListRecaudoLicenciaActivity.this);
                listAdapter.notifyDataSetChanged();


                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<RecaudoLicenciaItem> Busqueda(int sizetext,String newText, int sel)
    {
        ArrayList<RecaudoLicenciaItem> tempcliList = new ArrayList<>();
        RecaudoLicenciaItem rsli;
        //BUSQUEDA POR NOMBRE
        if (sel == 0)
        {
            for (RecaudoLicenciaItem cli : listRecaudos)
            {
                if (sizetext <= cli.getNombre().length())
                {
                    if (cli.getNombre().toLowerCase().
                            contains(newText.toLowerCase())) {
                        rsli = new RecaudoLicenciaItem();

                        rsli.setCodigo(cli.getCodigo());
                        rsli.setNombre(cli.getNombre());
                        rsli.setDireccion(cli.getDireccion());
                        rsli.setCodigo_cliente(cli.getCodigo_cliente());
                        rsli.setCodigo_venta(cli.getCodigo_venta());
                        rsli.setNum_lics(cli.getNum_lics());
                        rsli.setDias_cobro(cli.getDias_cobro());
                        rsli.setSaldo_lic(cli.getSaldo_lic());
                        rsli.setValor_pagado(cli.getValor_pagado());

                        tempcliList.add(rsli);
                    }
                }
            }
        }

        //BUSQUEDA POR DIRECCIÓN
        else if (sel == 1)
        {
            for (RecaudoLicenciaItem cli : listRecaudos)
            {
                if (sizetext <= cli.getDireccion().length())
                {
                    if (cli.getDireccion().toLowerCase().
                            contains(newText.toLowerCase())) {

                        rsli = new RecaudoLicenciaItem();

                        rsli.setCodigo(cli.getCodigo());
                        rsli.setNombre(cli.getNombre());
                        rsli.setDireccion(cli.getDireccion());
                        rsli.setCodigo_cliente(cli.getCodigo_cliente());
                        rsli.setCodigo_venta(cli.getCodigo_venta());
                        rsli.setNum_lics(cli.getNum_lics());
                        rsli.setDias_cobro(cli.getDias_cobro());
                        rsli.setSaldo_lic(cli.getSaldo_lic());
                        rsli.setValor_pagado(cli.getValor_pagado());

                        tempcliList.add(rsli);
                    }
                }
            }
        }

        //BUSQUEDA POR CORTE
        else if (sel == 2)
        {
            for (RecaudoLicenciaItem cli : listRecaudos)
            {
                if (sizetext <= cli.getNombre().length())
                {
                    if (cli.getDias_cobro().equals(newText)) {
                        rsli = new RecaudoLicenciaItem();

                        rsli.setCodigo(cli.getCodigo());
                        rsli.setNombre(cli.getNombre());
                        rsli.setDireccion(cli.getDireccion());
                        rsli.setCodigo_cliente(cli.getCodigo_cliente());
                        rsli.setCodigo_venta(cli.getCodigo_venta());
                        rsli.setNum_lics(cli.getNum_lics());
                        rsli.setDias_cobro(cli.getDias_cobro());
                        rsli.setSaldo_lic(cli.getSaldo_lic());
                        rsli.setValor_pagado(cli.getValor_pagado());

                        tempcliList.add(rsli);
                    }
                }
            }
        }
        return tempcliList;
    }
}
