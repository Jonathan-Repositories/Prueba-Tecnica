package com.jonathan.navigation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

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
    private final int TIEMPO = 1800000;
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


        /*db.collection("Ubicaciones")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("Ubicacion", document.getId() + " => " + document.getString("lat"));
                                Log.d("Ubicacion", " => " + document.getData().get("punto").toString());
                            }
                        } else {
                            Log.d("Ubicacion Error", "Error getting documents: ", task.getException());
                        }
                    }
                });*/
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
            /*Map<String,Object> latlong = new HashMap<>();
            latlong.put("lat", loc.getLatitude());
            latlong.put("long", loc.getLongitude());*/
            db = FirebaseFirestore.getInstance();
            CollectionReference ubicaciones = db.collection("Ubicaciones");
            Map<String, Object> data1 = new HashMap<>();
            data1.put("punto", Arrays.asList("lat", Double.toString(loc.getLatitude()), "lon", Double.toString(loc.getLongitude())));
            ubicaciones.document("ubi"+(int)(Math.random() * 100)).set(data1);
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