package com.Stardust.cabicat.ui.home;

import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private DatabaseHelper mDatabase;

    private HomeViewModel homeViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

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


        // latest added files
        final TextView textViewlatest = root.findViewById(R.id.home_text_latestfiles);
        homeViewModel.getTextLatest().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewlatest.setText("Latest Added ");
            }
        });


        // recently viewed files
        final TextView textViewrecent = root.findViewById(R.id.home_text_recentfiles);
        homeViewModel.getTextRecent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewrecent.setText("Recently viewed");
            }
        });



        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatabase=((MainActivity)getActivity()).getDatabase();

        // display greeting
        SharedPreferences pref = getActivity().getSharedPreferences("cabidata",MODE_PRIVATE);
        String username = pref.getString("username","");
        String welcome = "Welcome ——— " + username + " !!!!";
        TextView tvName = getActivity().findViewById(R.id.text_home);
        tvName.setText(welcome);


        // latest added files
        List<FileItem> ls_l = mDatabase.getNewestFile();
        RecyclerView recyclerView_l = getView().findViewById(R.id.home_view_latestfiles);
        CardviewAdapter cardviewAdapter_l = new CardviewAdapter(R.layout.cardview_adapterunit,ls_l,mDatabase);
        recyclerView_l.setAdapter(cardviewAdapter_l);
        LinearLayoutManager layoutManager_l = new LinearLayoutManager(getContext());
        layoutManager_l.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_l.setLayoutManager(layoutManager_l);
        recyclerView_l.setNestedScrollingEnabled(false);


        // recently viewed files
        List<FileItem> ls_r = mDatabase.getRecentCheckedFile();
        RecyclerView recyclerView_r =  getView().findViewById(R.id.home_view_recentfiles);
        CardviewAdapter cardviewAdapter_r = new CardviewAdapter(R.layout.cardview_adapterunit,ls_r,mDatabase);
        recyclerView_r.setAdapter(cardviewAdapter_r);
        LinearLayoutManager layoutManager_r = new LinearLayoutManager(getContext());
        layoutManager_r.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView_r.setLayoutManager(layoutManager_r);
        recyclerView_r.setNestedScrollingEnabled(false);
    }
}