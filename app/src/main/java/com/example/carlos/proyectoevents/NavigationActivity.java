package com.example.carlos.proyectoevents;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IEventos, SearchView.OnQueryTextListener {
    ListView listView;
    private static final int INTERVALO = 1500; //1,5 segundos para salir
    private long tiempoPrimerClick;
    public static int login = 0;
    public static int control = 0;
    ProgressBar pb;
    MenuItem searchMenuItem;
    final NavigationActivity contrato = this;
    AdaptadorFiltro adaptadorFiltro;
    ArrayList<Evento> datos = new ArrayList<Evento>();
    SearchView searchView;
    MenuItem sesion;
    public static String IMAGE_RES_ID_KEY = "hola";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        listView = findViewById(R.id.lvitems);
        pb = new ProgressBar(NavigationActivity.this);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        pb.setProgress(0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView.setTextFilterEnabled(true);
        adaptadorFiltro = new AdaptadorFiltro(NavigationActivity.this, datos);

        listView.setAdapter(adaptadorFiltro);
        EventosAsyncTask eat = new EventosAsyncTask(contrato, (AdaptadorFiltro) listView.getAdapter());
        eat.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento e = (Evento) adaptadorFiltro.getItem(position);
                Intent intento = new Intent(NavigationActivity.this, DescriptionActivity.class);
                intento.putExtra(EventosAsyncTask.COORD1, e.getC1());
                intento.putExtra(EventosAsyncTask.COORD0, e.getC0().toString());
                intento.putExtra(EventosAsyncTask.PLACE_TITLE, e.getPlace().toString());
                intento.putExtra(EventosAsyncTask.DESCRIPTION, e.getDescription().toString());
                intento.putExtra(EventosAsyncTask.STREET, e.getAddres().toString());
                intento.putExtra(EventosAsyncTask.TITLE_CATEGORY, e.getTitleCategory().toString());
                intento.putExtra(EventosAsyncTask.STARTDATE, e.getStartDate().toString());
                intento.putExtra(EventosAsyncTask.ENDDATE, e.getEndDate());
                intento.putExtra(EventosAsyncTask.TITLE, e.getTitleEvent().toString());
                intento.putExtra(EventosAsyncTask.HORARIO, e.getHorario().toString());//  intento.putExtra(IMAGE_RES_ID_KEY,e.getImageBmp());
                intento.putExtra(IMAGE_RES_ID_KEY, createImageFromBitmap(e.getImageBmp()));
                startActivity(intento);
            }
        });
        if (listView.getCount() > 0) {
            pb.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()) {
                super.onBackPressed();
                login = 0;
                Intent i = new Intent(NavigationActivity.this, InicioActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
            }
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        sesion = menu.findItem(R.id.icono);
        if (login == 0) {
            sesion.setIcon(ContextCompat.getDrawable(this, R.drawable.close));
        } else {
            sesion.setIcon(ContextCompat.getDrawable(this, R.drawable.open));
        }
        sesion.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (login == 0) {
                    MenuItem m = menu.findItem(R.id.action_settings);
                    m.setVisible(false);
                } else if (login == 1) {
                    MenuItem m = menu.findItem(R.id.action_settings);
                    m.setVisible(true);
                }
                return true;
            }
        });
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.busqueda);

        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint("Escribir evento...");
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchView.setQueryHint("Escribir evento...");
                control = 0;
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //1 quiere decir que ha iniciado conexion
        if (login > 0) {
            if (id == R.id.action_settings) {
                login = 0;
                Intent i = new Intent(NavigationActivity.this, InicioActivity.class);
                startActivity(i);
            }
        }
        if (id == R.id.icono) {
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.lugar) {
            searchView.setIconifiedByDefault(true);
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
            control = 1;
            searchView.setQueryHint("Escribir lugar...");
        } else if (id == R.id.tema) {
            searchView.setIconifiedByDefault(true);
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
            searchView.setQueryHint("Escribir tema...");
            control = 2;
        } else if (id == R.id.info) {
            Intent i = new Intent(NavigationActivity.this, InfoActivity.class);
            startActivity(i);
        } else if (id == R.id.preguntas) {
            Intent i = new Intent(NavigationActivity.this, NosotrosActivity.class);
            startActivity(i);

        } else if (id == R.id.contacto) {
            Intent i = new Intent(NavigationActivity.this, ContactoActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void mostarEvento(Evento evento) {
        datos.add(evento);
        adaptadorFiltro.notifyDataSetChanged();
        if (pb.isShown()) {
            pb.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        AdaptadorFiltro customAdapter = (AdaptadorFiltro) listView.getAdapter();
        Filter filter = customAdapter.getFilter();


        if (TextUtils.isEmpty(newText)) {
            filter.filter("");
            listView.clearTextFilter();
            control = 0;
            searchView.setQueryHint("Escribir evento...");
        } else {


            filter.filter(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }
}
