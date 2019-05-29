package com.example.carlos.proyectoevents;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IEventos, SearchView.OnQueryTextListener {
    ListView listView;

    public static int control = 0;

    ProgressBar pb;
    MenuItem searchMenuItem;
    final NavigationActivity contrato = this;
    AdaptadorFiltro adaptadorFiltro;
    ArrayList<Evento> datos = new ArrayList<Evento>();
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        listView = findViewById(R.id.lvitems);

        pb = new ProgressBar(NavigationActivity.this);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        pb.setProgress(0);


        EventosAsyncTask eat = new EventosAsyncTask(contrato);
        eat.execute();

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento e = datos.get(position);
                Intent intento = new Intent(NavigationActivity.this, DescriptionActivity.class);
                intento.putExtra(EventosAsyncTask.PLACE_TITLE, e.getPlace().toString());
                intento.putExtra(EventosAsyncTask.DESCRIPTION, e.getDescription().toString());
                intento.putExtra(EventosAsyncTask.STREET, e.getAddres().toString());
                intento.putExtra(EventosAsyncTask.TITLE_CATEGORY, e.getTitleCategory().toString());
                intento.putExtra(EventosAsyncTask.STARTDATE, e.getStartDate().toString());
                intento.putExtra(EventosAsyncTask.ENDDATE, e.getEndDate());
                intento.putExtra(EventosAsyncTask.TITLE, e.getTitleEvent().toString());
                startActivity(intento);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);


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
                control=0;
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
        } else if (id == R.id.licencias) {

        } else if (id == R.id.preguntas) {
            Intent i= new Intent(NavigationActivity.this, NosotrosActivity.class);
            startActivity(i);

        } else if (id == R.id.contacto) {

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

        if (TextUtils.isEmpty(newText)) {
            listView.clearTextFilter();
            control = 0;
            searchView.setQueryHint("Escribir evento...");
        } else {

            listView.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
