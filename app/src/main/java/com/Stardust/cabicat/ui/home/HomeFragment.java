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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Stardust.cabicat.MainActivity;
import com.Stardust.cabicat.R;
import com.Stardust.cabicat.adapter.CardviewAdapter;
import com.Stardust.cabicat.adapter.FileAdapterNormallayer;
import com.Stardust.cabicat.helper.DatabaseHelper;
import com.Stardust.cabicat.item.FileItem;

import java.util.List;

public class HomeFragment extends Fragment {
    private DatabaseHelper mDatabase;

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
        ImageView imageViewtop = root.findViewById(R.id.home_top_imageview);
        imageViewtop.setAlpha(0.5f);

        // button for import activity
        Button btnImport = root.findViewById(R.id.home_btn_import);
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_navigation_home_to_fileManager);
            }
        });

        //button for register activity
        Button btnRegister = root.findViewById(R.id.home_btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_navigation_home_to_register);
            }
        });

        //button for changepwd activity
        Button btnChangepwd = root.findViewById(R.id.home_btn_changepwd);
        btnChangepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_navigation_home_to_changepwd);
            }
        });



        final TextView textViewlatest = root.findViewById(R.id.home_text_latestfiles);
        homeViewModel.getTextLatest().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewlatest.setText("Latest Files");
            }
        });

        mDatabase=((MainActivity)getActivity()).getDatabase();

        List<FileItem> ls = mDatabase.getNewestFile();

        RecyclerView recyclerView = root.findViewById(R.id.home_view_latestfiles);
        CardviewAdapter cardviewAdapter = new CardviewAdapter(R.layout.cardview_adapterunit,ls,mDatabase);
        recyclerView.setAdapter(cardviewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatabase=((MainActivity)getActivity()).getDatabase();

        List<FileItem> ls = mDatabase.getAllItems(0);

        RecyclerView recyclerView = getView().findViewById(R.id.home_view_latestfiles);
        CardviewAdapter cardviewAdapter = new CardviewAdapter(R.layout.cardview_adapterunit,ls,mDatabase);
        recyclerView.setAdapter(cardviewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}