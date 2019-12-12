package com.Stardust.cabicat.ui.normalLayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Stardust.cabicat.MainActivity;
import com.Stardust.cabicat.R;
import com.Stardust.cabicat.adapter.FileAdapterNormallayer;
import com.Stardust.cabicat.helper.DatabaseHelper;
import com.Stardust.cabicat.item.FileItem;

import java.util.List;

public class NormalLayerFragment extends Fragment {
    private DatabaseHelper mDatabase;

    private NormalLayerViewModel normallayerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        normallayerViewModel =
                ViewModelProviders.of(this).get(NormalLayerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_normallayer, container, false);


        mDatabase=((MainActivity)getActivity()).getDatabase();


        List<FileItem> ls = mDatabase.getAllItems(0);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_normallayer);
        FileAdapterNormallayer fileAdapterNormallayer = new FileAdapterNormallayer(R.layout.fileitem_adapterunit_normallayer,ls,mDatabase);
        recyclerView.setAdapter(fileAdapterNormallayer);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(),DividerItemDecoration.VERTICAL));

        return root;
    }
}