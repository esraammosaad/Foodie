package com.example.foodplannerapp.authentication.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.authentication.data.repo.AuthenticationRepositoryImpl;
import com.example.foodplannerapp.authentication.presenter.PresenterImpl;
import com.example.foodplannerapp.data.local.MealsLocalDataSource;
import com.example.foodplannerapp.data.network.MealsRemoteDataSource;
import com.example.foodplannerapp.data.network.database.FiresStoreServices;
import com.example.foodplannerapp.data.repo.MealsRepositoryImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.snackbar.Snackbar;


public class LoginFragment extends Fragment implements ViewInterface {

    TextView registerText;
    Button loginButton;
    EditText emailEditText;
    EditText passwordEditText;
    PresenterImpl presenter;
    TextView emailError;
    TextView passwordError;
    Button signInWithGoogle;
    GoogleSignInClient googleSignInClient;
    Button visitAsAGuestButton;


    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {

                    try {
                        presenter.loginWithGoogle(result);
                    } catch (ApiException e) {
                        throw new RuntimeException(e);
                    }

                }
            });


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
        registerText = view.findViewById(R.id.registerText);
        loginButton = view.findViewById(R.id.signInButton);
        emailEditText = view.findViewById(R.id.editTextTextEmailAddressLogin);
        passwordEditText = view.findViewById(R.id.editTextTextPasswordLogin);
        emailError = view.findViewById(R.id.emailError);
        passwordError = view.findViewById(R.id.passwordError);
        signInWithGoogle = view.findViewById(R.id.googleSignInButton);
        visitAsAGuestButton=view.findViewById(R.id.visitAsAGuestButton);
        emailError.setVisibility(View.GONE);
        passwordError.setVisibility(View.GONE);
        presenter = new PresenterImpl(AuthenticationRepositoryImpl.getInstance(AuthenticationServices.getInstance(), FiresStoreServices.getInstance()), MealsRepositoryImpl.getInstance(new MealsRemoteDataSource(), new MealsLocalDataSource(getContext())), this);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), options);


        loginButton.setOnClickListener((v) -> {
            if (emailEditText.getText().toString().isEmpty()) {

                emailEditText.setBackgroundResource(R.drawable.error_edit_text_layout);
                emailError.setVisibility(View.VISIBLE);


            }

            if (passwordEditText.getText().toString().isEmpty()) {
                passwordEditText.setBackgroundResource(R.drawable.error_edit_text_layout);
                passwordError.setVisibility(View.VISIBLE);
            }

            if (!emailEditText.getText().toString().isEmpty()) {
                emailEditText.setBackgroundResource(R.drawable.rounded_edit_text);
                emailError.setVisibility(View.GONE);
            }

            if (!passwordEditText.getText().toString().isEmpty()) {
                passwordEditText.setBackgroundResource(R.drawable.rounded_edit_text);
                passwordError.setVisibility(View.GONE);
            }

            if (!emailEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {


                presenter.login(emailEditText.getText().toString(), passwordEditText.getText().toString());


            }
        });
        registerText.setOnClickListener((v) -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);


        });

        signInWithGoogle.setOnClickListener((v) -> {
            Intent intent = googleSignInClient.getSignInIntent();
            activityResultLauncher.launch(intent);

        });
        visitAsAGuestButton.setOnClickListener((v)->{

            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
        });


    }

    @Override
    public void onSuccess(String message) {

        if (presenter.getCurrentUser() != null) {
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
            Snackbar snackbar = Snackbar
                    .make(requireView(), message, Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(Color.rgb(60, 176, 67));
            snackbar.show();
            presenter.getFavoriteMealsFromFireStore();


        }

    }

    @Override
    public void onFailure(String message) {
        Snackbar snackbar = Snackbar
                .make(requireView(), message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.show();


    }
}