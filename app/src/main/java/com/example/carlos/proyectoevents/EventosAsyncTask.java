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
    public static final String LUGAR = "streetAddress";
    public static final String SUBEVENTOS = "subEvent";
    public static final String lOCATIONS = "location";
    public static final String CATEGORY = "category";
    public static final String TITLE_CATEGORY = "titleCategory";
    public static final String STARTDATE = "startDate";
    public static final String ENDDATE = "endDate";

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

            for (int i = 0; i < arrayeventos.length(); i++) {// arrayeventos.length()
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

                    evento.setDescription("No disponible");
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
                for (int x = 0; x < subEvent.length(); x++) {
                    try {
                        //cada iteracion es una propiedad del subevento
                        JSONObject location = subEvent.getJSONObject(x).getJSONObject(lOCATIONS);
                        evento.setAddres(location.getString(LUGAR));
                    } catch (JSONException ex) {
                        evento.setAddres("No Disponible");
                        ex.printStackTrace();
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

