package com.example.foodplannerapp.profile.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.data.network.database.FiresStoreServices;
import com.example.foodplannerapp.profile.presenter.PresenterImpl;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NetworkChangeListener;
import com.example.foodplannerapp.utilis.NetworkListener;
import com.example.foodplannerapp.utilis.NoInternetDialog;
import com.example.foodplannerapp.utilis.NoInternetSnackBar;
import com.example.foodplannerapp.utilis.ShareApp;
import com.google.android.material.materialswitch.MaterialSwitch;


public class ProfileFragment extends Fragment implements NetworkListener {

    private Button signOutButton;
    private PresenterImpl presenter;
    private TextView userName;
    private TextView userEmail;
    private ImageView userImg;
    private ImageView githubIcon;
    private ImageView linkedinIcon;
    private ImageView googleIcon;
    private MaterialSwitch darkModeSwitch;
    private ImageView editProfileIcon;
    private ImageView shareAppIcon;
    private Group guestGroup;
    private Group guestSecondGroup;
    private Group profileGroup;
    private NetworkChangeListener networkChangeListener;
    private TextView guest;
    private TextView login;
    private ImageView noInternetIcon;
    private TextView noInternetText;


    public ProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new PresenterImpl(AuthenticationRepositoryImpl.getInstance(AuthenticationServices.getInstance(), FiresStoreServices.getInstance()));
        signOutButton = view.findViewById(R.id.signOutButton);
        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        userImg = view.findViewById(R.id.userImg);
        githubIcon = view.findViewById(R.id.githubIcon);
        linkedinIcon = view.findViewById(R.id.linkedinIcon);
        googleIcon = view.findViewById(R.id.googleIcon);
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch);
        editProfileIcon = view.findViewById(R.id.editProfileIcon);
        shareAppIcon = view.findViewById(R.id.shareAppIcon);
        guestGroup = view.findViewById(R.id.calendarGuestGroup);
        guestSecondGroup = view.findViewById(R.id.guestGroupProfile);
        profileGroup = view.findViewById(R.id.profileGroup);
        guest = view.findViewById(R.id.guest);
        login = view.findViewById(R.id.login);
        noInternetIcon = view.findViewById(R.id.noInternetIcon);
        noInternetText = view.findViewById(R.id.noInternetText);
        networkChangeListener = new NetworkChangeListener(this);
        if (presenter.getCurrentUser() == null) {
            guestGroup.setVisibility(View.VISIBLE);
            guestSecondGroup.setVisibility(View.VISIBLE);
            profileGroup.setVisibility(View.GONE);

            login.setOnClickListener((v) -> {
                Navigation.findNavController(getView()).navigate(R.id.action_profileFragment_to_loginFragment, null,
                        new NavOptions.Builder()
                                .setPopUpTo(R.id.profileFragment, true)
                                .setPopUpTo(R.id.homeFragment, true)
                                .build());
            });

        } else {
            userName.setText(presenter.getCurrentUser().getDisplayName());
            userEmail.setText(presenter.getCurrentUser().getEmail());
            signOutButton.setOnClickListener((v) -> {

                if (NetworkAvailability.isNetworkAvailable(getContext())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to sign out ?");
                    builder.setTitle(R.string.alert);
                    builder.setCancelable(false);
                    builder.setNegativeButton(R.string.yes, (DialogInterface.OnClickListener) (dialog, which) -> {
                        presenter.signOut();
                        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment);

                    });
                    builder.setPositiveButton(R.string.no, (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                } else {
                    NoInternetDialog.showNoInternetDialog(getContext(), getString(R.string.no_internet_connection_please_reconnect_and_try_again));


                }


            });
            guest.setOnClickListener((v) -> {
                guestGroup.setVisibility(View.GONE);
            });

            if (presenter.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(requireContext()).load(presenter.getCurrentUser().getPhotoUrl()).into(userImg);
            }
            darkModeSwitch.setChecked(presenter.getThemeState(getContext()));
            darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    presenter.saveThemeState(requireContext(), true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


                } else {
                    presenter.saveThemeState(requireContext(), false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


                }
            });
            githubIcon.setOnClickListener((v) -> {
                openWebView("https://github.com/esraammosaad");


            });
            linkedinIcon.setOnClickListener((v) -> {

                openWebView("https://www.linkedin.com/in/esraa-mosaad-b8ba2724a/");
            });
            googleIcon.setOnClickListener((v) -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"esraa.m.mosaad@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Foodie App");

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }


            });
            shareAppIcon.setOnClickListener((v) -> {

                startActivity(Intent.createChooser(ShareApp.shareApp(), "choose one"));


            });
        }

    }

    public void openWebView(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void onLostConnection() {

        NoInternetSnackBar.showSnackBar(requireView());
        guestGroup.setVisibility(View.VISIBLE);
        noInternetIcon.setImageResource(R.drawable.baseline_wifi_off_24);
        noInternetText.setText(R.string.noInternetText);
        guest.setText(R.string.dismiss);
        login.setText(R.string.turnOnWifi);


    }

    @Override
    public void onConnectionReturned() {

        if (presenter.getCurrentUser() != null) {

            guestGroup.setVisibility(View.GONE);
        } else {
            noInternetIcon.setImageResource(R.drawable.baseline_assignment_ind_24);
            noInternetText.setText(R.string.signInNow);
            guest.setText(R.string.continue_as_a_guest);
            login.setText(R.string.sign_in);


        }


    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        requireActivity().registerReceiver(networkChangeListener, intentFilter);
    }
}