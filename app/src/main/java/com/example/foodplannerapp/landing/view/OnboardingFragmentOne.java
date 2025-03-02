package com.example.foodplannerapp.landing.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.authentication.data.network.AuthenticationServices;
import com.example.foodplannerapp.data.local.LocalStorageDataSource;
import com.example.foodplannerapp.landing.data.repo.OnBoardingRepositoryImpl;
import com.example.foodplannerapp.landing.presenter.PresenterImpl;


public class OnboardingFragmentOne extends Fragment {

    private TextView nextBtn;
    private TextView skipBtn;
    private ViewPager2 viewPager;
    private PresenterImpl presenter;


    public OnboardingFragmentOne() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_onboarding_one, container, false);
        nextBtn = view.findViewById(R.id.nextText);
        skipBtn = view.findViewById(R.id.skipText);
        viewPager = getActivity().findViewById(R.id.viewPager2);
        calendarPermission();
        presenter = PresenterImpl.getInstance(OnBoardingRepositoryImpl
                .getInstance(LocalStorageDataSource
                                .getInstance(getContext()),
                        AuthenticationServices.getInstance()));
        nextBtn.setOnClickListener((v) -> {

            viewPager.setCurrentItem(1);

        });
        skipBtn.setOnClickListener((v) -> {


            Navigation.findNavController(view).navigate(R.id.action_viewPagerFragment_to_loginFragment2);
            presenter.setOnOnBoardingState();

        });

        return view;
    }

    private void calendarPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                    1);
        }


    }

}