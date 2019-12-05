package com.Stardust.cabicat.ui.secretLayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.Stardust.cabicat.helper.DatabaseHelper;
import com.Stardust.cabicat.item.FileItem;
import com.Stardust.cabicat.item.PwdCheckDialog;

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
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerview_secretlayer);


        mDatabase = ((MainActivity) getActivity()).getDatabase();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));


        // live data example (will be removed)
        secretlayerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        SharedPreferences preferences = getActivity().getSharedPreferences("cabidata",Context.MODE_PRIVATE);
        boolean alreadyChecked = preferences.getBoolean("checked",false);

        if (alreadyChecked){
            List<FileItem> ls = mDatabase.getAllItems(1);
            FileAdapterSecretlayer fileAdapterSecretlayer = new FileAdapterSecretlayer(R.layout.fileitem_adapterunit_secretlayer, ls, mDatabase);
            recyclerView.setAdapter(fileAdapterSecretlayer);
        }else {

            // Check password
            final PwdCheckDialog pwdCheckDialog = new PwdCheckDialog(getActivity());
            pwdCheckDialog.setPasswordCallback(new PwdCheckDialog.PasswordCallback() {
                @Override
                public void callback(String password) {
                    SharedPreferences pref = getActivity().getSharedPreferences("cabidata", Context.MODE_PRIVATE);
                    String pwd = pref.getString("pwd","");

                    if (pwd.equals(password)) {
                        pwdCheckDialog.dismiss();

                        // fileitems for test
//        FileItem f1 = new FileItem("name_s_1","path_s_1",2);
//        FileItem f2 = new FileItem("name_s_2","path_s_2",2);
//        FileItem f3 = new FileItem("name_s_3","path_s_3",2);
//        mDatabase.createFileItem(f1,1);
//        mDatabase.createFileItem(f2,1);
//        mDatabase.createFileItem(f3,1);

                        List<FileItem> ls = mDatabase.getAllItems(1);

                        FileAdapterSecretlayer fileAdapterSecretlayer = new FileAdapterSecretlayer(R.layout.fileitem_adapterunit_secretlayer, ls, mDatabase);
                        recyclerView.setAdapter(fileAdapterSecretlayer);
                        Toast.makeText(getActivity(), "Identity Verified", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("cabidata",Context.MODE_PRIVATE).edit();
                        editor.putBoolean("checked",true);
                        editor.apply();
                    } else {
                        Toast.makeText(getActivity(), "Access Denied" + password, Toast.LENGTH_SHORT).show();
                        pwdCheckDialog.clearPasswordText();
                    }
                }
            });
            pwdCheckDialog.clearPasswordText();
            pwdCheckDialog.show();
        }
        return root;
    }


}