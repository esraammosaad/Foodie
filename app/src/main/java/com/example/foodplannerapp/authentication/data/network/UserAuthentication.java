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

public class UserAuthentication {
    public final FirebaseAuth firebaseAuth;
    private static UserAuthentication instance;
    public FirebaseUser currentUser;
    GoogleSignInClient googleSignInClient;


    private UserAuthentication() {

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
//                currentUser.getIdToken(true)
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                Log.d("Auth", "Token refreshed");
//                            } else {
//                                Log.e("Auth", "Token refresh failed: " + task.getException());
//                            }
//                        });

        }


    }

    public static UserAuthentication getInstance() {
        if (instance == null) {


            instance = new UserAuthentication();
        }
        return instance;

    }

    public void register(String email, String password, AuthenticationCallBack authenticationCallBack) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            authenticationCallBack.onSuccess("Register Done Successfully");
                            currentUser = firebaseAuth.getCurrentUser();
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
                            authenticationCallBack.onSuccess("Login Done Successfully");
                            currentUser = firebaseAuth.getCurrentUser();
                        } else {
                            authenticationCallBack.onFailure(task.getException().getMessage());


                        }
                    }
                });


    }

    public GoogleSignInClient getGoogleSignInClient(){

        return googleSignInClient;
    }

    public GoogleSignInClient initGoogleSignIn(Context context){
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
                            authenticationCallBack.onSuccess("Login Done Successfully");
                            currentUser=firebaseAuth.getCurrentUser();
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



}
