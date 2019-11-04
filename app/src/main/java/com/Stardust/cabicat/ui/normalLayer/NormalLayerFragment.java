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
import androidx.recyclerview.widget.RecyclerView;

import com.Stardust.cabicat.R;

public class NormalLayerFragment extends Fragment {

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

        RecyclerView rvnormal = root.findViewById(R.id.recyclerview_normallayer);


        return root;
    }
}