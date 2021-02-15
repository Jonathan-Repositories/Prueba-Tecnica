package com.jonathan.navigation.ui.archivos;

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

public class ArchivosFragment extends Fragment {

    private ArchivosViewModel archivosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        archivosViewModel =
                new ViewModelProvider(this).get(ArchivosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_archivos, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        archivosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}