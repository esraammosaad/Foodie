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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.UserAuthentication;
import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.authentication.presenter.PresenterImpl;


public class RegisterFragment extends Fragment implements ViewInterface {

    TextView signInText;
    Button registerButton;
    PresenterImpl presenter;
    EditText emailEditText;
    EditText passwordEditText;
    TextView emailError;
    TextView passwordError;


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
        signInText = view.findViewById(R.id.signInText);
        registerButton = view.findViewById(R.id.signInButton);
        emailEditText = view.findViewById(R.id.editTextTextEmailAddressRegister);
        emailError=view.findViewById(R.id.emailError2);
        passwordError=view.findViewById(R.id.passwordError2);
        emailError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);
        passwordEditText = view.findViewById(R.id.editTextTextPasswordRegister);
        presenter = PresenterImpl.getInstance(AuthenticationRepositoryImpl.getInstance(UserAuthentication.getInstance()), this);
        registerButton.setOnClickListener((v) -> {
            if (emailEditText.getText().toString().isEmpty()) {

                emailEditText.setBackgroundResource(R.drawable.error_edit_text_layout);
                emailError.setVisibility(View.VISIBLE);


            }

            if (passwordEditText.getText().toString().isEmpty()) {

                passwordEditText.setBackgroundResource(R.drawable.error_edit_text_layout);
                passwordError.setVisibility(View.VISIBLE);
            }
            if(!emailEditText.getText().toString().isEmpty()){
                emailEditText.setBackgroundResource(R.drawable.rounded_edit_text);
                emailError.setVisibility(View.GONE);
            }

            if(!passwordEditText.getText().toString().isEmpty()){
                passwordEditText.setBackgroundResource(R.drawable.rounded_edit_text);
                passwordError.setVisibility(View.GONE);
            }

            if (!emailEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {

                presenter.register(emailEditText.getText().toString(), passwordEditText.getText().toString());


            }


        });


        signInText.setOnClickListener((v) -> {
            Navigation.findNavController(view).navigateUp();
        });
    }

    @Override
    public void onSuccess(String message) {

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

        Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_homeFragment);

    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();


    }
}