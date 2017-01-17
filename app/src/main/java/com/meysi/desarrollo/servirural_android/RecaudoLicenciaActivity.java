package com.meysi.desarrollo.servirural_android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import CustomAdapters.ListLicenciaAdapter;
import Objetos.Impresion;
import Objetos.RecaudoLicenciaItem;
import Objetos.Transaccion;
import ObjetosAccesoDatos.DaoRecaudoLic;
import ObjetosAccesoDatos.DaoTransacciones;
import Session.ManejadorSession;

public class RecaudoLicenciaActivity extends AppCompatActivity {

    int cliente_seleccionado;
    DaoRecaudoLic daoRec = new DaoRecaudoLic();
    DaoTransacciones daoTrans = new DaoTransacciones();
    List<RecaudoLicenciaItem> ClienteSelected = new ArrayList<>();
    long num_recibo = 0;

    EditText txtCliente;
    EditText txtDireccion;
    EditText txtBarrio;
    EditText txtNumLineas;
    EditText txtSaldoTotal;
    EditText txtAPagar;
    EditText txtObservaciones;
    Button btnPagar;

    //LECTURA DE ARCHIVOS
    BluetoothAdapter mBluetoothAdapter;
    Impresion printer;
    Context mContext;

    ManejadorSession session;
    Dialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recaudo_licencia);

        mContext = getApplicationContext();
        session = new ManejadorSession(mContext);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        TextView noteView = (TextView) findViewById(R.id.lblVerDetallesLic);
        Linkify.addLinks(noteView, Linkify.ALL);

        //INICIALIZAR CONTROLES DE LAYOUT
        txtCliente = (EditText)findViewById(R.id.txtClienteRLic);
        txtDireccion = (EditText)findViewById(R.id.txtDireccionRLic);
        txtBarrio = (EditText)findViewById(R.id.txtBarrioRLic);
        txtNumLineas = (EditText)findViewById(R.id.txtNumLic);
        txtSaldoTotal = (EditText)findViewById(R.id.txtSaldoTotalRLic);
        txtAPagar = (EditText)findViewById(R.id.txtValorPagarRLic);
        txtObservaciones = (EditText)findViewById(R.id.txtObservacionesRLic);
        btnPagar = (Button)findViewById(R.id.btnPagarLic);

        txtCliente.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtBarrio.setEnabled(false);
        txtNumLineas.setEnabled(false);
        txtSaldoTotal.setEnabled(false);

        //EXTRAER EL CÓDIGO DE USUARIO QUE FUE SELECCIONADO
        //EN LA VISTA ANTERIOR
        Bundle data = getIntent().getExtras();
        cliente_seleccionado =  Integer.parseInt(data.getString("cliente"));

        android.util.Log.d("cliente_seleccionado", "" + cliente_seleccionado);

        txtAPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarModal();
            }
        });

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        new LoadCiente().execute();
    }

    class LoadCiente extends AsyncTask<String, String, String> {

        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RecaudoLicenciaActivity.this);
            pDialog.setMessage("Cargando información del cliente, por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            ClienteSelected = daoRec.GetClientRecaudo(String.valueOf(cliente_seleccionado));
            return null;
        }

        protected void onPostExecute(String file_url) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable()
            {
                public void run()
                {
                    //CARGAR LOS CONTROLES CON LOS DATOS BASICOS
                    if (ClienteSelected.size()>0)
                    {
                        txtCliente.setText(" "+ClienteSelected.get(0).getNombre());
                        txtDireccion.setText(" "+ClienteSelected.get(0).getDireccion());
                        txtBarrio.setText(" "+ClienteSelected.get(0).getBarrio());
                        txtNumLineas.setText(""+ClienteSelected.size());

                        int sum = 0;
                        for (RecaudoLicenciaItem rli:ClienteSelected)
                        {
                            sum += rli.getSaldo_lic();
                        }

                        txtSaldoTotal.setText(" $ " + String.format("%,d",sum));
                    }
                    else
                    {
                        Toast.makeText(RecaudoLicenciaActivity.this,"Por favor vuelva a seleccionar el cliente...!"+ClienteSelected.size(),Toast.LENGTH_SHORT).show();
                    }
                    // dismiss the dialog after getting all products
                    pDialog.dismiss();
                }
            });
        }}

    public void MostrarModal()
    {
        //PREPARAR INTERFAZ PARA MOSTRAR LINEAS
        pDialog = new Dialog(RecaudoLicenciaActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.lista_lineas_cliente, null);
        ListView lvLineas = (ListView)view.findViewById(R.id.lvLineas);
        Button btnCancelarDL = (Button)view.findViewById(R.id.btnCancelarDialogL);
        final Button btnguardarDL = (Button)view.findViewById(R.id.btnGuardarDialog);

        //LLENAR LISTA DELINEAS
        ListLicenciaAdapter lla = new ListLicenciaAdapter
                (RecaudoLicenciaActivity.this,R.layout.linea_row, ((ArrayList) ClienteSelected));
        lvLineas.setAdapter(lla);

        //VARIABLE PARA COMPROBAR SI ALGUNA LINEA NO
        //HA SIDO REGISTRADA EN EL SERVIDOR
        boolean alguno_debe = false;

        for (RecaudoLicenciaItem rli: ClienteSelected)
        {
            if (rli.getValor_pagado()>0)
            {
                rli.setCobrado(true);
                alguno_debe = false;
            }
            else if (rli.getValor_pagado() == 0)
            {
                alguno_debe = true;
                rli.setCobrado(false);
            }
        }

        if (alguno_debe)
        {
            btnguardarDL.setText("Aceptar");
        }
        else
        {
            btnguardarDL.setText("Imprimir");
        }

        pDialog.setTitle("Detalles de soats");
        pDialog.setContentView(view);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);

        pDialog.show();

        btnCancelarDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.dismiss();
            }
        });

        btnguardarDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnguardarDL.getText().equals("Aceptar"))
                {
                    //IMPRIMIR
                    if (session.GetPrintEnabled())
                    {
                        if (session.GetPrinterMac() != null) {

                            if (mBluetoothAdapter.isEnabled()) {

                                try {
                                    SendData();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //ACTUALIZAR SALDO EN SERVIDOR
                                new SaveRecaudo().execute
                                        (txtObservaciones.getText().toString());
                            } else {
                                final AlertDialog.Builder builder =
                                        new AlertDialog.Builder(RecaudoLicenciaActivity.this);

                                builder.setMessage("No hay impresora asociada, asocie impresora antes de efectuar recaudo..!")
                                        .setTitle("Recaudo").setPositiveButton("Aceptar", null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        } else {
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(RecaudoLicenciaActivity.this);

                            builder.setMessage("Activa el bluethoot para poder imprimir..!")
                                    .setTitle("Recaudo")
                                    .setPositiveButton("Aceptar", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } else {
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(RecaudoLicenciaActivity.this);

                        builder.setMessage("La impresión en sitio no esta habilitada, recaudar de todos modos ?").setTitle("Recaudo")
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new SaveRecaudo().execute
                                                (txtObservaciones.getText().toString());
                                    }
                                }).setNegativeButton("No", null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
                else
                {
                    //IMPRIMIR
                    if (session.GetPrintEnabled())
                    {
                        if (session.GetPrinterMac() != null)
                        {
                            if (mBluetoothAdapter.isEnabled())
                            {
                                Toast.makeText(mContext,"Imprimiendo...",Toast.LENGTH_LONG).show();
                                try {
                                    SendData();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                pDialog.dismiss();
            }

        });
    }

    //FORMATO DE IMPRESION
    private void SendData() throws IOException
    {
        try {
            //CALCULO DE PRECIOS
            List<CalculoCliente> listValores = new ArrayList<>();
            for (int i =0; i<ClienteSelected.size(); i++)
            {
                CalculoCliente cc =
                        new CalculoCliente(ClienteSelected.get(i).getCodigo(),
                                ClienteSelected.get(i).getSaldo_lic(),
                                ClienteSelected.get(i).getSaldo_lic() -
                                        ClienteSelected.get(i).getValor_pagado(),
                                ClienteSelected.get(i).getValor_pagado(),
                                ClienteSelected.get(i).getCategoria(),
                                ClienteSelected.get(i).getPrecio_venta(),
                                ClienteSelected.get(i).getTipo_vehiculo());

                listValores.add(cc);
            }

            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            Calendar c = Calendar.getInstance();

            String
                    msg = "********** SERVIRURAL **********";
            msg += "\n";
            msg += "================================";
            msg += "\n";
            msg += "Recibo N  "+num_recibo;
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
            msg += "Cliente: " + txtCliente.getText().toString();
            msg += "\n";
            msg += "Saldo total: " +"$ " + String.format("%,d", Integer.parseInt(txtSaldoTotal.getText()
                    .toString().replace("$ ","").replace(",","").trim()));
            msg += "\n";
            msg += "    INFORMACION DE LICENCIAS";
            msg += "\n";
            msg += "-------------------------------";
            msg += "\n";
            for (CalculoCliente ccf:listValores)
            {
                msg += "Categoria licencia: "+ccf.getCategoria()+"";
                msg += "\n";
                msg += "Tipo vehiculo: "+ccf.getTipo_vehiculo()+"";
                msg += "\n";
                msg += "Precio compra:$ " + String.format("%,d", Integer.parseInt(String.valueOf(ccf.getPrecio_compra())));
                msg += "\n";
                msg += "Saldo ant:    $ " + String.format("%,d", Integer.parseInt(String.valueOf(ccf.getDebe())));
                msg += "\n";
                msg += "Abono:        $ " + String.format("%,d", Integer.parseInt(String.valueOf(ccf.getPaga())));
                msg += "\n";
                msg += "Saldo actual: $ " + String.format("%,d", Integer.parseInt(String.valueOf(ccf.getNuevo_saldo())));
                msg += "\n";
                msg += "-";
                msg += "\n";
            }
            msg += "\n";
            msg += "-------------------------------";
            msg += "\n";
            msg += "\n";
            msg += "Firma: " + "________________________";
            msg += "\n";
            msg += "\n";
            msg += "Mensajero responsable: "+ClienteSelected.get(0).getNombre_cobrador();
            msg += "\n";
            msg += "\n";
            msg += "** Gracias por su tiempo ! **";
            msg += "\n";
            msg += "\n";
            msg += "\n";


            //Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
            printer = new Impresion(mContext,RecaudoLicenciaActivity.this);

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

    class SaveRecaudo extends AsyncTask<String, String, String>
    {
        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RecaudoLicenciaActivity.this);
            pDialog.setMessage("Efectuando pago en el servidor, por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            Log.i("Valores a pagar:", "" + ClienteSelected.get(0).getValor_pagado());
        }

        @Override
        protected String doInBackground(String... params) {
            //REALIZAR TRANSACCIONES POR CADA UNO DE LOS
            //RECAUDOS
            for (RecaudoLicenciaItem rli :ClienteSelected)
            {
                rli.setObservacion_recaudo(params[0]);
                if (!rli.isCobrado())
                {
                    Transaccion transaccion = new Transaccion();
                    transaccion.setCodigo_venta(rli.getCodigo_venta());
                    transaccion.setTipo_transaccion(1);
                    transaccion.setPrecio(rli.getValor_pagado());
                    transaccion.setObservacion(rli.getObservacion_recaudo());

                    //REGISTRAR TRANSACCION
                    if (transaccion.getPrecio()>0) {
                        if (daoTrans.CreateTransaction(transaccion)) {
                            //REGISTRAR EN RECAUDOS
                            daoRec.UpdateRecaudo(rli);
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                Toast.makeText(RecaudoLicenciaActivity.this,
                        "Recaudo actualizado correctamente..!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(RecaudoLicenciaActivity.this, "Recaudo no realizado a la linea: "
                        + s, Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    }

    class CalculoCliente
    {
        private long codigo;
        private String tipo_vehiculo;
        private String categoria;
        private long debe;
        private long paga;
        private long nuevo_saldo;
        private long precio_compra;

        public CalculoCliente(long codigo, long debe, long nuevo_saldo, long paga, String cat,
                              long precio_compra, String tipo_vehiculo) {
            this.codigo = codigo;
            this.debe = debe;
            this.nuevo_saldo = nuevo_saldo;
            this.paga = paga;
            this.precio_compra = precio_compra;
            this.categoria = cat;
            this.tipo_vehiculo = tipo_vehiculo;
        }

        public String getTipo_vehiculo() {
            return tipo_vehiculo;
        }

        public void setTipo_vehiculo(String tipo_vehiculo) {
            this.tipo_vehiculo = tipo_vehiculo;
        }

        public long getCodigo() {
            return codigo;
        }

        public void setCodigo(long codigo) {
            this.codigo = codigo;
        }

        public long getDebe() {
            return debe;
        }

        public void setDebe(long debe) {
            this.debe = debe;
        }

        public long getNuevo_saldo() {
            return nuevo_saldo;
        }

        public void setNuevo_saldo(long nuevo_saldo) {
            this.nuevo_saldo = nuevo_saldo;
        }

        public long getPaga() {
            return paga;
        }

        public void setPaga(long paga) {
            this.paga = paga;
        }

        public long getPrecio_compra() {
            return precio_compra;
        }

        public void setPrecio_compra(long precio_compra) {
            this.precio_compra = precio_compra;
        }


        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }
    }
}
