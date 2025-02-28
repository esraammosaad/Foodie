package com.example.foodplannerapp.landing.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodplannerapp.R;
import com.example.foodplannerapp.utilis.SharedPreferencesManager;
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

        View view=inflater.inflate(R.layout.fragment_onboarding_one, container, false);
        nextBtn=view.findViewById(R.id.nextText);
        skipBtn=view.findViewById(R.id.skipText);
        viewPager=getActivity().findViewById(R.id.viewPager2);
        presenter= PresenterImpl.getInstance(OnBoardingRepositoryImpl.getInstance(SharedPreferencesManager.getInstance(getContext())));
        nextBtn.setOnClickListener((v)->{

            viewPager.setCurrentItem(1);

        });
        skipBtn.setOnClickListener((v)->{



            Navigation.findNavController(view).navigate(R.id.action_viewPagerFragment_to_loginFragment2);
            presenter.setOnOnBoardingState();

        });

        return view;
    }
}