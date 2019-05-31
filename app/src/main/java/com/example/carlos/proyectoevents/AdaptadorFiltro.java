package com.example.carlos.proyectoevents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.Normalizer;
import java.util.ArrayList;


public class AdaptadorFiltro extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Evento> listaEventos;
    ArrayList<Evento> origi;


    public AdaptadorFiltro(Context c, ArrayList<Evento> listaEventos) {
        super();
        this.context = c;
        this.listaEventos = listaEventos;

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
    public View getView(final int position, View view, ViewGroup parent) {
         final ViewHolder holder;



        if (view == null) {


            view = LayoutInflater.from(context).inflate(R.layout.item, null);
           holder = new ViewHolder(view);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }





holder.cartel.setImageBitmap(listaEventos.get(position).getImageBmp());

        holder.i.setImageResource(asignarImagen(listaEventos.get(position).getTitleCategory()));
        holder.titulo.setText(listaEventos.get(position).getTitleEvent());
        holder.tema.setText(listaEventos.get(position).getTitleCategory());
        holder.lugar.setText(listaEventos.get(position).getPlace());
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
                            if (NavigationActivity.control == 0) {
                                String comparar = g.getTitleEvent().toLowerCase();
                                String comparar2 = Normalizer.normalize(comparar, Normalizer.Form.NFD);
                                ;
                                comparar2 = comparar2.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

                                if (comparar2.contains(constraint.toString()))
                                    results.add(g);

                            } else if (NavigationActivity.control == 1) {
                                String c = g.getPlace().toLowerCase();
                                String c2 = Normalizer.normalize(c, Normalizer.Form.NFD);
                                c2 = c2.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                                if (c2.contains(constraint.toString()))
                                    results.add(g);
                            } else if (NavigationActivity.control == 2) {
                                String a = g.getTitleCategory().toLowerCase();
                                String b = Normalizer.normalize(a, Normalizer.Form.NFD);
                                b = b.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
                                if (b.contains(constraint.toString()))
                                    results.add(g);
                            }
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

    public static int asignarImagen(String tema) {

        String b = Normalizer.normalize(tema, Normalizer.Form.NFD).toLowerCase();
        b = b.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        int result = R.drawable.cine;
        if (b.equals("conferencias y congresos")) {
            result = R.drawable.conferencias;

        } else if (b.equals("artes plasticas")) {
            result = R.drawable.artes_plasticas;
        } else if (b.equals("musica")) {
            result = R.drawable.musica;
        } else if (b.equals("deporte")) {
            result = R.drawable.deporte;
        } else if (b.equals("ocio y juegos")) {
            result = R.drawable.ocio;
        } else if (b.equals("cine")) {
            result = R.drawable.cine;
        } else if (b.equals("otros")) {
            result = R.drawable.otros;
        } else if (b.equals("gastronomia")) {
            result = R.drawable.gastronomia;
        } else if (b.equals("cursos y talleres")) {
            result = R.drawable.cursos_talleres;
        } else if (b.equals("imagen y sonido")) {
            result = R.drawable.imagen_sonido;
        } else if (b.equals("cine")) {
            result = R.drawable.cine;
        } else if (b.equals("otros")) {
            result = R.drawable.otros;
        } else if (b.equals("visitas turisticas")) {
            result = R.drawable.actividades_turisticas;
        }
        else if (b.equals("ciencia y tecnologia")) {
            result = R.drawable.ciencia;
        }
        else if (b.equals("exposiciones")) {
            result = R.drawable.picture;
        }
        else if (b.equals("actividades vacacionales")) {
            result = R.drawable.vacacionales;
        }
        else if (b.equals("teatro y artes escenicas")) {
            result = R.drawable.teatro;
        }else if (b.equals("turismo")) {
            result = R.drawable.turismo;
        }else if (b.equals("formacion")) {
            result = R.drawable.formacion;
        }else if (b.equals("ferias y fiestas")) {
            result = R.drawable.fiestas;
        }

        else {
            result = R.drawable.cine;
        }


        return result;
    }

    public class ViewHolder {
        ImageView cartel;
        ImageView i;
        TextView titulo;
        TextView tema;
        TextView lugar;
        public ViewHolder(View v){
            titulo = v.findViewById(R.id.item_tv_title);
            tema = v.findViewById(R.id.item_tv_theme);
            lugar = v.findViewById(R.id.item_tv_lugar);
             i = v.findViewById(R.id.image_theme);
            cartel= v.findViewById(R.id.item_image);

        }

    }
}
