package com.meysi.desarrollo.servirural_android;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.math.MathContext;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import Objetos.Impresion;
import Objetos.Transa;
import ObjetosAccesoDatos.DaoTransa;
import Session.ManejadorSession;

public class TransaccionesActivity extends AppCompatActivity implements AdapterView.OnClickListener {

    EditText txtLinea;
    EditText txtPrecio;
    EditText txtObservacion;
    Spinner cboTipoTransa;
    Button btnGuardar;

    Context mContext;

    DaoTransa daoTran;
    Transa transa;
    String tipo_selected = "";

    Impresion printer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacciones);

        mContext = getApplicationContext();
        daoTran = new DaoTransa(mContext);

        txtLinea = (EditText)findViewById(R.id.txtLinea);
        txtPrecio = (EditText)findViewById(R.id.txtPrecio);
        txtObservacion = (EditText)findViewById(R.id.txtObservacionT);
        cboTipoTransa = (Spinner)findViewById(R.id.cboTipoTransa);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(this);

        txtPrecio.setText("$"+String.format("%,d", 0));
        Selection.setSelection(txtPrecio.getText(), txtPrecio.getText().length());
        txtPrecio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (!s.toString().contains("$")) {
                        txtPrecio.setText("$"+s);
                        Selection.setSelection(txtPrecio.getText(), txtPrecio.getText().length());
                    }
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                mContext,
                R.layout.appbar_filter_title_t,
                new String[]{"ABONO", "ENTREGA", "DEVOLUCIÓN", "REPOSICIÓN", "CARGUE"});

        adapter.setDropDownViewResource(R.layout.appbar_filter_list);
        cboTipoTransa.setAdapter(adapter);

        cboTipoTransa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        tipo_selected = "ABONO";
                        break;
                    case 1:
                        tipo_selected = "ENTREGA";
                        break;
                    case 2:
                        tipo_selected = "DEVOLUCIÓN";
                        break;
                    case 3:
                        tipo_selected = "REPOSICIÓN";
                        break;
                    case 4:
                        tipo_selected = "CARGUE";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnGuardar:
                GuardarDatos(v);
                break;
        }
    }

    private void GuardarDatos(View v)
    {
        if (!txtLinea.getText().toString().trim().equals("") && txtLinea.getText().toString().length() == 10)
        {
            transa = new Transa();
            transa.setLinea(txtLinea.getText().toString().trim());
            transa.setObservacion(txtObservacion.getText().toString().trim());
            transa.setTipo_transaccion(tipo_selected);
            transa.setPrecio(txtPrecio.getText().toString().trim());

            daoTran.AgregarTransaccion(transa);

            Snackbar.make(v, "Transacción agregada correctamente...!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            try {
                SendData(transa.getLinea(),transa.getPrecio(),transa.getTipo_transaccion());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Limpiar();

        }
        else {
            Snackbar.make(v, "Debe indicar el número de linea correctamente...!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void Limpiar()
    {
        txtLinea.setText("");
        txtObservacion.setText("");
        txtPrecio.setText("");
    }

    //FORMATO DE IMPRESION
    private void SendData(String linea, String Precio, String tipo) throws IOException
    {
        try {
            //CALCULO DE PRECIOS
//            if (!txtValorPagar.getText().equals("")) {
            ManejadorSession session = new ManejadorSession(mContext);


            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

            Calendar c = Calendar.getInstance();

            String
                    msg = "********** SERVIRURAL **********";
            msg += "\n";
            msg += "================================";
            msg += "\n";
            msg += "Recibo N  "+0;
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
            msg += "  INFORMACION DE TRANSACCION";
            msg += "\n";
            msg += "-------------------------------";
            msg += "\n";


            msg += "Transaccion.:   "+tipo+"";
            msg += "\n";
            msg += "Num Linea.:     "+linea+"";
            msg += "\n";
            msg += "Precio:         " + Precio;
            msg += "\n";
            msg += "\n";

            msg += "\n";
            msg += "-------------------------------";
            msg += "\n";
            msg += "Observaciones:";
            msg += "\n";
            msg += "\n";
            msg += txtObservacion.getText().toString();
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



            printer = new Impresion(mContext,TransaccionesActivity.this);
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
}
