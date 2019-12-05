package com.Stardust.cabicat.ui.normalLayer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Stardust.cabicat.helper.DatabaseHelper;
import com.Stardust.cabicat.item.FileItem;

import java.util.List;

public class NormalLayerViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<FileItem>> mNormallayerFilelist;
    private DatabaseHelper mDatabase ;

    public NormalLayerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("normal layer");


        //mNormallayerFilelist = new MutableLiveData<>();
        //mNormallayerFilelist.setValue(mDatabase.getAllItems(0));

    }
    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<FileItem>> getFileItemList(){return mNormallayerFilelist;}
}