package com.Stardust.cabicat.ui.normalLayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Stardust.cabicat.R;
import com.Stardust.cabicat.adapter.FileAdapter;
import com.Stardust.cabicat.item.FileItem;

import java.util.ArrayList;
import java.util.List;

public class NormalLayerFragment extends Fragment {
//    private DatabaseHelper mDatabase;

    private NormalLayerViewModel normallayerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        normallayerViewModel =
                ViewModelProviders.of(this).get(NormalLayerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_normallayer, container, false);
        final TextView textView = root.findViewById(R.id.text_normallayer);
        normallayerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

//        mDatabase=getDatabase();
        FileItem fi1=new FileItem("name1","path1",Long.valueOf(1));
        FileItem fi2=new FileItem("name2","path2",Long.valueOf(2));
        List<FileItem> ls = new ArrayList<>();
        ls.add(fi1);
        ls.add(fi2);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_normallayer);
        FileAdapter fileAdapter = new FileAdapter(R.layout.fileitem_adapterunit_normallayer,ls);
        recyclerView.setAdapter(fileAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(),DividerItemDecoration.VERTICAL));

        return root;
    }
}