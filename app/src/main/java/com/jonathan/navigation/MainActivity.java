package com.jonathan.navigation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseFirestore db;
    LocationManager locationManager;
    private Location loc;
    private final int TIEMPO = 1800000;//Para cada media hora
    //private final int TIEMPO = 5000;//Para prueba cada 5 segundos
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        ejecutarTarea();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Desliza de la orilla izquierda a derecha para mostrar el menú");
        builder.setTitle("Atención");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ejecutarTarea() {
        handler.postDelayed(new Runnable() {
            public void run() {
                subir();
                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }
    public void subir(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.i("Permiso ", "No hay permiso de ubicación");
        }else
        {

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            db = FirebaseFirestore.getInstance();
            CollectionReference ubicaciones = db.collection("Ubicaciones");
            Map<String, Object> datos = new HashMap<>();
            datos.put("latitude", loc.getLatitude());
            datos.put("longitude", loc.getLongitude());
            ubicaciones.document("ubi"+(int)(Math.random() * 100)).set(datos);
            Log.i("Actual ", Double.toString(loc.getLatitude()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}