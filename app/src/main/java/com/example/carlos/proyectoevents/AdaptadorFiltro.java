package com.example.carlos.proyectoevents;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {


            view = LayoutInflater.from(context).inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.titulo = view.findViewById(R.id.item_tv_title);
            holder.tema = view.findViewById(R.id.item_tv_theme);
            holder.lugar = view.findViewById(R.id.item_tv_lugar);
            ImageView image = view.findViewById(R.id.image_theme);
            holder.i = asignarImagen(image, listaEventos.get(position).getTitleCategory());


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.titulo.setText(listaEventos.get(position).getTitleEvent());
        holder.tema.setText(listaEventos.get(position).getTitleCategory());
        holder.lugar.setText(listaEventos.get(position).getPlace());
        ImageView image = view.findViewById(R.id.image_theme);
        holder.i = asignarImagen(image, listaEventos.get(position).getTitleCategory());
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

    public static ImageView asignarImagen(ImageView i, String tema) {

        String b = Normalizer.normalize(tema, Normalizer.Form.NFD).toLowerCase();
        b = b.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        if (b.equals("conferencias y congresos")) {
            i.setImageResource(R.drawable.conferencias);

        } else if (b.equals("artes plasticas")) {
            i.setImageResource(R.drawable.artes_plasticas);
        } else if (b.equals("musica")) {
            i.setImageResource(R.drawable.musica);
        } else if (b.equals("deporte")) {
            i.setImageResource(R.drawable.deporte);
        } else if (b.equals("ocio y juegos")) {
            i.setImageResource(R.drawable.ocio);
        } else if (b.equals("cine")) {
            i.setImageResource(R.drawable.cine);
        } else if (b.equals("otros")) {
            i.setImageResource(R.drawable.otros);
        } else if (b.equals("gastronomia")) {
            i.setImageResource(R.drawable.gastronomia);
        } else if (b.equals("cursos y talleres")) {
            i.setImageResource(R.drawable.cursos_talleres);
        } else if (b.equals("imagen y sonido")) {
            i.setImageResource(R.drawable.imagen_sonido);
        } else if (b.equals("cine")) {
            i.setImageResource(R.drawable.cine);
        } else if (b.equals("otros")) {
            i.setImageResource(R.drawable.otros);
        } else if (b.equals("visitas turisticas")) {
            i.setImageResource(R.drawable.actividades_turisticas);
        }
        else if (b.equals("ciencia y tecnologia")) {
            i.setImageResource(R.drawable.ciencia);
        }
        else if (b.equals("exposiciones")) {
            i.setImageResource(R.drawable.picture);
        }
        else if (b.equals("actividades vacacionales")) {
            i.setImageResource(R.drawable.vacacionales);
        }
        else if (b.equals("teatro y artes escenicas")) {
            i.setImageResource(R.drawable.teatro);
        }else if (b.equals("turismo")) {
            i.setImageResource(R.drawable.turismo);
        }else if (b.equals("formacion")) {
            i.setImageResource(R.drawable.formacion);
        }else if (b.equals("ferias y fiestas")) {
            i.setImageResource(R.drawable.fiestas);
        }

        else {
            i.setImageResource(R.drawable.cine);
        }


        return i;
    }

    public class ViewHolder {
        ImageView i;
        TextView titulo;
        TextView tema;
        TextView lugar;
    }
}
