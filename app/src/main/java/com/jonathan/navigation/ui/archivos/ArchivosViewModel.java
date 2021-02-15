package com.jonathan.navigation.ui.archivos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ArchivosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ArchivosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Subir Archivos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}