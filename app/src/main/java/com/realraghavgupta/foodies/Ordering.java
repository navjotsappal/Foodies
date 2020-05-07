package com.realraghavgupta.foodies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

//Class for the first screen with tabs for ordering groceries
public class Ordering extends Fragment {

    private SectionsPageAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Button btnCompare;

    //https://stackoverflow.com/questions/40947477/how-does-tablayout-work
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ordering, container, false);

        mSectionsPagerAdapter = new SectionsPageAdapter(getChildFragmentManager());
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        ListView groceryList = (ListView) view.findViewById(R.id.lv_groceryList);
        return view;
    }

    //https://stackoverflow.com/questions/40947477/how-does-tablayout-work
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getChildFragmentManager());

        if (FragmentCatalog.categoryName.equals("")) {
            adapter.addFragment(new FragmentPopular(), "popular");
        } else {
            adapter.addFragment(new FragmentPopular(), FragmentCatalog.categoryName);
            FragmentCatalog.categoryName = "";
        }

        adapter.addFragment(new FragmentCatalog(), "catalog");
        viewPager.setAdapter(adapter);
    }
}
