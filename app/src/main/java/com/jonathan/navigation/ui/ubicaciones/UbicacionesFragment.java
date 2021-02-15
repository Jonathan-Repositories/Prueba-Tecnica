package com.jonathan.navigation.ui.ubicaciones;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jonathan.navigation.R;

public class UbicacionesFragment extends Fragment {

    private UbicacionesViewModel ubicacionesViewModel;
    Marker marker1;
    private GoogleMap mMap;
    FirebaseFirestore db;
    double lat;
    double lng;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ubicacionesViewModel = new ViewModelProvider(this).get(UbicacionesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ubicaciones, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                db = FirebaseFirestore.getInstance();
                db.collection("Ubicaciones")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("Ubicacion", " => " + document.getData().get("latitude"));
                                        lat = Double.parseDouble(document.getData().get("latitude").toString());
                                        lng = Double.parseDouble(document.getData().get("longitude").toString());
                                        LatLng marcador = new LatLng(lat, lng);
                                        mMap.addMarker(new MarkerOptions().position(marcador).title("Punto"));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marcador, 13));
                                    }
                                } else {
                                    Log.d("Ubicacion Error", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });
        ubicacionesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}