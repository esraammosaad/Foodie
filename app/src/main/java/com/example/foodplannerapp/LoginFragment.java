package com.example.foodplannerapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class LoginFragment extends Fragment {

    TextView registerText;
    Button loginButton;



    public LoginFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerText=view.findViewById(R.id.registerText);
        loginButton=view.findViewById(R.id.signInButton);
        loginButton.setOnClickListener((v)->{
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
        });
        registerText.setOnClickListener((v)->{
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);


        });

    }
}