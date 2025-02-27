package com.example.foodplannerapp.onboarding.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplannerapp.R;

import java.util.ArrayList;
import java.util.Arrays;


public class ViewPagerFragment extends Fragment {
    private ViewPager2 viewPager;

    public ViewPagerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_view_pager, container, false);
        ArrayList<Fragment> fragmentList=new ArrayList<Fragment>(Arrays.asList(new OnboardingFragmentOne(),new OnboardingFragmentTwo(),new OnboardingFragmentThree()));
        ViewPagerAdapter adapter=new ViewPagerAdapter(fragmentList,requireActivity().getSupportFragmentManager(),getLifecycle());
        viewPager=view.findViewById(R.id.viewPager2);
        viewPager.setAdapter(adapter);
        return view;
    }
}