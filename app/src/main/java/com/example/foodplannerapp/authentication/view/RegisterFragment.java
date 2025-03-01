package com.example.foodplannerapp.authentication.view;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
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
import com.example.foodplannerapp.data.network.database.FiresStoreServices;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.example.foodplannerapp.utilis.NetworkAvailability;
import com.example.foodplannerapp.utilis.NoInternetDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.snackbar.Snackbar;


public class RegisterFragment extends Fragment implements ViewInterface {

    private TextView signInText;
    private Button registerButton;
    private PresenterImpl presenter;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText usernameEditText;
    private TextView emailError;
    private TextView passwordError;
    private TextView usernameError;
    private Button signInWithGoogle;
    private GoogleSignInClient googleSignInClient;
    private Button visitAsAGuestButton;
    private ProgressBar progressBar;
    private ImageView googleIcon;
    private ImageView guestIcon;


    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == RESULT_OK) {

                        try {
                            presenter.loginWithGoogle(result);
                        } catch (ApiException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(requireView(), "Canceled", Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.RED);
                        snackbar.show();
                        progressBar.setVisibility(View.GONE);
                        registerButton.setVisibility(View.VISIBLE);
                        signInWithGoogle.setVisibility(View.VISIBLE);
                        visitAsAGuestButton.setVisibility(View.VISIBLE);
                        googleIcon.setVisibility(View.VISIBLE);
                        guestIcon.setVisibility(View.VISIBLE);
                    }

                }
            });


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
        emailError = view.findViewById(R.id.emailError2);
        passwordError = view.findViewById(R.id.passwordError2);
        usernameEditText = view.findViewById(R.id.editTextUsername);
        usernameError = view.findViewById(R.id.usernameError);
        progressBar = view.findViewById(R.id.progressBar2);
        googleIcon = view.findViewById(R.id.userImg);
        guestIcon = view.findViewById(R.id.imageView2);
        emailError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);
        usernameError.setVisibility(View.GONE);
        passwordEditText = view.findViewById(R.id.editTextTextPasswordRegister);
        signInWithGoogle = view.findViewById(R.id.googleSignInButtonRegister);
        visitAsAGuestButton = view.findViewById(R.id.visitAsAGuestButton);
        presenter = new PresenterImpl(AuthenticationRepositoryImpl.getInstance(AuthenticationServices.getInstance(), FiresStoreServices.getInstance()), MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(getContext()), new MealsLocalDataSource(getContext())), this);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), options);
        registerButton.setOnClickListener((v) -> {
            if (NetworkAvailability.isNetworkAvailable(getContext())) {
                if (emailEditText.getText().toString().isEmpty()) {

                    emailEditText.setBackgroundResource(R.drawable.error_edit_text_layout);
                    emailError.setVisibility(View.VISIBLE);


                }

                if (passwordEditText.getText().toString().isEmpty()) {

                    passwordEditText.setBackgroundResource(R.drawable.error_edit_text_layout);
                    passwordError.setVisibility(View.VISIBLE);
                }
                if (usernameEditText.getText().toString().isEmpty()) {

                    usernameEditText.setBackgroundResource(R.drawable.error_edit_text_layout);
                    usernameError.setVisibility(View.VISIBLE);
                }
                if (!emailEditText.getText().toString().isEmpty()) {
                    emailEditText.setBackgroundResource(R.drawable.rounded_edit_text);
                    emailError.setVisibility(View.GONE);
                }

                if (!passwordEditText.getText().toString().isEmpty()) {
                    passwordEditText.setBackgroundResource(R.drawable.rounded_edit_text);
                    passwordError.setVisibility(View.GONE);
                }
                if (!usernameEditText.getText().toString().isEmpty()) {
                    usernameEditText.setBackgroundResource(R.drawable.rounded_edit_text);
                    usernameError.setVisibility(View.GONE);
                }

                if (!emailEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty() && !usernameEditText.getText().toString().isEmpty()) {

                    presenter.register(emailEditText.getText().toString(), passwordEditText.getText().toString(), usernameEditText.getText().toString());
                    progressBar.setVisibility(View.VISIBLE);
                    registerButton.setVisibility(View.INVISIBLE);
                    signInWithGoogle.setVisibility(View.INVISIBLE);
                    visitAsAGuestButton.setVisibility(View.INVISIBLE);
                    googleIcon.setVisibility(View.INVISIBLE);
                    guestIcon.setVisibility(View.INVISIBLE);


                }
            } else {
                NoInternetDialog.showNoInternetDialog(getContext(), getString(R.string.no_internet_connection_please_reconnect_and_try_again));
            }


        });


        signInText.setOnClickListener((v) -> {
            Navigation.findNavController(view).navigateUp();
        });

        signInWithGoogle.setOnClickListener((v) -> {
            if (NetworkAvailability.isNetworkAvailable(getContext())) {
                Intent intent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(intent);
                progressBar.setVisibility(View.VISIBLE);
                registerButton.setVisibility(View.INVISIBLE);
                signInWithGoogle.setVisibility(View.INVISIBLE);
                visitAsAGuestButton.setVisibility(View.INVISIBLE);
                googleIcon.setVisibility(View.INVISIBLE);
                guestIcon.setVisibility(View.INVISIBLE);
            } else {
                NoInternetDialog.showNoInternetDialog(getContext(), getString(R.string.no_internet_connection_please_reconnect_and_try_again));

            }


        });

        visitAsAGuestButton.setOnClickListener((v) -> {

            if (NetworkAvailability.isNetworkAvailable(getContext())) {

                Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_homeFragment, null,
                        new NavOptions.Builder()
                                .setPopUpTo(R.id.loginFragment, true)
                                .setLaunchSingleTop(true)
                                .build());
            } else {
                NoInternetDialog.showNoInternetDialog(getContext(), getString(R.string.no_internet_connection_please_reconnect_and_try_again));
            }

        });
    }

    private void navigateToHome(String message) {
        if (presenter.getCurrentUser() != null) {
            Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_homeFragment, null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.loginFragment, true)
                            .setLaunchSingleTop(true)
                            .build());
            Snackbar snackbar = Snackbar
                    .make(requireView(), message, Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(Color.rgb(60, 176, 67));
            snackbar.show();
            progressBar.setVisibility(View.GONE);
            registerButton.setVisibility(View.VISIBLE);
            signInWithGoogle.setVisibility(View.VISIBLE);
            visitAsAGuestButton.setVisibility(View.VISIBLE);
            googleIcon.setVisibility(View.VISIBLE);
            guestIcon.setVisibility(View.VISIBLE);
            presenter.getFavoriteMealsFromFireStore();
            presenter.getCalendarMealsFromFireStore();


        }
    }

    @Override
    public void onGoogleLoginSuccess(String message) {
        navigateToHome(message);

    }

    @Override
    public void onSuccess(String message) {

        if (presenter.getCurrentUser() != null) {
            Snackbar snackbar = Snackbar
                    .make(requireView(), message, Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(Color.rgb(60, 176, 67));
            snackbar.show();
            progressBar.setVisibility(View.GONE);
            registerButton.setVisibility(View.VISIBLE);
            signInWithGoogle.setVisibility(View.VISIBLE);
            visitAsAGuestButton.setVisibility(View.VISIBLE);
            googleIcon.setVisibility(View.VISIBLE);
            guestIcon.setVisibility(View.VISIBLE);
            Navigation.findNavController(requireView()).navigateUp();

        }


    }

    @Override
    public void onFailure(String message) {
        Snackbar snackbar = Snackbar
                .make(requireView(), message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.show();
        progressBar.setVisibility(View.GONE);
        registerButton.setVisibility(View.VISIBLE);
        signInWithGoogle.setVisibility(View.VISIBLE);
        visitAsAGuestButton.setVisibility(View.VISIBLE);
        googleIcon.setVisibility(View.VISIBLE);
        guestIcon.setVisibility(View.VISIBLE);


    }
}