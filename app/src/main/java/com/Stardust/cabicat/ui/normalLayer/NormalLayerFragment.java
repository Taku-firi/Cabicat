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

import com.Stardust.cabicat.MainActivity;
import com.Stardust.cabicat.R;
import com.Stardust.cabicat.adapter.FileAdapterNormallayer;
import com.Stardust.cabicat.database.DatabaseHelper;
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
        final TextView textView = root.findViewById(R.id.text_normallayer);
        normallayerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mDatabase=((MainActivity)getActivity()).getDatabase();

        // fileitems for test
//        FileItem f1 = new FileItem("name_n_1","path_n_1",2);
//        FileItem f2 = new FileItem("name_n_2","path_n_2",2);
//        FileItem f3 = new FileItem("name_n_3","path_n_3",2);
//        mDatabase.createFileItem(f1,0);
//        mDatabase.createFileItem(f2,0);
//        mDatabase.createFileItem(f3,0);

        List<FileItem> ls = mDatabase.getAllItems(0);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_normallayer);
        FileAdapterNormallayer fileAdapterNormallayer = new FileAdapterNormallayer(R.layout.fileitem_adapterunit_normallayer,ls,mDatabase);
        recyclerView.setAdapter(fileAdapterNormallayer);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(),DividerItemDecoration.VERTICAL));

        return root;
    }
}