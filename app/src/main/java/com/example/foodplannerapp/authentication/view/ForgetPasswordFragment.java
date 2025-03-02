package com.example.foodplannerapp.authentication.view;

import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.authentication.presenter.PresenterImpl;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.network.database.RemoteDatabaseServices;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NoInternetDialog;
import com.example.foodplannerapp.data.local.LocalStorageDataSource;
import com.google.android.material.snackbar.Snackbar;


public class ForgetPasswordFragment extends Fragment implements ViewInterface {
    private EditText emailEditText;
    private TextView emailErrorResetPass;
    private Button resetPassword;
    private PresenterImpl presenter;
    private ImageView backIcon;
    ProgressBar progressBar;


    public ForgetPasswordFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEditText = view.findViewById(R.id.emailEditText);
        emailErrorResetPass = view.findViewById(R.id.emailErrorResetPass);
        resetPassword = view.findViewById(R.id.resetPassword);
        backIcon = view.findViewById(R.id.backIcon);
        progressBar = view.findViewById(R.id.progressBar5);
        backIcon.setOnClickListener((v) -> Navigation.findNavController(requireView()).navigateUp());
        presenter = new PresenterImpl(AuthenticationRepositoryImpl.getInstance(AuthenticationServices.getInstance(),
                RemoteDatabaseServices.getInstance(), LocalStorageDataSource.getInstance(getContext())),
                MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(requireContext()),
                        new MealsLocalDataSource(getContext())), this);
        resetPassword.setOnClickListener((v) -> {
            if (NetworkAvailability.isNetworkAvailable(requireContext())) {
                if (emailEditText.getText().toString().isEmpty()) {
                    emailEditText.setBackgroundResource(R.drawable.error_edit_text_layout);
                    emailErrorResetPass.setVisibility(View.VISIBLE);
                } else {
                    emailEditText.setBackgroundResource(R.drawable.rounded_edit_text);
                    emailErrorResetPass.setVisibility(View.GONE);
                    presenter.forgetPassword(emailEditText.getText().toString());
                    progressBar.setVisibility(View.VISIBLE);
                    resetPassword.setVisibility(View.GONE);
                }
            } else {
                NoInternetDialog.showNoInternetDialog(getContext(), getString(R.string.no_internet_connection_please_reconnect_and_try_again));
            }
        });
    }

    @Override
    public void onGoogleLoginSuccess(String message) {

    }

    @Override
    public void onSuccess(String message) {
        Snackbar snackbar = Snackbar
                .make(requireView(), message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.rgb(60, 176, 67));
        snackbar.show();
        Navigation.findNavController(requireView()).navigateUp();
        progressBar.setVisibility(View.GONE);
        resetPassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(String message) {
        Snackbar snackbar = Snackbar
                .make(requireView(), message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.show();
        progressBar.setVisibility(View.GONE);
        resetPassword.setVisibility(View.VISIBLE);





    }
}