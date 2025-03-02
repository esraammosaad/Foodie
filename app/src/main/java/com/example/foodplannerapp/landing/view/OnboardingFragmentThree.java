package com.example.foodplannerapp.landing.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.data.local.LocalStorageDataSource;
import com.example.foodplannerapp.landing.data.repo.OnBoardingRepositoryImpl;
import com.example.foodplannerapp.landing.presenter.PresenterImpl;

public class OnboardingFragmentThree extends Fragment {

    private Button getStartedBtn;
    private PresenterImpl presenter;


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
        getStartedBtn = view.findViewById(R.id.getStartedButton);

        presenter = PresenterImpl.getInstance(OnBoardingRepositoryImpl
                .getInstance(LocalStorageDataSource
                        .getInstance(getContext()), AuthenticationServices.getInstance()));

        getStartedBtn.setOnClickListener((v) -> {
            Navigation.findNavController(view).navigate(R.id.action_viewPagerFragment_to_loginFragment2);
            presenter.setOnOnBoardingState();

        });
    }
}