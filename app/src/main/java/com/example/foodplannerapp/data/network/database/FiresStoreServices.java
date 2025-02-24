package com.example.foodplannerapp.data.network.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.data.local.model.CalenderMealModel;
import com.example.foodplannerapp.data.local.model.FavoriteMealModel;
import com.example.foodplannerapp.utilis.Strings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.List;

public class FiresStoreServices {

    FirebaseFirestore firebaseFirestore;
    private static FiresStoreServices instance;

    private FiresStoreServices() {

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public static FiresStoreServices getInstance() {

        if (instance == null) {

            instance = new FiresStoreServices();
        }

        return instance;
    }


    public void insertFavoriteMealToFireStore(FavoriteMealModel meal, FireStoreCallBack fireStoreCallBack) {


        firebaseFirestore.collection(Strings.FAV_COLLECTION).document(meal.getUserUID()+meal.getIdMeal())
                .set(meal)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        fireStoreCallBack.onFireStoreSuccess("DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        fireStoreCallBack.onFireStoreFailure("Error writing document" + e.getMessage());
                    }
                });


    }
    public void deleteFavoriteMealFromFireStore(FavoriteMealModel meal, FireStoreCallBack fireStoreCallBack) {
        firebaseFirestore.collection(Strings.FAV_COLLECTION).document(meal.getUserUID()+meal.getIdMeal())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        fireStoreCallBack.onFireStoreSuccess("DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        fireStoreCallBack.onFireStoreFailure("Error deleting document" + e.getMessage());
                    }
                });


    }

    public void getFavoriteMealsFromFireStore(String userUID, GetDataFromFirebaseCallBack getDataFromFirebaseCallBack) {

        firebaseFirestore.collection(Strings.FAV_COLLECTION)
                .whereEqualTo("userUID", userUID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            getDataFromFirebaseCallBack.onGetFavoriteMeals(task.getResult());

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    public void deleteMealInTheCalendarFromFireStore(CalenderMealModel meal, FireStoreCallBack fireStoreCallBack) {
        firebaseFirestore.collection(Strings.CALENDAR_COLLECTION).document(meal.getUserUID()+meal.getIdMeal()+meal.getDay()+meal.getMonth()+meal.getYear())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        fireStoreCallBack.onFireStoreSuccess("DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        fireStoreCallBack.onFireStoreFailure("Error deleting document" + e.getMessage());
                    }
                });


    }

    public void getCalendarMealsFromFireStore(String userUID,GetDataFromFirebaseCallBack getDataFromFirebaseCallBack) {

        firebaseFirestore.collection(Strings.CALENDAR_COLLECTION)
                .whereEqualTo("userUID", userUID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            getDataFromFirebaseCallBack.onGetCalendarMeals(task.getResult());

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    public void insertMealInTheCalendarToFireStore(CalenderMealModel meal, FireStoreCallBack fireStoreCallBack) {


        firebaseFirestore.collection(Strings.CALENDAR_COLLECTION).document(meal.getUserUID()+meal.getIdMeal()+meal.getDay()+meal.getMonth()+meal.getYear())
                .set(meal)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        fireStoreCallBack.onFireStoreSuccess("DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        fireStoreCallBack.onFireStoreFailure("Error writing document" + e.getMessage());
                    }
                });


    }



}
