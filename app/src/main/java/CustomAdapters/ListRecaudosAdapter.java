package CustomAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meysi.desarrollo.servirural_android.R;

import java.security.BasicPermission;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Objetos.RecuadoListItem;

/**
 * Created by Desarrollo on 04/09/2015.
 */
public class ListRecaudosAdapter extends BaseAdapter {

    Context context;
    private int lastPosition = -1;

    protected List<RecuadoListItem> listRecaudos;
    LayoutInflater inflater;

    public ListRecaudosAdapter(Context context, List<RecuadoListItem> listRecaudos)
    {
        this.listRecaudos = listRecaudos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }



    @Override
    public int getCount() {
        return listRecaudos.size();
    }

    @Override
    public RecuadoListItem getItem(int position) {
        return listRecaudos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listRecaudos.get(position).getCodigo_cliente();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.layout_list_item, parent, false);
            holder.txtCliente = (TextView) convertView.findViewById(R.id.txt_cliente);
            holder.txtLinea = (TextView) convertView.findViewById(R.id.txt_linea);
            holder.txtDireccion = (TextView) convertView.findViewById(R.id.txt_direccion);
            holder.txtSaldo = (TextView) convertView.findViewById(R.id.txt_saldo);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RecuadoListItem recaudo = listRecaudos.get(position);

        if (getItem(position).getValor_pagado() > 0) {
            convertView.setBackgroundColor(Color.YELLOW);
        }
        else
        {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.txtCliente.setText(recaudo.getNombre());
        holder.txtLinea.setText(String.valueOf(recaudo.getNum_lineas()));
        holder.txtDireccion.setText(recaudo.getDireccion());
        holder.txtSaldo.setText("$ " + String.format("%,d", Long.parseLong(String.valueOf(recaudo.getSaldo_linea()))));




        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition = position;
        return convertView;
    }



    public class ViewHolder {
        TextView txtCliente;
        TextView txtLinea;
        TextView txtDireccion;
        TextView txtSaldo;
        ImageView imgCliente;
    }


    private void setRowColor(View view, Long var) {
        if (var > 0) {
            view.setBackgroundColor(Color.rgb(255, 0, 0));
        }
    }
}
