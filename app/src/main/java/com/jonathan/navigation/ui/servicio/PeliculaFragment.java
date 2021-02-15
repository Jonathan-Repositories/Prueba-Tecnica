package com.jonathan.navigation.ui.servicio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.jonathan.navigation.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeliculaFragment extends Fragment {

    private PeliculaViewModel peliculaViewModel;
    private static String API_KEY = "41c57e0ad14b00dcd5801ed7cf4a42a0";
    private static String LANGUAGE = "en-us";
    private static String MOVIE_ID = "464052";
    Api api;
    TextView textView;
    ImageView img;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        peliculaViewModel =
                new ViewModelProvider(this).get(PeliculaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pelicula, container, false);
        final TextView textView = root.findViewById(R.id.text_pelicula);
        peliculaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                api = RetrofitInstance.getApi();
                Call<Pelicula> call = api.getPelicula(MOVIE_ID,API_KEY,LANGUAGE);

                call.enqueue(new Callback<Pelicula>() {
                    @Override
                    public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                        Integer id = response.body().getId();
                        String title = response.body().getTitle();
                        String ruta = response.body().getBackdropPath();
                        String overview = response.body().getOverview();
                        Pelicula collections = response.body();
                        List<Pelicula.Production_Companies> nombres = collections.getProductionCompanies();
                        Pelicula.Production_Companies nombre = nombres.get(0);
                        Log.i("API",title);
                        //Glide.with(getContext()).load("https://api.themoviedb.org"+ruta).into(image);
                        textView.setText("ID: "+id+"\n\nTítulo: "+title+"\n\nDescripción: "+overview+"\n\nUniverso: "+nombre);
                    }

                    @Override
                    public void onFailure(Call<Pelicula> call, Throwable t) {
                        Toast.makeText(getContext(), "No hay conexión a internet", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        return root;

    }

}