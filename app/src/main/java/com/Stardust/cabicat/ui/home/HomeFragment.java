package com.Stardust.cabicat.ui.home;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.Stardust.cabicat.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(Html.fromHtml("<font color = '#68228B'>" + s + "</font>"));
//            }
//        });
//
        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_navigation_home_to_fileManager);
            }
        });
        ImageView imageViewtop = root.findViewById(R.id.home_top_imageview);
        imageViewtop.setAlpha(0.5f);

        final TextView textViewlatest = root.findViewById(R.id.home_text_latestfiles);
        homeViewModel.getTextLatest().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewlatest.setText("Latest Files");
            }
        });

        return root;
    }
}