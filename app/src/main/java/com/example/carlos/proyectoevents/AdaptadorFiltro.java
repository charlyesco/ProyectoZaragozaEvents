package com.example.carlos.proyectoevents;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorFiltro extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Evento>listaEventos;
    ArrayList<Evento> origi;


    public AdaptadorFiltro(Context c,ArrayList<Evento>listaEventos){
        super();
        this.context=c;
        this.listaEventos=listaEventos;

    }


    @Override
    public int getCount() {
        return listaEventos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaEventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if(view==null){


            view=  LayoutInflater.from(context).inflate(R.layout.item,null);
            holder=new ViewHolder();
            holder.titulo=view.findViewById(R.id.item_tv_title);
            holder.tema=view.findViewById(R.id.item_tv_theme);
            holder.lugar=view.findViewById(R.id.item_tv_lugar);
            // holder.icon=view.findViewById(R.id.item_image);

            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.titulo.setText(listaEventos.get(position).getTitleEvent());
        holder.tema.setText(listaEventos.get(position).getTitleCategory());
        holder.lugar.setText(listaEventos.get(position).getAddres());

        return view;


    }
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Evento> results = new ArrayList<Evento>();
                if (origi == null)
                    origi = listaEventos;
                if (constraint != null) {
                    if (origi != null && origi.size() > 0) {
                        for (final Evento g : origi) {
                            if (g.getTitleEvent().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                listaEventos = (ArrayList<Evento>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder{
        //   TextView icon;
        TextView titulo;
        TextView tema;
        TextView lugar;
    }
}
