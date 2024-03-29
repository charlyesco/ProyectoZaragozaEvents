package com.example.carlos.proyectoevents;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EventosAsyncTask extends AsyncTask<Void, Evento, Boolean> {

    private static final String RESULT = "result";//array
    IEventos eventoMain;
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String STREET = "streetAddress";
    public static final String SUBEVENTOS = "subEvent";
    public static final String lOCATIONS = "location";
    public static final String CATEGORY = "category";
    public static final String TITLE_CATEGORY = "titleCategory";
    public static final String PLACE_TITLE = "place_title";
    public static final String STARTDATE = "startDate";
    public static final String ENDDATE = "endDate";
    public static final String STARTHOUR = "startTime";
    public static final String ENDHOUR = "endtTime";
    public static final String OPENHOUR = "openingHours";
    public static final String DAYWEEK = "dayOfWeek";
    public static final String HORARIO = "horario";
    public static final String GEOMETRY = "geometry";
    public static final String COORDINANTES = "coordinates";
    public static final String COORD0 = "0";
    public static final String COORD1 = "1";
    public static final String REGISTRATION = "registration";
    public final String IMAGE = "image";
    public static final String URL = "url";
    public static final String ALT = "alt";
    final AdaptadorFiltro a = null;

    public EventosAsyncTask(IEventos ievento, AdaptadorFiltro a) {
        eventoMain = ievento;
        a = a;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String titulo = null, desc = null, start = null, end = null;
        Evento evento;
        String url = "https://www.zaragoza.es/sede/servicio/cultura/evento/list.json?srsname=wgs84";
        try {
            URL urlPeticion = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) urlPeticion.openConnection();
            httpConnection.setRequestMethod("GET");
            BufferedReader aReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder sBuilder = new StringBuilder();
            String line;

            while ((line = aReader.readLine()) != null) {
                sBuilder.append(line);
            }
            JSONObject coleccion = new JSONObject(sBuilder.toString());
            //Total de eventos a recoger.
            JSONArray arrayeventos = coleccion.getJSONArray(RESULT);

            for (int i = 0; i < arrayeventos.length(); i++) {
                //cada iteración es un evento
                evento = new Evento();
                JSONObject contenedor = null;
                try {

                    contenedor = arrayeventos.getJSONObject(i);
                    evento.setTitleEvent(contenedor.getString(TITLE));

                } catch (JSONException ex) {
                    evento.setTitleEvent("No disponible");
                }
                //enlace web
                try{
                    evento.setUrl(contenedor.getString(ALT));
                }catch (JSONException ex){
                    evento.setUrl("");
                }

                try {
                    evento.setDescription(contenedor.getString(DESCRIPTION));
                } catch (JSONException ex) {

                    evento.setDescription("Descripción no disponible");
                }

                final Evento finalEvento = evento;
                new Thread(new Runnable() {
                    public void run() {
                        URL urlImage = null;
                        try {
                            urlImage = new URL("http:" + finalEvento.getImage());
                            final Bitmap bmp = BitmapFactory.decodeStream(urlImage.openConnection().getInputStream());
                            Log.d("Bytes", String.valueOf(bmp.getByteCount()));
                            finalEvento.setImageBmp(bmp);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

                try {
                    evento.setImage(contenedor.getString(IMAGE));
                } catch (JSONException ex) {

                    evento.setImage("");
                }

                try {
                    evento.setStartDate(contenedor.getString(STARTDATE));
                } catch (JSONException ex) {

                    evento.setStartDate("No disponible");
                }
                try {
                    evento.setEndDate(contenedor.getString(ENDDATE));
                } catch (JSONException ex) {

                    evento.setEndDate("No disponible");
                }


                //EL TITULO
                JSONArray subEvent = contenedor.getJSONArray(SUBEVENTOS);
                JSONObject l = null;
                JSONObject loca = null;
                for (int x = 0; x < subEvent.length(); x++) {

                    l = subEvent.getJSONObject(x);
                    //cada iteracion es una propiedad del subevento
                    try {
                        loca = l.getJSONObject(lOCATIONS);

                        try {

                            evento.setAddres(loca.getString(STREET));

                        } catch (JSONException ex) {
                            evento.setAddres("");
                        }
                        try {

                            evento.setPlace(loca.getString(TITLE));
                        } catch (JSONException ex) {
                            evento.setPlace("Localización no disponible");
                        }
                    } catch (JSONException e) {
                        evento.setAddres("");
                        evento.setPlace("Localización no disponible");
                    }
                    JSONArray contHours = null;
                    try {
                        contHours = l.getJSONArray(OPENHOUR);

                        JSONObject horas = null;
//recoge las horas y los dias pero y si hay mas dias o diferentes horarios
                        String horario = "", empieza, termina, dia, horarios = "";
                        for (int k = 0; k < contHours.length(); k++) {
                            horas = contHours.getJSONObject(k);
                            try {
                                empieza = horas.getString(STARTHOUR);

                            } catch (JSONException ex) {
                                empieza = "";
                            }

                            try {
                                termina = "-" + horas.getString(ENDHOUR) + "  ";

                            } catch (JSONException ex) {
                                termina = "";
                            }
                            try {
                                dia = horas.getString(DAYWEEK);

                            } catch (JSONException ex) {
                                dia = "";
                            }
                            horario = dia + " " + empieza + "" + termina;
                            horarios += " " + horario;

                        }
                        evento.setHorario(horarios);
                    } catch (JSONException e) {
                        evento.setHorario("");
                        evento.setStartTime("");
                        evento.setEndTime("");
                    }
                }
                //RECOGEMOS EL TEMA DEL EVENTO
                JSONArray category = contenedor.getJSONArray(CATEGORY);
                for (int k = 0; k < category.length(); k++) {
                    try {
                        JSONObject categ = category.getJSONObject(k);
                        evento.setTitleCategory(categ.getString(TITLE));
                    } catch (JSONException ex) {
                        evento.setTitleCategory("No Disponible");
                    }
                }
                try {
                    JSONObject geometry = contenedor.getJSONObject(GEOMETRY);
                    JSONArray coord = geometry.getJSONArray(COORDINANTES);
                    try {
                        evento.setC0(coord.getString(0));
                    } catch (JSONException v) {
                        evento.setC0("");
                    }
                    try {
                        evento.setC1(coord.getString(1));
                    } catch (JSONException v) {
                        evento.setC1("");
                    }
                } catch (JSONException e) {
                    evento.setC0("");
                    evento.setC1("");
                }
             /*   try {
                    JSONObject registro = contenedor.getJSONObject(REGISTRATION);
                    evento.setUrl(registro.getString(URL));

                } catch (JSONException v) {
                    evento.setUrl("");
                }*/

                publishProgress(evento);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Evento... values) {
        super.onProgressUpdate(values);
        eventoMain.mostarEvento(values[0]);
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(Boolean result) {
    }

    @Override
    protected void onCancelled() {
    }
}