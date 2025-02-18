package com.example.foodplannerapp.authentication.view;

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

import com.example.foodplannerapp.R;


public class RegisterFragment extends Fragment {

    TextView signInText;
    Button registerButton;



    public RegisterFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signInText=view.findViewById(R.id.signInText);
        registerButton=view.findViewById(R.id.signInButton);
        registerButton.setOnClickListener((v)->{
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_homeFragment);
        });


        signInText.setOnClickListener((v)->{



            Navigation.findNavController(view).navigateUp();

        });
    }
}