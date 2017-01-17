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

import CustomAdapters.RecargaAdpater;
import Objetos.Impresion;
import Objetos.RecargaItem;
import Objetos.Transaccion;
import ObjetosAccesoDatos.DaoRecargas;
import ObjetosAccesoDatos.DaoTransacciones;
import Session.ManejadorSession;

public class RecargasActivity extends AppCompatActivity {

    int cliente_seleccionado;
    DaoRecargas daoRec = new DaoRecargas();
    DaoTransacciones daoTrans = new DaoTransacciones();
    List<RecargaItem> ClienteSelected = new ArrayList<>();
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
        setContentView(R.layout.activity_recargas);

        mContext = getApplicationContext();
        session = new ManejadorSession(mContext);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        TextView noteView = (TextView) findViewById(R.id.lblVerDetallesRec);
        Linkify.addLinks(noteView, Linkify.ALL);

        //INICIALIZAR CONTROLES DE LAYOUT
        txtCliente = (EditText)findViewById(R.id.txtClienteRRec);
        txtDireccion = (EditText)findViewById(R.id.txtDireccionRRec);
        txtBarrio = (EditText)findViewById(R.id.txtBarrioRRec);
        txtNumLineas = (EditText)findViewById(R.id.txtNumRec);
        txtSaldoTotal = (EditText)findViewById(R.id.txtSaldoTotalRRec);
        txtAPagar = (EditText)findViewById(R.id.txtValorPagarRRec);
        txtObservaciones = (EditText)findViewById(R.id.txtObservacionesRRec);
        btnPagar = (Button)findViewById(R.id.btnPagarRec);

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
            pDialog = new ProgressDialog(RecargasActivity.this);
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
                        for (RecargaItem rli:ClienteSelected)
                        {
                            sum += rli.getSaldo_rec();
                        }

                        txtSaldoTotal.setText(" $ " + String.format("%,d",sum));
                    }
                    else
                    {
                        Toast.makeText(RecargasActivity.this,"Por favor vuelva a seleccionar el cliente...!"+ClienteSelected.size(),Toast.LENGTH_SHORT).show();
                    }
                    // dismiss the dialog after getting all products
                    pDialog.dismiss();
                }
            });
        }}

    public void MostrarModal()
    {
        //PREPARAR INTERFAZ PARA MOSTRAR LINEAS
        pDialog = new Dialog(RecargasActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.lista_lineas_cliente, null);
        ListView lvLineas = (ListView)view.findViewById(R.id.lvLineas);
        Button btnCancelarDL = (Button)view.findViewById(R.id.btnCancelarDialogL);
        final Button btnguardarDL = (Button)view.findViewById(R.id.btnGuardarDialog);

        //LLENAR LISTA DELINEAS
        RecargaAdpater lla = new RecargaAdpater
                (RecargasActivity.this,R.layout.linea_row, ((ArrayList) ClienteSelected));
        lvLineas.setAdapter(lla);

        //VARIABLE PARA COMPROBAR SI ALGUNA LINEA NO
        //HA SIDO REGISTRADA EN EL SERVIDOR
        boolean alguno_debe = false;

        for (RecargaItem rli: ClienteSelected)
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
                                        new AlertDialog.Builder(RecargasActivity.this);

                                builder.setMessage("No hay impresora asociada, asocie impresora antes de efectuar recaudo..!")
                                        .setTitle("Recaudo").setPositiveButton("Aceptar", null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        } else {
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(RecargasActivity.this);

                            builder.setMessage("Activa el bluethoot para poder imprimir..!")
                                    .setTitle("Recaudo")
                                    .setPositiveButton("Aceptar", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } else {
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(RecargasActivity.this);

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
                                ClienteSelected.get(i).getLinea(),
                                ClienteSelected.get(i).getSaldo_rec(),
                                ClienteSelected.get(i).getValor_pagado(),
                                ClienteSelected.get(i).getSaldo_rec() -
                                        ClienteSelected.get(i).getValor_pagado(),
                                ClienteSelected.get(i).getPrecio_venta());

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
            msg += "    INFORMACION DE RECARGAS";
            msg += "\n";
            msg += "-------------------------------";
            msg += "\n";
            for (CalculoCliente ccf:listValores)
            {
                msg += "Linea: "+ccf.getLinea()+"";
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
            printer = new Impresion(mContext,RecargasActivity.this);

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
            pDialog = new ProgressDialog(RecargasActivity.this);
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
            for (RecargaItem rli :ClienteSelected)
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
                        if (daoTrans.CreateTransactionRecarga(transaccion)) {
//                            //REGISTRAR EN RECAUDOS
                            daoRec.UpdateRecaudoRecarga(rli);
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
                Toast.makeText(RecargasActivity.this,
                        "Recaudo actualizado correctamente..!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(RecargasActivity.this, "Recaudo no realizado a la linea: "
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
        private String linea;
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

        public CalculoCliente(long codigo, String linea, long debe, long paga, long nuevo_saldo, long precio_compra) {
            this.codigo = codigo;
            this.linea = linea;
            this.debe = debe;
            this.paga = paga;
            this.nuevo_saldo = nuevo_saldo;
            this.precio_compra = precio_compra;
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

        public String getLinea() {
            return linea;
        }

        public void setLinea(String linea) {
            this.linea = linea;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }
    }
}
