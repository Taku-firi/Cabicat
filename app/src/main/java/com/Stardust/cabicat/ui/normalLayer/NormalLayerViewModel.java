package com.Stardust.cabicat.ui.normalLayer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NormalLayerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NormalLayerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("normal layer");
    }

    public LiveData<String> getText() {
        return mText;
    }
}