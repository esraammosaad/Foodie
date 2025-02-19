package com.example.foodplannerapp.authentication.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.UserAuthentication;
import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.authentication.presenter.PresenterImpl;
import com.google.android.material.snackbar.Snackbar;


public class LoginFragment extends Fragment implements ViewInterface {

    TextView registerText;
    Button loginButton;
    EditText emailEditText;
    EditText passwordEditText;
    PresenterImpl presenter;
    TextView emailError;
    TextView passwordError;
    ConstraintLayout constraintLayout;



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
        emailEditText=view.findViewById(R.id.editTextTextEmailAddressLogin);
        passwordEditText=view.findViewById(R.id.editTextTextPasswordLogin);
        emailError=view.findViewById(R.id.emailError);
        passwordError=view.findViewById(R.id.passwordError);
        constraintLayout=view.findViewById(R.id.loginLayout);
        emailError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);
        presenter=PresenterImpl.getInstance(AuthenticationRepositoryImpl.getInstance(UserAuthentication.getInstance()),this);
        loginButton.setOnClickListener((v)->{
            if (emailEditText.getText().toString().isEmpty()) {

                emailEditText.setBackgroundResource(R.drawable.error_edit_text_layout);
                emailError.setVisibility(View.VISIBLE);


            }

            if (passwordEditText.getText().toString().isEmpty()) {

                passwordEditText.setBackgroundResource(R.drawable.error_edit_text_layout);
                passwordError.setVisibility(View.VISIBLE);
            }

            if (!emailEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {

                emailEditText.setBackgroundResource(R.drawable.rounded_edit_text);
                passwordEditText.setBackgroundResource(R.drawable.rounded_edit_text);
                emailError.setVisibility(View.GONE);
                passwordError.setVisibility(View.GONE);
                presenter.login(emailEditText.getText().toString(),passwordEditText.getText().toString());


            }
        });
        registerText.setOnClickListener((v)->{
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);


        });

    }

    @Override
    public void onSuccess(String message) {
        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_homeFragment);
        Snackbar snackbar = Snackbar
                .make(loginButton, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.rgb(60,176,67));
        snackbar.show();

    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        Snackbar snackbar = Snackbar
                .make(loginButton, message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.show();


    }
}