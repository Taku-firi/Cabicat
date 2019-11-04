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
import androidx.recyclerview.widget.RecyclerView;

import com.Stardust.cabicat.R;

public class SecretLayerFragment extends Fragment {

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

        return root;
    }
}