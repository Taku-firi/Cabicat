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

import com.Stardust.cabicat.MainActivity;
import com.Stardust.cabicat.R;
import com.Stardust.cabicat.adapter.FileAdapterSecretlayer;
import com.Stardust.cabicat.database.DatabaseHelper;
import com.Stardust.cabicat.item.FileItem;

import java.util.List;

public class SecretLayerFragment extends Fragment {
    private DatabaseHelper mDatabase;

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

        mDatabase = ((MainActivity)getActivity()).getDatabase();

        // fileitems for test
//        FileItem f1 = new FileItem("name_s_1","path_s_1",2);
//        FileItem f2 = new FileItem("name_s_2","path_s_2",2);
//        FileItem f3 = new FileItem("name_s_3","path_s_3",2);
//        mDatabase.createFileItem(f1,1);
//        mDatabase.createFileItem(f2,1);
//        mDatabase.createFileItem(f3,1);

        List<FileItem> ls = mDatabase.getAllItems(1);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_secretlayer);
        FileAdapterSecretlayer fileAdapterSecretlayer = new FileAdapterSecretlayer(R.layout.fileitem_adapterunit_secretlayer,ls,mDatabase);
        recyclerView.setAdapter(fileAdapterSecretlayer);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(),DividerItemDecoration.VERTICAL));

        return root;
    }
}