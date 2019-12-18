package com.Stardust.cabicat.ui.secretLayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.Stardust.cabicat.helper.FileOperateUtil;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class SecretLayerFragment extends Fragment {

    private DatabaseHelper mDatabase;

    private SecretLayerViewModel secretlayerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        secretlayerViewModel =
                ViewModelProviders.of(this).get(SecretLayerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_secretlayer, container, false);

        final RecyclerView recyclerView = root.findViewById(R.id.recyclerview_secretlayer);


        mDatabase = ((MainActivity) getActivity()).getDatabase();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));



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

                    if (pwd.equals(md5(password))) {
                        pwdCheckDialog.dismiss();


                        List<FileItem> ls = mDatabase.getAllItems(1);

                        FileAdapterSecretlayer fileAdapterSecretlayer = new FileAdapterSecretlayer(R.layout.fileitem_adapterunit_secretlayer, ls, mDatabase);
                        recyclerView.setAdapter(fileAdapterSecretlayer);
                        Toast.makeText(getActivity(), "Identity Verified", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("cabidata",Context.MODE_PRIVATE).edit();
                        editor.putBoolean("checked",true);
                        editor.apply();
                    } else {
                        Toast.makeText(getActivity(), "Access Denied" , Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("cabidata",Context.MODE_PRIVATE).edit();
                        int denied = pref.getInt("denied",0);
                        denied = denied+1;
                        if (denied == 5){
                            editor.putInt("denied",0);
                            editor.apply();
                            Toast.makeText(getActivity(),"Hazardous situation, screat layer been cleaned. ",Toast.LENGTH_SHORT).show();
                            FileOperateUtil util = new FileOperateUtil();
                            util.deleteAllsecret(getContext(),mDatabase.getAllItems(1));
                            mDatabase.deleteSecret();
                        }else {
                            editor.putInt("denied", denied);
                            editor.apply();
                        }
                        pwdCheckDialog.clearPasswordText();
                    }
                }
            });
            pwdCheckDialog.clearPasswordText();
            pwdCheckDialog.show();
        }
        return root;
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}