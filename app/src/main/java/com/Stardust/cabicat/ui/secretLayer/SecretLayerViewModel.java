package com.Stardust.cabicat.ui.secretLayer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SecretLayerViewModel extends ViewModel {
    //    private DatabaseHelper mDatabase;
    private MutableLiveData<String> mText;

    public SecretLayerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("secret layer");
    }

    public LiveData<String> getText() {
        return mText;
    }
}