package com.jonathan.navigation.ui.servicio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PeliculaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PeliculaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragmento");
    }

    public LiveData<String> getText() {
        return mText;
    }
}