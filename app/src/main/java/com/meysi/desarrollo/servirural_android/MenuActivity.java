package com.meysi.desarrollo.servirural_android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import android.widget.Toast;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Objetos.Impresion;
import Objetos.Transa;
import ObjetosAccesoDatos.DaoTransa;
import ObjetosAccesoDatos.DaoUsuario;
import Session.ManejadorSession;
import jxl.Cell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLineas;
    Button btnSoat;
    Button btnLicencias;
    Button btnRecarga;
    Button btnTransa;
    ProgressDialog pDialog;
    DaoTransa daoTran;
    Context mContext;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mContext = getApplicationContext();
        daoTran = new DaoTransa(mContext);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                                               @Override
                                               public boolean onMenuItemClick(MenuItem item) {
                                                   onOptionsItemSelected(item);
                                                   return false;
                                               }
                                           });
        btnLineas = (Button) findViewById(R.id.btnLineas);
        btnSoat = (Button)findViewById(R.id.btnSoat);
        btnLicencias = (Button)findViewById(R.id.btnLic);
        btnRecarga = (Button)findViewById(R.id.btnRecargas);
        btnTransa = (Button)findViewById(R.id.btnTransa);

        btnLineas.setOnClickListener(this);
        btnSoat.setOnClickListener(this);
        btnLicencias.setOnClickListener(this);
        btnRecarga.setOnClickListener(this);
        btnTransa.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLineas:
                CargarMenuLineas();
                break;
            case R.id.btnSoat:
                CargarMenuSoat();
                break;
            case R.id.btnRecargas:
                CargarMenuRec();
                break;
            case R.id.btnLic:
                CargarMenuLic();
                break;
            case R.id.btnTransa:
                CargarMenuTrnas();
                break;

        }
    }

    private void CargarMenuLineas() {
        startActivity(new Intent
                (getApplicationContext(), activity_linea.class));
    }

    private void CargarMenuSoat() {
        startActivity(new Intent
                (getApplicationContext(), LRecaudoSoatActivity.class));
    }

    private void CargarMenuLic() {
        startActivity(new Intent
                (getApplicationContext(), ListRecaudoLicenciaActivity.class));
    }

    private void CargarMenuRec() {
        startActivity(new Intent
                (getApplicationContext(), ListRecargasActivity.class));}

    private void CargarMenuTrnas() {
        startActivity(new Intent
                (getApplicationContext(), TransaccionesActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_configuracion:
                Intent ic = new Intent(getBaseContext(), Configuracion.class);
                startActivity(ic);
                break;
            case R.id.action_cerrar_sesion:
                Intent is = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(is);
                new CerrarSesion().execute();
                break;
            case R.id.action_exportar:
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(MenuActivity.this);

                builder.setMessage("Desea exportar las transacciones ?")
                        .setTitle("Exportación")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new ExportaEncuestaExel().execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.action_delete:
                builder =
                        new AlertDialog.Builder(MenuActivity.this);

                builder.setMessage("Desea eliminar las transacciones almacenadas ?")
                        .setTitle("Eliminar transacciones")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                daoTran.Delete();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                dialog = builder.create();
                dialog.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    class CerrarSesion extends  AsyncTask<Void, Void, Void>
    {
        public ProgressDialog pDialog;

        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(MenuActivity.this);
            pDialog.setMessage("Cerrando sesión...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            DaoUsuario daoUsuario = new DaoUsuario();
            ManejadorSession session = new ManejadorSession(mContext);
            daoUsuario.CloseSession(session.GetUserCode());
            return null;
        }


        protected void onPostExecute(Void d)
        {
            pDialog.dismiss();
            finish();
        }
    }

    //Exporta LECTURAS EN CSV
    public class ExportaEncuestaExel extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MenuActivity.this);
            pDialog.setMessage("Exportando...");
            pDialog.setCancelable(false);
            pDialog.show();

        }


        @Override
        protected Void doInBackground(Void... params) {
            String ruta_folder = Environment.getExternalStorageDirectory() + "/MeysiApp/";
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());



            //se define tipo file el directorio
            File storageDir = new File(ruta_folder);
            if(!storageDir.exists()){
                storageDir.mkdir();
            }

            ArrayList<Transa> lisdata = daoTran.GetTrasa();

            //El nombre del archivo, aca se mete las variables de medidor.
            String LectFile = "Servirural_Transacciones" + timeStamp + ".xls";
            File fileXls = new File(ruta_folder  + LectFile);
            try {
                WritableWorkbook workbook = Workbook.createWorkbook(fileXls);
                WritableSheet sheet_encuesta = workbook.createSheet("Encuesta principal", 0);


                //FORMATO DE ENCABEZADO
                WritableFont times16font =
                        new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false);
                WritableCellFormat time16 = new WritableCellFormat(times16font);

                //FROMATO de CELDAS
                WritableFont times12font =
                        new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false);

                WritableCellFormat time12 = new WritableCellFormat(times12font);


                Cell codigo;
                // ENCABEZADOS LECTURAS
                Label cod = new Label(1, 0, "Código", time16);
                sheet_encuesta.addCell(cod);

                Label equi = new Label(2, 0, "Tipo transacción", time16);
                sheet_encuesta.addCell(equi);

                Label tel = new Label(3, 0, "Linea", time16);
                sheet_encuesta.addCell(tel);

                Label mail = new Label(4, 0, "Precio", time16);
                sheet_encuesta.addCell(mail);

                Label fec = new Label(5, 0, "Fecha", time16);
                sheet_encuesta.addCell(fec);

                Label obs = new Label(6, 0, "Observación", time16);
                sheet_encuesta.addCell(obs);


                int row = 1;
                //Contenido
                for (int i = 0; i < lisdata.size(); i++) {
                    // DATOS
                    WritableCellFormat integerFormat = new WritableCellFormat
                            (NumberFormats.INTEGER);

                    jxl.write.Number codClid = new jxl.write.Number(1, row, lisdata.get(i).getCodigo(), integerFormat);
                    sheet_encuesta.addCell(codClid);

                    Label Dequi = new Label(2, row, lisdata.get(i).getTipo_transaccion(), time12);
                    sheet_encuesta.addCell(Dequi);

                    Label Dtel = new Label(3, row, lisdata.get(i).getLinea(), time12);
                    sheet_encuesta.addCell(Dtel);

                    Label Dmail = new Label(4, row,
                            String.valueOf(lisdata.get(i).getPrecio()), time12);
                    sheet_encuesta.addCell(Dmail);

                    Label fecc = new Label(5, row,
                            String.valueOf(lisdata.get(i).getFecha()), time12);
                    sheet_encuesta.addCell(fecc);

                    Label Dobs = new Label(6, row,
                            String.valueOf(lisdata.get(i).getObservacion()), time12);
                    sheet_encuesta.addCell(Dobs);


                    row++;
                }

                //---------------------------------------------------------

                workbook.write();
                workbook.close();

            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (RowsExceededException e) {
                e.printStackTrace();
            }
            catch (WriteException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(pDialog.isShowing())
                pDialog.dismiss();

            final ViewGroup viewGroup = (ViewGroup) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);

            Snackbar.make(viewGroup, "Exportación correcta...!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }


}

