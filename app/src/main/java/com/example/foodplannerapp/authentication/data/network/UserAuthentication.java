package com.example.foodplannerapp.authentication.data.network;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAuthentication {
    private final FirebaseAuth firebaseAuth;
    private static UserAuthentication instance;
    FirebaseUser currentUser;


    private UserAuthentication() {
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            //ToDo
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
                            Log.d("TAG", "createUserWithEmail:success");
                            authenticationCallBack.onSuccess("Register Done Successfully");
                            currentUser = firebaseAuth.getCurrentUser();
                        } else {

                            authenticationCallBack.onFailure("There is a problem try again later");

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
                            Log.d("TAG", "signInWithEmail:success");
                            authenticationCallBack.onSuccess("Login Done Successfully");
                            currentUser = firebaseAuth.getCurrentUser();
                        } else {
                            authenticationCallBack.onFailure("There is a problem try again later");


                        }
                    }
                });


    }



}
