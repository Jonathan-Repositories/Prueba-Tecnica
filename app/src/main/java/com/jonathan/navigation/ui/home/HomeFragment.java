package com.jonathan.navigation.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jonathan.navigation.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static String API_KEY = "41c57e0ad14b00dcd5801ed7cf4a42a0";
    private static String LANGUAGE = "en-us";
    private static String MOVIE_ID = "464052";
    Api api;
    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_pelicula);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                api = RetrofitInstance.getApi();
                Call<Pelicula> call = api.getPelicula(MOVIE_ID,API_KEY,LANGUAGE);

                call.enqueue(new Callback<Pelicula>() {
                    @Override
                    public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                        Integer id = response.body().getId();
                        String title = response.body().getTitle();
                        String ruta = response.body().getBackdropPath();
                        String overview = response.body().getOverview();
                        //String nombre = response.body().
                        Pelicula collections = response.body();
                        List<Pelicula.Production_Companies> nombres = collections.getProductionCompanies();
                        Pelicula.Production_Companies nombre = nombres.get(0);
                        //textView.setText("ID: "+id+"\nNombre: "+nombre.getName()+"\nTitle: "+title);
                        //textView.setText("ID: "+id+"\nNombre: "+ruta+"\nTitle: "+title+"\nDescripción: "+overview+"\nUniverso: "+nombre);
                        Log.i("API",title);
                        textView.setText(title);
                        //Glide.with(getContext()).load("https://api.themoviedb.org"+ruta).into(image);
                    }

                    @Override
                    public void onFailure(Call<Pelicula> call, Throwable t) {

                    }
                });
            }
        });


        return root;

    }

}