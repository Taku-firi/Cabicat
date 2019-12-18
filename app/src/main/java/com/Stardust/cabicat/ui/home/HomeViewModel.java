package com.Stardust.cabicat.ui.home;

import android.content.SharedPreferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mTextLatest;
    private MutableLiveData<String> mTextRecent;

    public HomeViewModel() {

        mTextLatest = new MutableLiveData<>();
        mTextLatest.setValue("Latest Files ");

        mTextRecent = new MutableLiveData<>();
        mTextRecent.setValue("Recently viewed ");

    }


    public LiveData<String> getTextLatest() {
        return mTextLatest;
    }

    public LiveData<String> getTextRecent() { return mTextRecent; }

}