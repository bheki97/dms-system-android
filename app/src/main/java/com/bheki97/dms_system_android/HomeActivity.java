package com.bheki97.dms_system_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.bheki97.dms_system_android.userdetails.UserDetailsHolder;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.bheki97.dms_system_android.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        binding.appBarHome.fab.setOnClickListener(this::openReportDisasterActivity);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();

        setLogoutListener(navigationView.getMenu().findItem(R.id.nav_logout));

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        TextView usernameView = (TextView) binding.navView.getHeaderView(0).findViewById(R.id.username);
        UserDetailsHolder holder = UserDetailsHolder.getInstance();
        String username   = convertToInitCap(holder.getFirstname()) +" " +convertToInitCap(holder.getLastname());
        usernameView.setText(username);

        TextView userEmailView = (TextView) binding.navView.getHeaderView(0).findViewById(R.id.userEmail);
        userEmailView.setText(holder.getEmail());
    }

    private void setLogoutListener(MenuItem item) {
        item.setOnMenuItemClickListener( i -> {
            UserDetailsHolder.build(null);
            startActivity(new Intent(this,MainActivity.class));
            finish();
            return false;
        });
    }

    private String convertToInitCap(String name){
        String names[] = name.split(" ");
        name = "";
        String val;
        for(int i=0; i<names.length;i++){
            val = names[i];
            val = val.toLowerCase();
            val  = Character.toUpperCase(val.charAt(0))+val.substring(1);
            name  += val +" ";
        }
        return name.trim();
    }
    private void openReportDisasterActivity(View view) {
        startActivity(new Intent(this,ReporterActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== R.id.action_profile) {
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}