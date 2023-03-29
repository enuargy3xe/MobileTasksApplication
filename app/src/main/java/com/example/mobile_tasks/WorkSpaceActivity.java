package com.example.mobile_tasks;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class WorkSpaceActivity extends AppCompatActivity {

    BottomNavigationView bottomNavBar;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workspace_activity);

        bottomNavBar = findViewById(R.id.bottomNavigationView);

        setFragment(TasksFragment.newInstance());
        bottomNavBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.contacts_view:
                        setFragment(ContactsFragment.newInstance());

                        break;
                    case R.id.new_task:
                        setFragment(TasksFragment.newInstance());
                        break;
                    case R.id.profile_view:
                        setFragment(ProfileFragment.newInstance());
                        break;
                }
                return true;
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout,fragment);
        transaction.setReorderingAllowed(false);
        transaction.commit();
    }


}
