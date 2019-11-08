package com.Stardust.cabicat.ui.home;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mTextLatest;

    public HomeViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is the home page");


        mTextLatest = new MutableLiveData<>();
        mTextLatest.setValue("Latest Files ");

    }

//    public LiveData<String> getText() {
//        return mText;
//    }
    public LiveData<String> getTextLatest() {
        return mTextLatest;
    }

}