package com.example.foodplannerapp.onboarding.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.utilis.SharedPreferencesManager;
import com.example.foodplannerapp.onboarding.data.repo.OnBoardingRepositoryImpl;
import com.example.foodplannerapp.onboarding.presenter.PresenterImpl;


public class SplashFragment extends Fragment {

    private PresenterImpl presenter;


    public SplashFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = PresenterImpl.getInstance(OnBoardingRepositoryImpl.getInstance(SharedPreferencesManager.getInstance(getContext())));
        if (presenter.getThemeState()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        new Handler().postDelayed(() -> {
            if (isAdded()) {
                Navigation.findNavController(view).navigate(


                        presenter.getOnBoardingState() ?
                                AuthenticationServices.getInstance().getCurrentUser() != null ?
                                        R.id.action_splashFragment_to_homeFragment : R.id.action_splashFragment_to_loginFragment :
                                R.id.action_splashFragment_to_viewPagerFragment
                );
            }
        }, 5000);


    }
}