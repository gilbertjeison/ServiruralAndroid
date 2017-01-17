package CustomAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.meysi.desarrollo.servirural_android.R;

import java.util.List;

import Objetos.RecaudoLicenciaItem;

/**
 * Created by Desarrollo on 22/12/2015.
 */
public class ListRecaudoLicenciaAdapter extends BaseAdapter {

    protected List<RecaudoLicenciaItem> listRecaudos;
    LayoutInflater inflater;
    Context context;
    private int lastPosition = -1;

    public ListRecaudoLicenciaAdapter(Context context,List<RecaudoLicenciaItem> listRecaudos) {
        this.listRecaudos = listRecaudos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return listRecaudos.size();
    }

    @Override
    public Object getItem(int position) {
        return listRecaudos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listRecaudos.get(position).getCodigo_cliente();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolderLic holder ;

        if (convertView == null)
        {
            holder = new ViewHolderLic();
            convertView = this.inflater.inflate(R.layout.layout_list_item, parent, false);
            holder.txtCliente = (TextView) convertView.findViewById(R.id.txt_cliente);
            holder.txtLinea = (TextView) convertView.findViewById(R.id.txt_linea);
            holder.txtDireccion = (TextView) convertView.findViewById(R.id.txt_direccion);
            holder.txtSaldo = (TextView) convertView.findViewById(R.id.txt_saldo);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolderLic) convertView.getTag();
        }

        RecaudoLicenciaItem recaudo = listRecaudos.get(position);

        if (recaudo.getValor_pagado() > 0) {
            convertView.setBackgroundColor(Color.YELLOW);
        }
        else
        {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.txtCliente.setText(recaudo.getNombre());
        holder.txtLinea.setText(String.valueOf(recaudo.getNum_lics()));
        holder.txtDireccion.setText(recaudo.getDireccion());
        holder.txtSaldo.setText("$ " + String.format("%,d", Long.parseLong(String.valueOf(recaudo.getSaldo_lic()))));

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition = position;

        return convertView;
    }

    public static class ViewHolderLic {
        TextView txtCliente;
        TextView txtLinea;
        TextView txtDireccion;
        TextView txtSaldo;
    }
}
