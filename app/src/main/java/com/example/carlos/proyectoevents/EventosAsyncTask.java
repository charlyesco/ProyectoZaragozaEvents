package com.example.carlos.proyectoevents;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
    public static final String PLACE = "title";
    //FECHAS
    public static final String STARTDATE = "startDate";
    public static final String ENDDATE = "endDate";
    //HORAS
    public static final String STARTHOUR = "startTime";
    public static final String ENDHOUR = "endtTime";
    public static final String OPENHOUR = "openingHours";
    public static final String DAYWEEK = "dayOfWeek";
    public static final String GEOMETRY = "geometry";
    public static final String COORDINANTES = "coordinates";
    public static final int COORD0 = 0;
    public static final String COORD1 = "1";

    public EventosAsyncTask(IEventos ievento) {
        eventoMain = ievento;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        String titulo = null, desc = null, start = null, end = null;
        Evento evento;
        String url = "https://www.zaragoza.es/sede/servicio/cultura/evento/list.json";
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
                try {
                    evento.setDescription(contenedor.getString(DESCRIPTION));
                } catch (JSONException ex) {

                    evento.setDescription("Descripción no disponible");
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
                // JSONArray arrayeventos = coleccion.getJSONArray(RESULT);
                //contenedor = arrayeventos.getJSONObject(i);
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

                            evento.setPlace(loca.getString(PLACE));
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
                        //solucion concatenarlostodo
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
                        // evento.setTitleCategory(category.getJSONObject(k).getJSONObject(CATEGORY).getString(TITLE_CATEGORY));
                    } catch (JSONException ex) {
                        //  evento.setTitleCategory("No Disponible");
                        ex.printStackTrace();
                    }

                }

                //  JSONObject geometry = contenedor.getJSONObject(GEOMETRY);

                //     JSONArray coord = geometry.getJSONArray(COORDINANTES);

                //for (int m = 0; m < coord.length(); m++) {
                //    try {
                //         JSONObject c1 = coord.getJSONObject(m);
                //        evento.setC1(c1.getString(COORD1).toString());

                //  } catch (JSONException v) {
                //   }
                //    try {
                //      JSONObject c0 = coord.getJSONObject(m);
                //    evento.setC0(Double.parseDouble(c0.getString(String.valueOf(COORD0)).toString()));

                //  } catch (JSONException v) {
                //      v.printStackTrace();
                //     v.getMessage();
                //  }
                // }

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
        // pb.setMax(100);
        //  pb.setProgress(0);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        //  if(result)
        // Toast.makeText(MainActivity.this, "Tarea finalizada!",
        //     Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCancelled() {
        // Toast.makeText(MainActivity.this, "Tarea cancelada!",
        //      Toast.LENGTH_SHORT).show();
    }
}

//try{
//   evento.setEndTime(hours.getString(ENDHOUR));

// }catch (JSONException ex){
//    evento.setEndTime("");
// }
//   try{
//    evento.setDayOfWeek(hours.getString(DAYWEEK));
// }catch (JSONException ex){
//    evento.setDayOfWeek("");
// }