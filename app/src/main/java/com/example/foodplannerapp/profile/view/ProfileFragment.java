package com.example.foodplannerapp.profile.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.data.network.database.FiresStoreServices;
import com.example.foodplannerapp.profile.presenter.PresenterImpl;
import com.google.android.material.materialswitch.MaterialSwitch;


public class ProfileFragment extends Fragment {

    Button signOutButton;
    PresenterImpl presenter;
    TextView userName;
    TextView userEmail;
    ImageView userImg;
    ImageView githubIcon;
    ImageView linkedinIcon;
    ImageView googleIcon;
    MaterialSwitch darkModeSwitch;
    ImageView editProfileIcon;
    ImageView shareAppIcon;






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
        presenter=new PresenterImpl(AuthenticationRepositoryImpl.getInstance(AuthenticationServices.getInstance(), FiresStoreServices.getInstance()));
        signOutButton=view.findViewById(R.id.signOutButton);
        userName=view.findViewById(R.id.userName);
        userEmail=view.findViewById(R.id.userEmail);
        userImg=view.findViewById(R.id.userImg);
        githubIcon=view.findViewById(R.id.githubIcon);
        linkedinIcon=view.findViewById(R.id.linkedinIcon);
        googleIcon=view.findViewById(R.id.googleIcon);
        darkModeSwitch=view.findViewById(R.id.darkModeSwitch);
        editProfileIcon=view.findViewById(R.id.editProfileIcon);
        shareAppIcon=view.findViewById(R.id.shareAppIcon);

        signOutButton.setOnClickListener((v)->{

            presenter.signOut();
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment);


        });
        userName.setText(presenter.getCurrentUser().getDisplayName());
        userEmail.setText(presenter.getCurrentUser().getEmail());
        if(presenter.getCurrentUser().getPhotoUrl()!=null){
        Glide.with(requireContext()).load(presenter.getCurrentUser().getPhotoUrl()).into(userImg);
        }

        darkModeSwitch.setChecked(presenter.getThemeState(getContext()));
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                presenter.saveThemeState(requireContext(), true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


            }
            else{
                presenter.saveThemeState(requireContext(), false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


            }});


    }
}