package com.example.carlos.proyectoevents;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DescriptionActivity extends AppCompatActivity {
    public static String title = "";
    public static String titlePlace = "";
    AdaptadorFiltro adaptadorFiltro;

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GregorianCalendar c = null;
        String start = null, end = null, startDayName = null, endDayName = null, startMonthName = null, endMonthName = null;
        int startYearName = 0, endYearName = 0, startFinalDay = 0, endFinalDay = 0, startMonth = 0, endMonth = 0, startWeekDay = 0, endWeekDay = 0, todayFinalDay = 0, todayMonth = 0, todayYear = 0;
        String mensaje = null, mensaje2 = null, mensaje3 = null, mensajeMañana = null, mensajeMismoDia;
        ImageButton b, buttoMaps;
        ImageView iconoTema, cartel;
        Date date = null, date2 = null;
        Bitmap bimage = null;


        int today = 0;

        super.onCreate(savedInstanceState);
        final Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_description);

        title = extras.getString(EventosAsyncTask.TITLE);
        final String des = extras.getString(EventosAsyncTask.DESCRIPTION), cat = extras.getString(EventosAsyncTask.TITLE_CATEGORY), lugar = extras.getString(EventosAsyncTask.STREET);
        final String m = Html.fromHtml(extras.getString(EventosAsyncTask.DESCRIPTION), Html.FROM_HTML_MODE_LEGACY).toString();

        buttoMaps = findViewById(R.id.bt_maps);
        iconoTema = findViewById(R.id.iv_tema);
        cartel = findViewById(R.id.iv_cartel);
        b = findViewById(R.id.bt_desc_anadir);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

        String mHtmlString = "&lt;p class=&quot;MsoNormal&quot; style=&quot;margin-bottom:10.5pt;text-align:justify;line-height: 10.5pt&quot;&gt;&lt;b&gt;&lt;span style=&quot;font-size: 8.5pt; font-family: Arial, sans-serif;&quot;&gt;Lorem Ipsum&lt;/span&gt;&lt;/b&gt;&lt;span style=&quot;font-size: 8.5pt; font-family: Arial, sans-serif;&quot;&gt;&amp;nbsp;is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.&lt;o:p&gt;&lt;/o:p&gt;&lt;/span&gt;&lt;/p&gt; &lt;p class=&quot;MsoNormal&quot; style=&quot;margin-bottom: 0.0001pt;&quot;&gt;&lt;span style=&quot;font-size: 8.5pt; font-family: Arial, sans-serif;&quot;&gt;&amp;nbsp;&lt;/span&gt;&lt;span style=&quot;font-family: Arial, sans-serif; font-size: 8.5pt; line-height: 10.5pt; text-align: justify;&quot;&gt;Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of &quot;de Finibus Bonorum et Malorum&quot; (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, &quot;Lorem ipsum dolor sit amet..&quot;, comes from a line in section 1.10.32.&lt;/span&gt;&lt;/p&gt;";

        TextView tv_street, tv_desc, tv_lugar, tv_category, tv_fecha, tv_horario;
        tv_category = findViewById(R.id.tv_desc_category);

        //este he cambiado
        tv_street = findViewById(R.id.tv_desc_street);
        tv_desc = findViewById(R.id.tv_desc_desc);
        //aqui recibe la direccion
        tv_lugar = findViewById(R.id.tv_desc_lugar);
        tv_fecha = findViewById(R.id.tv_desc_fecha);
        tv_horario = findViewById(R.id.tv_desc_horario);

        tv_desc.setMovementMethod(new ScrollingMovementMethod());

        start = extras.getString(EventosAsyncTask.STARTDATE);
        end = extras.getString(EventosAsyncTask.ENDDATE);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            c = new GregorianCalendar();
            //Fecha cuando empieza
            date = format.parse(start);
            c.setTime(date);

            startFinalDay = c.get(Calendar.DATE);//num dia
            startMonth = c.get(Calendar.MONTH) + 1;
            startMonthName = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());//mes escrito largo
            startDayName = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());//dia semana escrito largo
            startYearName = c.get(Calendar.YEAR);

            //Fecha cuando acaba
            date2 = format.parse(end);
            c.setTime(date2);


            endFinalDay = c.get(Calendar.DATE);//num dia
            endMonth = c.get(Calendar.MONTH) + 1;
            endMonthName = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());//mes escrito largo
            endDayName = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());//dia semana escrito largo
            endYearName = c.get(Calendar.YEAR);

            //Fecha Actual
            Date hoy = Calendar.getInstance().getTime();
            c.setTime(hoy);
            todayFinalDay = c.get(Calendar.DATE);
            todayMonth = c.get(Calendar.MONTH) + 1;//empieza desde 0
            todayYear = c.get(Calendar.YEAR);


        } catch (ParseException e) {
            e.printStackTrace();
        }


        tv_category.setText(extras.getString(EventosAsyncTask.TITLE_CATEGORY));
        tv_street.setText(extras.getString(EventosAsyncTask.STREET));
        // tv_desc.setText(extras.getString(EventosAsyncTask.DESCRIPTION));
        tv_lugar.setText(extras.getString(EventosAsyncTask.PLACE_TITLE));
        tv_horario.setText(extras.getString(EventosAsyncTask.HORARIO));
        iconoTema.setImageResource(AdaptadorFiltro.asignarImagen(extras.getString(EventosAsyncTask.TITLE_CATEGORY)));

        try {
            bimage = BitmapFactory.decodeStream(getApplicationContext().openFileInput("myImage"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //   Bitmap bimage=getIntent().getParcelableExtra(NavigationActivity.IMAGE_RES_ID_KEY);
        cartel.setImageBitmap(bimage);

        //LLegan las coordenadas
        String c0 = extras.getString(EventosAsyncTask.COORD0);
        String c1 = extras.getString(EventosAsyncTask.COORD1);

        tv_desc.setText(Html.fromHtml(Html.fromHtml(extras.getString(EventosAsyncTask.DESCRIPTION)).toString()));
        tv_desc.setMovementMethod(new ScrollingMovementMethod());
        mensaje = "Comienza el " + startDayName + " " + startFinalDay + " de " + startMonthName + " de " + startYearName + "\n" + "\n" +
                "Termina el " + endDayName + " " + endFinalDay + " de " + endMonthName + " de " + endYearName;
        mensaje2 = "El evento se realizará hoy.";
        mensaje3 = "Comienza hoy y acaba el " + endDayName + " " + endFinalDay + " de " + endMonthName + " de " + endYearName;
        mensajeMañana = "El evento comienza mañana";
        mensajeMismoDia = "";

        //dia de hoy igual al dia del comienxzo e igual al dia final y los meses iguales
        if ((startFinalDay == todayFinalDay && endFinalDay == todayFinalDay) && ((startMonth == endMonth) && (startMonth == todayMonth))) {
            tv_fecha.setText(mensaje2);//se realiza solo hoy
            //comienza mismo dia termina otro
        } else if ((startFinalDay == todayFinalDay && endFinalDay != todayFinalDay) && (startMonth == todayMonth)) {
            tv_fecha.setText(mensaje3);
        } else if (startFinalDay == (todayFinalDay + 1)) {
            tv_fecha.setText(mensajeMañana);
        } else {
            if ((startFinalDay == endFinalDay) && (startMonth == endMonth)) {
                tv_fecha.setText(mensajeMismoDia);
            } else {
                tv_fecha.setText(mensaje);
            }

        }

        final int finalStartYearName = startYearName;
        final int finalStartFinalDay = startFinalDay + 1;
        final int finalEndFinalDay = endFinalDay + 4;


        final Date finalDate = date;
        final Date finalDate1 = date2;
        final GregorianCalendar finalC = c;
        final GregorianCalendar finalC1 = c;
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i;

                i = new Intent(Intent.ACTION_EDIT);
                i.setType("vnd.android.cursor.item/event");

                i.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, finalC1.getTime());
                i.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 12);


                i.putExtra(CalendarContract.Events.TITLE, title);
                i.putExtra(CalendarContract.Events.DESCRIPTION, m);
                i.putExtra(CalendarContract.Events.EVENT_LOCATION, lugar);
                // i.putExtra(CalendarContract.Events.DTSTART, 5);
                // i.putExtra(CalendarContract.Events.DTEND, 17);

                // i.putExtra(CalendarContract.Events.DTSTART,)
                startActivity(i);
            }
        });

        buttoMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c1, c0;
                c0 = extras.getString(EventosAsyncTask.COORD0);
                c1 = extras.getString(EventosAsyncTask.COORD1);
                Intent i = new Intent(DescriptionActivity.this, MapsActivity.class);
                i.putExtra(EventosAsyncTask.COORD1, c1);
                i.putExtra(EventosAsyncTask.COORD0, c0);
                i.putExtra(EventosAsyncTask.TITLE, title);
                startActivity(i);
            }
        });

    }
}
