package com.jonathan.navigation.ui.ubicaciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jonathan.navigation.R;

public class UbicacionesFragment extends Fragment {

    private UbicacionesViewModel ubicacionesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ubicacionesViewModel = new ViewModelProvider(this).get(UbicacionesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ubicaciones, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        ubicacionesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}