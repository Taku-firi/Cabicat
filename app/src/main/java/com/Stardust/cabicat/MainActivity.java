package com.Stardust.cabicat;

import android.os.Bundle;

import com.Stardust.cabicat.database.DatabaseHelper;
import com.Stardust.cabicat.item.FileItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase= DatabaseHelper.getInstance(getApplicationContext());
        mDatabase.getWritableDatabase();


        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navView.getMenu()).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }



    public DatabaseHelper getDatabase() {
        return mDatabase;
    }

}
