package com.example.foodplannerapp.onboarding.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.utilis.Strings;

public class OnboardingFragmentThree extends Fragment {

    Button getStartedBtn;



    public OnboardingFragmentThree() {
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getStartedBtn=view.findViewById(R.id.getStartedButton);
        getStartedBtn.setOnClickListener((v)->{

            Navigation.findNavController(view).navigate(R.id.action_viewPagerFragment_to_loginFragment2);
            SharedPreferences prefs= getActivity().getSharedPreferences(Strings.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putBoolean(Strings.SEEN_ONBOARDING,true);
            editor.apply();

        });
    }
}