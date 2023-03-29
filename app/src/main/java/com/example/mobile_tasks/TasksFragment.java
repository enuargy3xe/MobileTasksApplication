package com.example.mobile_tasks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class TasksFragment extends Fragment {


    public TasksFragment() {
    }

    public static TasksFragment newInstance() {
        TasksFragment fragment = new TasksFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TabLayout tabLayout;
    ViewPager viewPager;

    AviableAContacts aviableContactsFragment;
    IssuedTaska issuedTasksFragment;
    MyTasks myTasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        aviableContactsFragment = new AviableAContacts();
        issuedTasksFragment = new IssuedTaska();
        myTasks = new MyTasks();

        tabLayout.setupWithViewPager(viewPager);

        //viewPagerAdapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), 0){
        };
        viewPagerAdapter.addFragment(aviableContactsFragment,"Новая задача");
        viewPagerAdapter.addFragment(issuedTasksFragment,"Выданные");
        viewPagerAdapter.addFragment(myTasks,"Мои задачи");

        viewPager.setAdapter(viewPagerAdapter);


        //BadgeDrawable badgeDrawable = tabLayout.getTabAt(0).getOrCreateBadge();
        //badgeDrawable.setVisible(true);
        //badgeDrawable.setNumber(5);

        return view;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTittles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager, int behavior){
            super(fragmentManager,behavior);
        }

        public void addFragment(Fragment fragment,String tittle){
            fragments.add(fragment);
            fragmentTittles.add(tittle);
        }

        @Override
        public Fragment getItem(int position){
            return fragments.get(position);
        }

        @Override
        public int getCount(){
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position){
            return fragmentTittles.get(position);
        }
    }


}