package CustomAdapters;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.meysi.desarrollo.servirural_android.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.*;

import Objetos.CustomLineas;
import Objetos.RecuadoListItem;

/**
 * Created by Desarrollo on 07/09/2015.
 */
public class ListLineasAdapter extends ArrayAdapter<RecuadoListItem> {

    private ArrayList<RecuadoListItem> lineastList;
    private Context mContext;

    @Override
    public RecuadoListItem getItem(int position) {
        return super.getItem(position);
    }

    public ListLineasAdapter(Context context, int resource, ArrayList<RecuadoListItem> objects) {
        super(context, resource, objects);
        mContext = context;
        this.lineastList = new ArrayList<RecuadoListItem>();
        this.lineastList.addAll(objects);
    }

    int ref;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        RecuadoListItem linea = lineastList.get(position);

        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.linea_row,null);
            ref = position;

            TextView lblLineaRow = (TextView)convertView.findViewById(R.id.lblLinea);
            TextView lblCorteRow = (TextView)convertView.findViewById(R.id.lblCorte);
            lblLineaRow.setText(String.valueOf(linea.getLinea()));
            lblCorteRow.setText(String.valueOf(linea.getDia_corte()+" x "+linea.getMinutos()));
            TextView lblPrecioRow = (TextView)convertView.findViewById(R.id.lblPrecio);
            lblPrecioRow.setText("$ "+ String.format("%,d", Integer.parseInt(String.valueOf(linea.getSaldo_linea()))));
            final EditText VPagar = (EditText)convertView.findViewById(R.id.txtPAGAR);

            if (linea.getValor_pagado()>0)
            {
                VPagar.setEnabled(false);
            }
            else
            {
                VPagar.setEnabled(true);
            }
            VPagar.setText(String.format("%,d", Integer.parseInt(String.valueOf(linea.getValor_pagado()))));

            VPagar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.equals(""))
                    {
                        s = "0";
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {


                        lineastList.get(position).setValor_pagado(Long.parseLong((VPagar.getText().toString().equals("")) ? "0":VPagar.getText().toString()));

            }});
        }
        return convertView;
    }
}
