package com.example.foodplannerapp.authentication.data.network;

import static android.app.Activity.RESULT_OK;
import android.content.Context;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import com.example.foodplannerapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;


public class AuthenticationServices {
    private static FirebaseAuth firebaseAuth;
    private static AuthenticationServices instance;
    private static FirebaseUser currentUser;
    GoogleSignInClient googleSignInClient;


    private AuthenticationServices() {

        firebaseAuth = FirebaseAuth.getInstance();


    }

    public FirebaseUser getCurrentUser() {

        return firebaseAuth.getCurrentUser();
    }

    public static AuthenticationServices getInstance() {

        if (instance == null) {
            instance = new AuthenticationServices();
        }
        if (currentUser == null) {
            currentUser = firebaseAuth.getCurrentUser();
        }
        return instance;

    }

    public void register(String email, String password, String name, AuthenticationCallBack authenticationCallBack) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = firebaseAuth.getCurrentUser();
                            authenticationCallBack.onSuccess("Register Done Successfully");
                            if (currentUser != null)
                                currentUser.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(name).build());

                        } else {

                            authenticationCallBack.onFailure(task.getException().getMessage());

                        }

                    }

                });

    }

    public void login(String email, String password, AuthenticationCallBack authenticationCallBack) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = firebaseAuth.getCurrentUser();
                            authenticationCallBack.onSuccess("Login Done Successfully");
                        } else {
                            authenticationCallBack.onFailure(task.getException().getMessage());


                        }
                    }
                });


    }

    public GoogleSignInClient getGoogleSignInClient() {

        return googleSignInClient;
    }

    public GoogleSignInClient initGoogleSignIn(Context context) {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(String.valueOf(R.string.default_web_client_id))
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(context, options);


    }

    public void loginWithGoogle(ActivityResult result, AuthenticationCallBack authenticationCallBack) throws ApiException {

        if (result.getResultCode() == RESULT_OK) {
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try {
                GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = firebaseAuth.getCurrentUser();
                            authenticationCallBack.onSuccess("Login Done Successfully");
                            Log.i("TAG", "onComplete: ");

                        } else {
                            Log.i("TAG", "fail: ");
                            authenticationCallBack.onFailure(task.getException().getMessage());
                        }
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }


    }

    public void signOut(){

            firebaseAuth.signOut();
            currentUser=null;

        }
    }



