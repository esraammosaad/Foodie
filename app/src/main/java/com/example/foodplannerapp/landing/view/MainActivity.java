package com.example.foodplannerapp.landing.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.landing.data.repo.OnBoardingRepositoryImpl;
import com.example.foodplannerapp.landing.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NoInternetDialog;
import com.example.foodplannerapp.utilis.ShareApp;
import com.example.foodplannerapp.utilis.SharedPreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private ImageView menuIcon;
    private DrawerLayout drawerLayout;
    private TextView appName;
    private TextView userName;
    private TextView userEmail;
    private ImageView userImg;
    private FirebaseUser currentUser;
    private PresenterImpl presenter;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavBar);
        presenter = PresenterImpl.getInstance(OnBoardingRepositoryImpl
                .getInstance(SharedPreferencesManager
                        .getInstance(this), AuthenticationServices.getInstance()));
        appName = findViewById(R.id.textView9);
        menuIcon = findViewById(R.id.menuIcon);
        drawerLayout = findViewById(R.id.main);
        NavigationView navView = findViewById(R.id.navView);
        View headerView = navView.getHeaderView(0);
        userEmail = headerView.findViewById(R.id.userEmail);
        userName = headerView.findViewById(R.id.userName);
        userImg = headerView.findViewById(R.id.userImg);
        menuIcon.setOnClickListener((v) -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        NavigationUI.setupWithNavController(bottomNav, navController);
        bottomNav.setVisibility(View.GONE);
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.homeFragment || navDestination.getId() == R.id.searchFragment || navDestination.getId() == R.id.calenderFragment || navDestination.getId() == R.id.favoriteFragment || navDestination.getId() == R.id.profileFragment) {
                currentUser = presenter.getCurrentUser();
                if (currentUser != null) {
                    userName.setText(currentUser.getDisplayName());
                    userEmail.setText(currentUser.getEmail());
                    if (currentUser.getPhotoUrl() != null) {
                        Glide.with(this).load(currentUser.getPhotoUrl()).into(userImg);

                    } else {
                        userImg.setImageResource(R.drawable.img);
                    }
                } else {
                    userName.setText(R.string.guest);
                    userEmail.setText(R.string.sign_in_to_see_your_profile);
                    userImg.setImageResource(R.drawable.img);
                }

                bottomNav.setVisibility(View.VISIBLE);
                menuIcon.setVisibility(View.VISIBLE);
                appName.setVisibility(View.VISIBLE);

            } else {
                bottomNav.setVisibility(View.GONE);
                menuIcon.setVisibility(View.GONE);
                appName.setVisibility(View.GONE);
            }

        });

        navView.setNavigationItemSelectedListener(item -> onNavigationItemSelected(item));


    }


    private void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_you_want_to_sign_out);
        builder.setTitle(R.string.alert);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            presenter.signOut();
            navigateToLogin();
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.darkMode) {
            changeTheme();
        } else if (id == R.id.share) {
            startActivity(Intent.createChooser(ShareApp.shareApp(), "choose one"));
        } else {
            if (NetworkAvailability.isNetworkAvailable(this)) {
                if (AuthenticationServices.getInstance().getCurrentUser() != null) {
                    signOut();
                }
            } else {
                NoInternetDialog.showNoInternetDialog(this, getString(R.string.no_internet_connection_please_reconnect_and_try_again));
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void navigateToLogin() {
        navController.navigate(R.id.loginFragment, null, new NavOptions.Builder()
                .setPopUpTo(R.id.homeFragment, true)
                .setPopUpTo(R.id.searchFragment, true)
                .setPopUpTo(R.id.favoriteFragment, true)
                .setPopUpTo(R.id.calenderFragment, true)
                .setLaunchSingleTop(true)
                .build());
    }

    private void changeTheme() {
        if (!presenter.getThemeState()) {
            presenter.setThemeState(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            presenter.setThemeState(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}