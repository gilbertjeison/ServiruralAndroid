package CustomAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.meysi.desarrollo.servirural_android.R;

import java.util.List;

import Objetos.RecaudoSoatListItem;
import Objetos.RecuadoListItem;

import static CustomAdapters.ListRecaudosAdapter.*;

/**
 * Created by Desarrollo on 21/12/2015.
 */
public class ListRecaudosSoatAdapter extends BaseAdapter {

    protected List<RecaudoSoatListItem> listRecaudos;
    LayoutInflater inflater;
    Context context;
    private int lastPosition = -1;

    public ListRecaudosSoatAdapter(Context context_, List<RecaudoSoatListItem> listRecaudos)
    {
        this.context = context_;
        this.inflater = LayoutInflater.from(context);
        this.listRecaudos = listRecaudos;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderSoat holder ;

        if (convertView == null)
        {
            holder = new ViewHolderSoat();
            convertView = this.inflater.inflate(R.layout.layout_list_item, parent, false);
            holder.txtCliente = (TextView) convertView.findViewById(R.id.txt_cliente);
            holder.txtLinea = (TextView) convertView.findViewById(R.id.txt_linea);
            holder.txtDireccion = (TextView) convertView.findViewById(R.id.txt_direccion);
            holder.txtSaldo = (TextView) convertView.findViewById(R.id.txt_saldo);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolderSoat) convertView.getTag();
        }

        RecaudoSoatListItem recaudo = listRecaudos.get(position);

        if (recaudo.getValor_pagado() > 0) {
            convertView.setBackgroundColor(Color.YELLOW);
        }
        else
        {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.txtCliente.setText(recaudo.getNombre());
        holder.txtLinea.setText(String.valueOf(recaudo.getNum_soats()));
        holder.txtDireccion.setText(recaudo.getDireccion());
        holder.txtSaldo.setText("$ " + String.format("%,d", Long.parseLong(String.valueOf(recaudo.getSaldo_soat()))));

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        convertView.startAnimation(animation);
        lastPosition = position;

        return convertView;
    }

    public static class ViewHolderSoat {
        TextView txtCliente;
        TextView txtLinea;
        TextView txtDireccion;
        TextView txtSaldo;
    }
}
