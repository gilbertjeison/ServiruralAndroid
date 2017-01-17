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

import java.util.ArrayList;
import java.util.List;

import Objetos.RecargaItem;

/**
 * Created by Desarrollo on 23/12/2015.
 */
public class RecargaAdpater extends ArrayAdapter<RecargaItem> {

    private ArrayList<RecargaItem> lineastList;
    private Context mContext;

    public RecargaAdpater(Context context, int resource, List<RecargaItem> objects) {
        super(context, resource, objects);

        mContext = context;
        this.lineastList = new ArrayList<RecargaItem>();
        this.lineastList.addAll(objects);
    }

    int ref;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        RecargaItem linea = lineastList.get(position);

        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.linea_row,null);
            ref = position;

            TextView lblLineaRow = (TextView)convertView.findViewById(R.id.lblLinea);
            lblLineaRow.setText(String.valueOf(linea.getLinea()));
            TextView lblPrecioRow = (TextView)convertView.findViewById(R.id.lblPrecio);
            lblPrecioRow.setText("$ "+ String.format("%,d", Integer.parseInt(String.valueOf(linea.getSaldo_rec()))));
            final EditText VPagar = (EditText)convertView.findViewById(R.id.txtPAGAR);

            if (linea.getValor_pagado()>0)
            {
                VPagar.setEnabled(false);
            }
            else
            {
                VPagar.setEnabled(true);
            }
            VPagar.setText("$"+String.format("%,d", Integer.parseInt(String.valueOf(linea.getValor_pagado()))));
            Selection.setSelection(VPagar.getText(), VPagar.getText().length());
            VPagar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length()>0)
                    {
                        if(!s.toString().contains("$")){
                            VPagar.setText("$"+s);
                            Selection.setSelection(VPagar.getText(), VPagar.getText().length());
                        }

                        lineastList.get(position).setValor_pagado(Long.parseLong(s.toString().replace("$", "")));
                    }}
            });


        }
        return convertView;
    }
}
