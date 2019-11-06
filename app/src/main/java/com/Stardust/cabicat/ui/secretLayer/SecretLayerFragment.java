package com.Stardust.cabicat.ui.secretLayer;

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

public class SecretLayerFragment extends Fragment {
    //    private DatabaseHelper mDatabase;
    private SecretLayerViewModel secretlayerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        secretlayerViewModel =
                ViewModelProviders.of(this).get(SecretLayerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_secretlayer, container, false);
        final TextView textView = root.findViewById(R.id.text_secretlayer);
        secretlayerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        RecyclerView rvsecret = root.findViewById(R.id.recyclerview_secretlayer);

        //        mDatabase=getDatabase();
        FileItem fi1=new FileItem("name3","path3",Long.valueOf(1));
        FileItem fi2=new FileItem("name4","path4",Long.valueOf(2));
        List<FileItem> ls = new ArrayList<>();
        ls.add(fi1);
        ls.add(fi2);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_secretlayer);
        FileAdapter fileAdapter = new FileAdapter(R.layout.fileitem_adapterunit_secretlayer,ls);
        recyclerView.setAdapter(fileAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(),DividerItemDecoration.VERTICAL));

        return root;
    }
}