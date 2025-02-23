package com.example.foodplannerapp.profile.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.data.network.database.FiresStoreServices;
import com.example.foodplannerapp.profile.presenter.PresenterImpl;


public class ProfileFragment extends Fragment {

    Button signOutButton;
    PresenterImpl presenter;



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
        signOutButton.setOnClickListener((v)->{

            presenter.signOut();
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment);


        });
    }
}