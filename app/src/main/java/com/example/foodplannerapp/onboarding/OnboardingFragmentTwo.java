package com.example.foodplannerapp.onboarding;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodplannerapp.R;


public class OnboardingFragmentTwo extends Fragment {

    TextView nextBtn;
    ViewPager2 viewPager;




    public OnboardingFragmentTwo() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_onboarding_two, container, false);
        nextBtn=view.findViewById(R.id.nextText);
        viewPager=getActivity().findViewById(R.id.viewPager2);
        nextBtn.setOnClickListener((v)->{

            viewPager.setCurrentItem(2);

        });
        return view;
    }
}